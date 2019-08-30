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
package org.apache.submarine.database.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.apache.submarine.rest.SysUserRestApi;
import org.apache.submarine.server.JsonResponse;
import org.junit.Test;

import javax.ws.rs.core.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class DictAnnotationTest {
  private SysUserRestApi userRestApi = new SysUserRestApi();

  private static final Gson gson = new Gson();

  @Test
  public void userSexDictAnnotationTest() {
    Response response = userRestApi.queryPageList(null, null, 1, 10);

    String entity = (String) response.getEntity();
    Type type = new TypeToken<JsonResponse>() {}.getType();
    JsonResponse jsonResponse = gson.fromJson(entity, type);

    LinkedTreeMap<String, Object> linkedTreeMap = (LinkedTreeMap<String, Object>) jsonResponse.getResult();
    ArrayList<LinkedTreeMap<String, Object>> arrayList
        = (ArrayList<LinkedTreeMap<String, Object>>) linkedTreeMap.get("records");

    assertTrue(arrayList.get(0).containsKey("sex"));
    assertTrue(arrayList.get(0).containsKey("sex" + DictAnnotation.DICT_SUFFIX));
  }
}
