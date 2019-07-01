package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class AbstractDao {

    protected Object find(String sql , Object[] params){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object obj = null;

        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String user = "root";
        String password = "root";

        try {
            //1、加载驱动
            Class.forName(driverClassName);
            //2、获取连接
            DriverManager.getConnection(url,user,password);
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length;i++){
                ps.setObject(i + 1 , params[i]);
            }
            rs = ps.executeQuery();




        } catch (Exception e) {
        }




        return obj;
    }

}
