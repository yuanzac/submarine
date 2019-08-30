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
package org.apache.submarine.database.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.submarine.database.MyBatisUtil;
import org.apache.submarine.database.entity.SysDictItem;
import org.apache.submarine.database.mappers.SysDictItemMapper;
import org.apache.submarine.rest.SysDictRestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SysDictItemService {
  private static final Logger LOG = LoggerFactory.getLogger(SysDictRestApi.class);

  public List<SysDictItem> queryDictByCode(String dictCode) {
    SqlSession sqlSession = MyBatisUtil.getSqlSession();
    SysDictItemMapper dictItemMapper = sqlSession.getMapper(SysDictItemMapper.class);

    List<SysDictItem> dictItems = null;
    try {
      dictItems = dictItemMapper.queryDictByCode(dictCode);
      sqlSession.commit();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } finally {
      sqlSession.close();
    }
    return dictItems;
  }
}
