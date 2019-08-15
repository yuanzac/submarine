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
package org.apache.submarine.entity;

public class User {
  private final String id;
  private final String name;
  private final String username;
  private final String password;
  private final String avatar;
  private final int status;
  private final String telephone;
  private final String lastLoginIp;
  private final long lastLoginTime;
  private final String creatorId;
  private final long createTime;
  private final int deleted;
  private final String roleId;
  private final String lang;
  private final String token;

  private User(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.username = builder.username;
    this.password = builder.password;
    this.avatar = builder.avatar;
    this.status = builder.status;
    this.telephone = builder.telephone;
    this.lastLoginIp = builder.lastLoginIp;
    this.lastLoginTime = builder.lastLoginTime;
    this.creatorId = builder.creatorId;
    this.createTime = builder.createTime;
    this.deleted = builder.deleted;
    this.roleId = builder.roleId;
    this.lang = builder.lang;
    this.token = builder.token;
  }

  public static class Builder{
    private final String id;
    private final String name;
    private String username;
    private String password;
    private String avatar;
    private int status = 0;
    private String telephone;
    private String lastLoginIp;
    private long lastLoginTime;
    private String creatorId;
    private long createTime;
    private int deleted = 0;
    private String roleId;
    private String lang;
    private String token;

    public Builder(String id, String name) {
      this.id = id;
      this.name = name;
    }

    public Builder username(String username){
      this.username = username;
      return this;
    }

    public Builder password(String password){
      this.password = password;
      return this;
    }

    public Builder avatar(String avatar){
      this.avatar = avatar;
      return this;
    }

    public Builder status(int status){
      this.status = status;
      return this;
    }

    public Builder lastLoginIp(String lastLoginIp){
      this.lastLoginIp = lastLoginIp;
      return this;
    }

    public Builder lastLoginTime(long lastLoginTime){
      this.lastLoginTime = lastLoginTime;
      return this;
    }

    public Builder creatorId(String creatorId){
      this.creatorId = creatorId;
      return this;
    }

    public Builder telephone(String telephone){
      this.telephone = telephone;
      return this;
    }

    public Builder createTime(long createTime){
      this.createTime = createTime;
      return this;
    }

    public Builder deleted(int deleted){
      this.deleted = deleted;
      return this;
    }

    public Builder roleId(String roleId){
      this.roleId = roleId;
      return this;
    }

    public Builder lang(String lang){
      this.lang = lang;
      return this;
    }

    public Builder token(String token){
      this.token = token;
      return this;
    }

    public User build(){
      return new User(this);
    }
  }

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", username=" + username +
        ", password='" + password + '\'' +
        ", avatar=" + avatar +
        ", status='" + status + '\'' +
        ", telephone='" + telephone + '\'' +
        ", lastLoginIp=" + lastLoginIp +
        ", creatorId='" + creatorId + '\'' +
        ", createTime='" + createTime + '\'' +
        ", deleted='" + deleted + '\'' +
        ", roleId='" + roleId + '\'' +
        ", lang='" + lang + '\'' +
        ", token='" + token + '\'' +
        '}';
  }
}
