/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. See accompanying LICENSE file.
 */
package org.apache.submarine.rest;

import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.submarine.annotation.SubmarineApi;
import org.apache.submarine.database.MyBatisUtil;
import org.apache.submarine.database.entity.SysDeptSelect;
import org.apache.submarine.database.entity.QueryResult;
import org.apache.submarine.database.entity.SysDeptTree;
import org.apache.submarine.database.entity.SysDept;
import org.apache.submarine.database.mappers.SysDeptMapper;
import org.apache.submarine.database.utils.DepartmentUtil;
import org.apache.submarine.server.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/sys/dept")
@Produces("application/json")
@Singleton
public class SysDeptRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(SysDeptRestApi.class);

  public static final String SHOW_ALERT = "showAlert";

  @Inject
  public SysDeptRestApi() {
  }

  @GET
  @Path("/tree")
  @SubmarineApi
  // Query all departments and respond to the front end in tree structure data format
  public Response tree(@QueryParam("deptCode") String likeDeptCode,
                       @QueryParam("deptName") String likeDeptName) {
    LOG.info("SysDeptRestApi.tree()");

    List<SysDept> sysDeptList = null;
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);
      Map<String, Object> where = new HashMap<>();
      where.put("deptCode", likeDeptCode);
      where.put("deptName", likeDeptName);
      sysDeptList = sysDeptMapper.selectAll(where);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK).success(false).build();
    } finally {
      sqlSession.close();
    }

    List<SysDeptSelect> sysDeptSelects = new ArrayList<>();
    List<SysDeptTree> sysDeptTreeList = DepartmentUtil.wrapDeptListToTree(sysDeptList, sysDeptSelects);
    PageInfo<SysDept> page = new PageInfo<>(sysDeptList);

    long sizeDeptTreeList = DepartmentUtil.getDeptTreeSize(sysDeptTreeList);
    if (sysDeptList.size() != sizeDeptTreeList) {
      QueryResult<SysDept> queryResult = new QueryResult(sysDeptList, page.getTotal());

      JsonResponse.Builder builder = new JsonResponse.Builder<QueryResult<SysDept>>(Response.Status.OK)
          .success(true).result(queryResult);
      if (StringUtils.isEmpty(likeDeptCode) && StringUtils.isEmpty(likeDeptName)) {
        // Query some data, may not be a configuration error
        builder.attribute(SHOW_ALERT, Boolean.TRUE);
        LOG.warn("The department's level is set incorrectly. Now show all department in a list.");
      }
      return builder.build();
    } else {
      QueryResult<SysDeptTree> queryResult = new QueryResult(sysDeptTreeList, page.getTotal());
      return new JsonResponse.Builder<QueryResult<SysDeptTree>>(Response.Status.OK)
          .success(true).result(queryResult).build();
    }
  }

  @GET
  @Path("/queryIdTree")
  @SubmarineApi
  public Response queryIdTree(@QueryParam("disableDeptCode") String disableDeptCode) {
    LOG.info("queryIdTree({})", disableDeptCode);

    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);
      List<SysDept> sysDeptList = sysDeptMapper.selectAll(new HashMap<>());

      List<SysDeptSelect> sysDeptSelects = new ArrayList<>();
      DepartmentUtil.wrapDeptListToTree(sysDeptList, sysDeptSelects);

      if (!StringUtils.isEmpty(disableDeptCode)) {
        DepartmentUtil.disableTagetDeptCode(sysDeptSelects, disableDeptCode);
      }
      return new JsonResponse.Builder<QueryResult<List<SysDeptSelect>>>(Response.Status.OK)
          .success(true).result(sysDeptSelects).build();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK).success(false).build();
    } finally {
      sqlSession.close();
    }
  }

  @POST
  @Path("/add")
  @SubmarineApi
  public Response add(SysDept sysDept) {
    LOG.info("add({})", sysDept.toString());

    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);
      sysDeptMapper.add(sysDept);
      sqlSession.commit();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK).success(false)
          .message("Save department failed!").build();
    } finally {
      sqlSession.close();
    }
    return new JsonResponse.Builder<QueryResult<SysDept>>(Response.Status.OK)
        .success(true).message("Save department successfully!").result(sysDept).build();
  }

  @PUT
  @Path("/edit")
  @SubmarineApi
  public Response edit(SysDept sysDept) {
    LOG.info("edit({})", sysDept.toString());
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);

      SysDept dept = sysDeptMapper.getById(sysDept.getId());
      if (dept == null) {
        return new JsonResponse.Builder<>(Response.Status.OK)
            .message("Can not found department:" + sysDept.getId()).success(false).build();
      }
      sysDeptMapper.updateBy(sysDept);
      sqlSession.commit();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK)
          .message("Update department failed!").success(false).build();
    } finally {
      sqlSession.close();
    }
    return new JsonResponse.Builder<QueryResult<SysDept>>(Response.Status.OK)
        .success(true).message("Update department successfully!").build();
  }

  @PUT
  @Path("/resetParentDept")
  @SubmarineApi
  public Response resetParentDept() {
    LOG.info("resetParentDept()");
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);
      sysDeptMapper.resetDeptLevel();
      sqlSession.commit();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK)
          .message("Reset department level failed!").success(false).build();
    } finally {
      sqlSession.close();
    }
    return new JsonResponse.Builder<QueryResult<SysDept>>(Response.Status.OK)
        .success(true).message("Reset department level successfully!").build();
  }

  @DELETE
  @Path("/delete")
  @SubmarineApi
  public Response delete(@QueryParam("id") String id, @QueryParam("deleted") int deleted) {
    LOG.info("delete({}, {})", id, deleted);
    String msgOperation = "Delete";
    if (deleted == 0) {
      msgOperation = "Restore";
    }

    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);

      SysDept dept = new SysDept();
      dept.setId(id);
      dept.setDeleted(deleted);
      sysDeptMapper.updateBy(dept);
      sqlSession.commit();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK)
          .message(msgOperation + " department failed!").success(false).build();
    } finally {
      sqlSession.close();
    }

    return new JsonResponse.Builder<>(Response.Status.OK)
        .message(msgOperation + " department successfully!").success(true).build();
  }

  @DELETE
  @Path("/deleteBatch")
  @SubmarineApi
  public Response deleteBatch(@QueryParam("ids") String ids) {
    LOG.info("deleteBatch({})", ids.toString());
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);
      sysDeptMapper.deleteBatch(Arrays.asList(ids.split(",")));
      sqlSession.commit();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK)
          .message("Batch delete department failed!").success(false).build();
    } finally {
      sqlSession.close();
    }

    return new JsonResponse.Builder<>(Response.Status.OK)
        .message("Batch delete department successfully!").success(true).build();
  }

  @DELETE
  @Path("/remove")
  @SubmarineApi
  public Response remove(String id) {
    LOG.info("remove({})", id);
    SqlSession sqlSession = null;
    try {
      sqlSession = MyBatisUtil.getSqlSession();
      SysDeptMapper sysDeptMapper = sqlSession.getMapper(SysDeptMapper.class);
      sysDeptMapper.deleteById(id);
      sqlSession.commit();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new JsonResponse.Builder<>(Response.Status.OK)
          .message("Delete department failed!").success(false).build();
    } finally {
      sqlSession.close();
    }

    return new JsonResponse.Builder<>(Response.Status.OK)
        .message("Delete department successfully!").success(true).build();
  }
}
