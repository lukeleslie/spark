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

package org.apache.spark.network.util;

/**
 * A central location that tracks all the settings we expose to users.
 */
public class TransportConf {
  private final ConfigProvider conf;

  public TransportConf(ConfigProvider conf) {
    this.conf = conf;
  }

  /** Port the server listens on. Default to a random port. */
  public int serverPort() { return conf.getInt("spark.shuffle.io.port", 0); }

  /** IO mode: nio or epoll */
  public String ioMode() { return conf.get("spark.shuffle.io.mode", "NIO").toUpperCase(); }

  /** Connect timeout in secs. Default 120 secs. */
  public int connectionTimeoutMs() {
    return conf.getInt("spark.shuffle.io.connectionTimeout", 120) * 1000;
  }

  /** Requested maximum length of the queue of incoming connections. Default -1 for no backlog. */
  public int backLog() { return conf.getInt("spark.shuffle.io.backLog", -1); }

  /** Number of threads used in the server thread pool. Default to 0, which is 2x#cores. */
  public int serverThreads() { return conf.getInt("spark.shuffle.io.serverThreads", 0); }

  /** Number of threads used in the client thread pool. Default to 0, which is 2x#cores. */
  public int clientThreads() { return conf.getInt("spark.shuffle.io.clientThreads", 0); }

  /**
   * Receive buffer size (SO_RCVBUF).
   * Note: the optimal size for receive buffer and send buffer should be
   *  latency * network_bandwidth.
   * Assuming latency = 1ms, network_bandwidth = 10Gbps
   *  buffer size should be ~ 1.25MB
   */
  public int receiveBuf() { return conf.getInt("spark.shuffle.io.receiveBuffer", -1); }

  /** Send buffer size (SO_SNDBUF). */
  public int sendBuf() { return conf.getInt("spark.shuffle.io.sendBuffer", -1); }
}