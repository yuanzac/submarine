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
package org.apache.submarine.database.entity;

public class SysDict extends BaseEntity {

  private String dictCode;

  private String dictName;

  private String description;

  private Integer deleted;

  private Integer type;

  public void setDictCode(String dictCode) {
    this.dictCode = dictCode;
  }

  public void setDictName(String dictName) {
    this.dictName = dictName;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDeleted(Integer deleted) {
    this.deleted = deleted;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getDictCode() {
    return dictCode;
  }

  public String getDictName() {
    return dictName;
  }

  public String getDescription() {
    return description;
  }

  public Integer getDeleted() {
    return deleted;
  }
}
