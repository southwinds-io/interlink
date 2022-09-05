/*
    Interlink Configuration Management Database
    © 2018-Present - SouthWinds Tech Ltd - www.southwinds.io

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0

    Contributors to this project, hereby assign copyright in their code to the
    project, to be licensed under the same terms as the rest of the code.
*/

package io.southwinds.interlink.db;

import com.zaxxer.hikari.HikariDataSource;
import io.southwinds.interlink.conf.Config;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DataSourceFactory {
    private HikariDataSource ds;
    private Connection conn;

    private final Config cfg;

    public DataSourceFactory(Config cfg) {
        this.cfg = cfg;
    }

    private HikariDataSource instance() {
        if (ds == null) {
            ds = new HikariDataSource();
            System.out.println(String.format("JDBC ==> Setting JDBC URL to: '%s'", cfg.getConnString()));
            ds.setJdbcUrl(cfg.getConnString());
            ds.setUsername(cfg.getDbuser());
            ds.setPassword(cfg.getDbpwd());
            ds.setPoolName("interlink-connection-pool");
            ds.addDataSourceProperty("cachePrepStmts", cfg.isCachePrepStmts());
            ds.addDataSourceProperty("prepStmtCacheSize", cfg.getPrepStmtCacheSize());
            ds.addDataSourceProperty("prepStmtCacheSqlLimit", cfg.getPrepStmtCacheSqlLimit());
            ds.addDataSourceProperty("useServerPrepStmts", cfg.isUseServerPrepStmts());
        }
        return ds;
    }

    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = instance().getConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public void closeConn() {
        try {
            if (conn != null || !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}