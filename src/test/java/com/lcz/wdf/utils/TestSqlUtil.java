package com.lcz.wdf.utils;

import com.lcz.wdf.TestPersistenceConfig;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 测试的sql工具类
 **/
public class TestSqlUtil {

    /**
     * 执行sql文件，支持通配符的文件路径
     */
    public static void execSqlFile(String sqlFile) throws SQLException, ClassNotFoundException, IOException {
        Connection conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        runner.setAutoCommit(false);
        runner.setStopOnError(true);
        runner.setSendFullScript(false);
        runner.setLogWriter(null);
        runner.runScript(Resources.getResourceAsReader(sqlFile));
        conn.close();
    }

    /**
     * 获取测试数据库的连接
     */
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(TestPersistenceConfig.TEST_DB_URL, TestPersistenceConfig.TEST_DB_USER, TestPersistenceConfig.TEST_DB_PASSWORD);
    }

    /**
     * 清空表数据
     */
    public static void clearTableDatas(String tableName) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        Statement stmt = connection.createStatement();
        String sql = "truncate `" + tableName + "`";
        stmt.execute(sql);
        stmt.close();
        connection.close();
    }
}
