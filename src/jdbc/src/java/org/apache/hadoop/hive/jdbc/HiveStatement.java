/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hive.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import org.apache.hadoop.hive.service.HiveInterface;
import org.apache.hadoop.hive.service.HiveServerException;

/**
 * HiveStatement.
 *
 */
public class HiveStatement implements java.sql.Statement {
  private JdbcSessionState session;
  private HiveInterface client;
  /**
   * We need to keep a reference to the result set to support the following:
   * <code>
   * statement.execute(String sql);
   * statement.getResultSet();
   * </code>.
   */
  private ResultSet resultSet = null;

  /**
   * The maximum number of rows this statement should return (0 => all rows).
   */
  private int maxRows = 0;

  /**
   * Add SQLWarnings to the warningChain if needed.
   */
  private SQLWarning warningChain = null;

  /**
   * Keep state so we can fail certain calls made after close().
   */
  private boolean isClosed = false;

  /**
   *
   */
  public HiveStatement(JdbcSessionState session, HiveInterface client) {
    this.session = session;
    this.client = client;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#addBatch(java.lang.String)
   */

  public void addBatch(String sql) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#cancel()
   */

  public void cancel() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#clearBatch()
   */

  public void clearBatch() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#clearWarnings()
   */

  public void clearWarnings() throws SQLException {
    warningChain = null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#close()
   */

  public void close() throws SQLException {
    client = null;
    resultSet = null;
    isClosed = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#execute(java.lang.String)
   */

  public boolean execute(String sql) throws SQLException {
    ResultSet rs = executeQuery(sql);

    // TODO: this should really check if there are results, but there's no easy
    // way to do that without calling rs.next();
    return rs != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#execute(java.lang.String, int)
   */

  public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#execute(java.lang.String, int[])
   */

  public boolean execute(String sql, int[] columnIndexes) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#execute(java.lang.String, java.lang.String[])
   */

  public boolean execute(String sql, String[] columnNames) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#executeBatch()
   */

  public int[] executeBatch() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#executeQuery(java.lang.String)
   */

  public ResultSet executeQuery(String sql) throws SQLException {
    if (isClosed) {
      throw new SQLException("Can't execute after statement has been closed");
    }

    try {
      resultSet = null;
      client.execute(sql);
    } catch (HiveServerException e) {
      throw new SQLException(e.getMessage(), e.getSQLState(), e.getErrorCode());
    } catch (Exception ex) {
      throw new SQLException(ex.toString(), "08S01");
    }
    resultSet = new HiveQueryResultSet(client, this, maxRows);
    return resultSet;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String)
   */

  public int executeUpdate(String sql) throws SQLException {
    try {
      client.execute(sql);
    } catch (Exception ex) {
      throw new SQLException(ex.toString());
    }
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String, int)
   */

  public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String, int[])
   */

  public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#executeUpdate(java.lang.String, java.lang.String[])
   */

  public int executeUpdate(String sql, String[] columnNames) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getConnection()
   */

  public Connection getConnection() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getFetchDirection()
   */

  public int getFetchDirection() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getFetchSize()
   */

  public int getFetchSize() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getGeneratedKeys()
   */

  public ResultSet getGeneratedKeys() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getMaxFieldSize()
   */

  public int getMaxFieldSize() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getMaxRows()
   */

  public int getMaxRows() throws SQLException {
    return maxRows;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getMoreResults()
   */

  public boolean getMoreResults() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getMoreResults(int)
   */

  public boolean getMoreResults(int current) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getQueryTimeout()
   */

  public int getQueryTimeout() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getResultSet()
   */

  public ResultSet getResultSet() throws SQLException {
    return resultSet;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getResultSetConcurrency()
   */

  public int getResultSetConcurrency() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getResultSetHoldability()
   */

  public int getResultSetHoldability() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getResultSetType()
   */

  public int getResultSetType() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getUpdateCount()
   */

  public int getUpdateCount() throws SQLException {
    return 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#getWarnings()
   */

  public SQLWarning getWarnings() throws SQLException {
    return warningChain;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#isClosed()
   */

  public boolean isClosed() throws SQLException {
    return isClosed;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#isPoolable()
   */

  public boolean isPoolable() throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setCursorName(java.lang.String)
   */

  public void setCursorName(String name) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setEscapeProcessing(boolean)
   */

  public void setEscapeProcessing(boolean enable) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setFetchDirection(int)
   */

  public void setFetchDirection(int direction) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setFetchSize(int)
   */

  public void setFetchSize(int rows) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setMaxFieldSize(int)
   */

  public void setMaxFieldSize(int max) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setMaxRows(int)
   */

  public void setMaxRows(int max) throws SQLException {
    if (max < 0) {
      throw new SQLException("max must be >= 0");
    }
    maxRows = max;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setPoolable(boolean)
   */

  public void setPoolable(boolean poolable) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Statement#setQueryTimeout(int)
   */

  public void setQueryTimeout(int seconds) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */

  public boolean isWrapperFor(Class<?> iface) throws SQLException {
    throw new SQLException("Method not supported");
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.sql.Wrapper#unwrap(java.lang.Class)
   */

  public <T> T unwrap(Class<T> iface) throws SQLException {
    throw new SQLException("Method not supported");
  }

}