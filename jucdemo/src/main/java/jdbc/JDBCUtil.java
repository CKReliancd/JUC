package jdbc;

import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    /**
     * 获取连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception{
        Properties pros = new Properties();
        pros.load(JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        String driverClassName = pros.getProperty("driverClassName");
        String url = pros.getProperty("url");
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");

        //1. 加载驱动
        Class.forName(driverClassName);

        //2. 获取连接
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭连接
     * @param conn
     * @param ps
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     * @param conn
     * @param ps
     */
    public static void close(Connection conn, PreparedStatement ps){
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

