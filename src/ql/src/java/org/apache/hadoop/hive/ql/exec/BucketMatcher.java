/*!
* Copyright 2010 - 2013 Pentaho Corporation.  All rights reserved.
*
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
* limitations under the License.
*
*/

package org.apache.hadoop.hive.ql.exec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.hadoop.fs.Path;

public interface BucketMatcher {

  public List<Path> getAliasBucketFiles(String currentInputFile, String refTableAlias, String alias);

  public void setAliasBucketFileNameMapping(
      LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> aliasBucketFileNameMapping);

  public LinkedHashMap<String, Integer> getBucketFileNameMapping();

  public void setBucketFileNameMapping(LinkedHashMap<String, Integer> bucketFileNameMapping);
}
