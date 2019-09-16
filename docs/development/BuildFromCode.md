<!--
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
-->

## Build Submarine From Source Code

## Prerequisites

+ JDK 1.8
+ Maven 3.3 or later

## Quick Start

### Init submodule
LinkedIn TensorFlow on YARN (TonY) project is included in submarine as a 
submodule. If you just clone submarine project, please initialize the tony
project using the following command.

```
git submodule update --init --recursive
```

### Update submodule
After the initialization, when you want to update tony project, please execute a
command like this.

```
git submodule update --recursive
```

### Building the code

+ Checking releases for licenses

```
mvn clean org.apache.rat:apache-rat-plugin:check
```

+ Create binary distribution with default hadoop version

```
mvn clean install package -DskipTests
```

+ Create binary distribution with hadoop-2.7.x version

```
mvn clean install package -DskipTests -Phadoop-2.7
```

+ Create binary distribution with hadoop-2.9.x version

```
mvn clean install package -DskipTests -Phadoop-2.9
```

+ Create binary distribution with hadoop-3.1.x version

```
mvn clean install package -DskipTests -Phadoop-3.1
```

+ Create binary distribution with hadoop-3.2.x version

```
mvn clean install package -DskipTests -Phadoop-3.2
```

+ Create source code distribution

```
mvn clean install package -DskipTests -Psrc
```

## TonY code modification

If it is needed to make modifications to TonY project, please make sure
both submarine and tony project are committed.
