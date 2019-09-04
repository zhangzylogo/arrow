/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.arrow.adapter.jdbc.consumer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.arrow.vector.IntVector;

/**
 * Consumer which consume int type values from {@link ResultSet}.
 * Write the data to {@link org.apache.arrow.vector.IntVector}.
 */
public class IntConsumer implements JdbcConsumer<IntVector> {

  private IntVector vector;
  private final int columnIndexInResultSet;

  private int currentIndex;

  /**
   * Instantiate a IntConsumer.
   */
  public IntConsumer(IntVector vector, int index) {
    this.vector = vector;
    this.columnIndexInResultSet = index;
  }

  @Override
  public void consume(ResultSet resultSet) throws SQLException {
    int value = resultSet.getInt(columnIndexInResultSet);
    if (!resultSet.wasNull()) {
      vector.setSafe(currentIndex++, value);
    }
  }

  @Override
  public void close() throws Exception {
    this.vector.close();
  }

  @Override
  public void resetValueVector(IntVector vector) {
    this.vector = vector;
    this.currentIndex = 0;
  }
}
