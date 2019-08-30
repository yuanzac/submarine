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
import com.google.gson.reflect.TypeToken;
import org.apache.submarine.annotation.SubmarineApi;
import org.apache.submarine.database.entity.SysUser;
import org.apache.submarine.database.service.SysUserService;
import org.apache.submarine.server.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/auth")
@Produces("application/json")
@Singleton
public class LoginRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(LoginRestApi.class);
  private SysUserService userService = new SysUserService();
  private static final Gson gson = new Gson();

  @Inject
  public LoginRestApi() {
  }

  @POST
  @Path("/login")
  @SubmarineApi
  public Response login(String loginParams) {
    HashMap<String, String> mapParams
        = gson.fromJson(loginParams, new TypeToken<HashMap<String, String>>() {}.getType());
    String username = mapParams.get("username");
    String password = mapParams.get("password");

    SysUser sysUser = userService.getUserByName(username, password);
    if (null == sysUser) {
      LOG.error("Cannot get data from the database");
    } else {
      sysUser.setRoleId("admin");
      sysUser.setToken("mock_token");
    }

    return new JsonResponse.Builder<SysUser>(Response.Status.OK).success(true).result(sysUser).build();
  }

  @POST
  @Path("/2step-code")
  @SubmarineApi
  public Response step() {
    String data = "{stepCode:1}";

    return new JsonResponse.Builder<String>(Response.Status.OK).success(true).result(data).build();
  }
}
