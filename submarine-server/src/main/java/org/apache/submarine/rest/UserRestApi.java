/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. See accompanying LICENSE file.
 */
package org.apache.submarine.rest;

import com.google.gson.Gson;
import org.apache.submarine.annotation.SubmarineApi;
import org.apache.submarine.entity.Action;
import org.apache.submarine.entity.Permission;
import org.apache.submarine.entity.Role;
import org.apache.submarine.entity.UserInfo;
import org.apache.submarine.server.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/user")
@Produces("application/json")
@Singleton
public class UserRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(UserRestApi.class);

  private static final Gson gson = new Gson();

  @Inject
  public UserRestApi() {
  }

  @GET
  @Path("/info")
  @SubmarineApi
  public Response info() {
    List<Action> actions = new ArrayList<Action>();
    Action action1 = new Action("add", false, "新增");
    Action action2 = new Action("query", false, "查询");
    Action action3 = new Action("get", false, "详情");
    Action action4 = new Action("update", false, "修改");
    Action action5 = new Action("delete", false, "删除");
    actions.add(action1);
    actions.add(action2);
    actions.add(action3);
    actions.add(action4);
    actions.add(action5);

    Permission.Builder permissionBuilder1 = new Permission.Builder("admin", "dashboard", "仪表盘");
    Permission permission1 = permissionBuilder1.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder2 = new Permission.Builder("admin", "exception", "异常页面权限");
    Permission permission2 = permissionBuilder2.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder3 = new Permission.Builder("admin", "result", "结果权限");
    Permission permission3 = permissionBuilder3.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder4 = new Permission.Builder("admin", "profile", "详细页权限");
    Permission permission4 = permissionBuilder4.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder5 = new Permission.Builder("admin", "table", "表格权限");
    Permission permission5 = permissionBuilder5.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder6 = new Permission.Builder("admin", "form", "表单权限");
    Permission permission6 = permissionBuilder6.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder7 = new Permission.Builder("admin", "order", "订单管理");
    Permission permission7 = permissionBuilder7.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder8 = new Permission.Builder("admin", "permission", "权限管理");
    Permission permission8 = permissionBuilder8.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder9 = new Permission.Builder("admin", "role", "角色管理");
    Permission permission9 = permissionBuilder9.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder10 = new Permission.Builder("admin", "table", "桌子管理");
    Permission permission10 = permissionBuilder10.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder11 = new Permission.Builder("admin", "user", "用户管理");
    Permission permission11 = permissionBuilder11.actions(actions).actionEntitySet(actions).build();

    Permission.Builder permissionBuilder12 = new Permission.Builder("admin", "support", "超级模块");
    Permission permission12 = permissionBuilder12.actions(actions).actionEntitySet(actions).build();

    List<Permission> permissions = new ArrayList<Permission>();
    permissions.add(permission1);
    permissions.add(permission2);
    permissions.add(permission3);
    permissions.add(permission4);
    permissions.add(permission5);
    permissions.add(permission6);
    permissions.add(permission7);
    permissions.add(permission8);
    permissions.add(permission9);
    permissions.add(permission10);
    permissions.add(permission11);
    permissions.add(permission12);

    Role.Builder roleBuilder = new Role.Builder("admin", "管理员");
    Role role = roleBuilder.describe("拥有所有权限").status(1).creatorId("system")
        .createTime(1497160610259L).deleted(0).permissions(permissions).build();

    UserInfo.Builder userInfoBuilder = new UserInfo.Builder("4291d7da9005377ec9aec4a71ea837f", "liuxun");
    UserInfo userInfo = userInfoBuilder.username("admin").password("")
        .avatar("/avatar2.jpg").status(1).telephone("").lastLoginIp("27.154.74.117")
        .lastLoginTime(1534837621348L).creatorId("admin")
        .createTime(1497160610259L).merchantCode("TLif2btpzg079h15bk")
        .deleted(0).roleId("admin").role(role).build();

    return new JsonResponse.Builder<UserInfo>(Response.Status.OK).success(true).result(userInfo).build();
  }

  @POST
  @Path("/2step-code")
  @SubmarineApi
  public Response step() {
    String data = "{stepCode:1}";

    return new JsonResponse.Builder<>(Response.Status.OK).success(true).result(data).build();
  }

  @GET
  @Path("/list")
  @SubmarineApi
  public Response queryPageList(@QueryParam("column") String column,
                                @QueryParam("field") String field,
                                @QueryParam("pageNo") int pageNo,
                                @QueryParam("pageSize") int pageSize) {
    LOG.info("userList column:{}, field:{}, pageNo:{}, pageSize:{}", column, field, pageNo, pageSize);

    String data = "";

    return new JsonResponse.Builder<>(Response.Status.OK).success(true).result(data).build();
  }
}
