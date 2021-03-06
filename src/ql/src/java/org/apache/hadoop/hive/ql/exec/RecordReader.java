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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Writable;

/**
 * RecordReader.
 *
 */
public interface RecordReader {

  void initialize(InputStream in, Configuration conf, Properties tbl) throws IOException;

  Writable createRow() throws IOException;

  /**
   * Returns the number of bytes that we consumed.
   * -1 means end of stream.
   */
  int next(Writable row) throws IOException;

  void close() throws IOException;
}
