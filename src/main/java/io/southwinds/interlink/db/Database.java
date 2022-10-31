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

import io.southwinds.interlink.FileUtil;
import io.southwinds.interlink.conf.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.sql.*;

@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
class Database {
    Logger log = LoggerFactory.getLogger(Database.class);
    private PreparedStatement stmt;
    private final DataSourceFactory ds;
    private final FileUtil file;
    private final Config cfg;
    private Version version;

    @Autowired
    public Database(DataSourceFactory ds, FileUtil file, Config cfg) {
        this.ds = ds;
        this.file = file;
        this.cfg = cfg;
    }

    class Version {
        String app;
        String db;

        @Override
        public String toString() {
            if (app != null && db != null) {
                return String.format("App Version: '%s'; Db Version: '%s'.", app, db);
            } else {
                return super.toString();
            }
        }
    }

    void prepare(String sql) throws SQLException {
        stmt = ds.getConn().prepareStatement(sql);
    }

    void setString(int parameterIndex, String value) throws SQLException {
        stmt.setString(parameterIndex, value);
    }

    void setBoolean(int parameterIndex, boolean value) throws SQLException {
        stmt.setBoolean(parameterIndex, value);
    }

    void setBinaryStream(int parameterIndex, InputStream value) throws SQLException {
        stmt.setBinaryStream(parameterIndex, value);
    }

    void setString(int parameterIndex, String value, String defaultValue) throws SQLException {
        if (value == null || value.trim().length() == 0) {
            value = defaultValue;
        }
        stmt.setString(parameterIndex, value);
    }

    void setArray(int parameterIndex, String[] value) throws SQLException {
        stmt.setArray(parameterIndex, ds.getConn().createArrayOf("varchar", value));
    }

    void setInt(int parameterIndex, Integer value) throws SQLException {
        stmt.setInt(parameterIndex, value);
    }

    void setShort(int parameterIndex, Short value) throws SQLException {
        stmt.setShort(parameterIndex, value);
    }

    void setObject(int parameterIndex, Object value) throws SQLException {
        stmt.setObject(parameterIndex, value);
    }

    void setChar(int parameterIndex, char value, char defaultValue) throws SQLException {
        // if no character value assigned, then use the default value
        if (value == '\u0000') {
            value = defaultValue;
        }
        stmt.setString(parameterIndex, String.valueOf(value));
    }

    void setObjectRange(int fromIndex, int toIndex, Object value) throws SQLException {
        for (int i = fromIndex; i < toIndex + 1; i++) {
            setObject(i, null);
        }
    }

    String executeQueryAndRetrieveStatus(String query_name) throws SQLException {
        ResultSet set = stmt.executeQuery();
        if (set.next()) {
            return set.getString(query_name);
        }
        throw new RuntimeException(String.format("Failed to execute query '%s'", query_name));
    }

    ResultSet executeQuery() throws SQLException {
        return stmt.executeQuery();
    }

    ResultSet executeQuerySingleRow() throws SQLException {
        String result = null;
        ResultSet set = stmt.executeQuery();
        if (set.next()) {
            return set;
        }
        // if no row if found then return null
        return null;
    }

    boolean execute() throws SQLException {
        return stmt.execute();
    }

    void close() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            ds.closeConn();
        }
        catch (Exception ex) {
            System.out.println("WARNING: failed to close database statement.");
            ex.printStackTrace();
        }
    }

    private void runScriptFromString(String adminPwd, String script, String targetDb) throws SQLException {
        Connection conn = DriverManager.getConnection(String.format("%s/%s", cfg.getDbServerUrl(), targetDb), "postgres", adminPwd);
        Statement stmt = conn.createStatement();
        stmt.execute(script);
        stmt.close();
        conn.close();
    }

    /**
     * determines if the interlink database exists
     * @return true if the interlink database exists, otherwise false
     */
    public boolean exists() {
        boolean exists = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(String.format("%s/postgres", cfg.getDbServerUrl()), "postgres", new String(cfg.getDbAdminPwd()));
            Statement stmt = conn.createStatement();
            if (stmt.execute(String.format("SELECT 1 from pg_database WHERE datname='%s';", cfg.getDbName()))){
                ResultSet set = stmt.getResultSet();
                exists = set.next();
                if (exists) {
                    log.info(String.format("Check database %s exists: OK.", cfg.getDbName()));
                } else {
                    log.info(String.format("Database %s does not exist.", cfg.getDbName()));
                }
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Check database %s exists: FAILED - ", cfg.getDbName()), e);
        }
        return exists;
    }

    Version getVersion(){
        return getVersion(false);
    }

    /**
     * gets the version information from the database
     * @return a varsion instance containing app and db versions
     */
    Version getVersion(boolean refresh) {
        if (version == null || refresh) {
            version = new Version();
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(String.format("%s/%s", cfg.getDbServerUrl(), cfg.getDbName()), "postgres", new String(cfg.getDbAdminPwd()));
                Statement stmt = conn.createStatement();
                if (stmt.execute(String.format("SELECT * from version ORDER BY time DESC LIMIT 1;"))) {
                    ResultSet set = stmt.getResultSet();
                    if (set.next()) {
                        version.app = set.getString("appversion");
                        version.db = set.getString("dbversion");
                    }
                }
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException("Failed to retrieve version from database", e);
            }
        }
        return version;
    }

    private void setVersion(String appVer, String dbVer, String desc, String scriptSrc) {
        log.info(String.format("Recording version of database installed: %s:%s.", appVer, dbVer));
        try {
            prepare("SELECT link_set_version(" +
                    "?::character varying," +
                    "?::character varying," +
                    "?::text," +
                    "?::character varying" +
                    ")");
            setString(1, appVer);
            setString(2, dbVer);
            setString(3, desc);
            setString(4,scriptSrc);
            executeQuery();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to set version in database.", ex);
        }
    }

    /**
     * deletes the database.
     */
    void deleteDb() {
        try {
            // kills all existing connections and drops the database
            runScriptFromString(new String(cfg.getDbAdminPwd()),
                String.format(
                    "SELECT pid, pg_terminate_backend(pid) \n" +
                    "FROM pg_stat_activity \n" +
                    "WHERE datname = '%s' AND pid <> pg_backend_pid();\n" +
                    "DROP DATABASE IF EXISTS %s;\n" +
                    "DROP USER %s", cfg.getDbName(), cfg.getDbName(), cfg.getDbUser()), "postgres");
        } catch (Exception e) {
            log.warn(String.format("Failed to drop database '%s' after deployment failure: %s.", cfg.getDbName(), e.getMessage()));
        }
    }

    /**
     * drops all interlink functions in the database
     */
    void dropFunctions() {
        StringBuilder dropStatement = new StringBuilder();
        // retrieve all function names
        String query = "SELECT routines.routine_name as fx_name\n" +
                "FROM information_schema.routines\n" +
                "WHERE routines.specific_schema='public'\n" +
                "AND routines.routine_name LIKE 'link_%'\n" +
                "ORDER BY routines.routine_name;";
        try {
            prepare(query);
            ResultSet set = executeQuery();
            while (set.next()){
                dropStatement.append(String.format("DROP FUNCTION IF EXISTS %s;\n", set.getString("fx_name")));
            }
            runScriptFromString(new String(cfg.getDbAdminPwd()), dropStatement.toString(), cfg.getDbName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
