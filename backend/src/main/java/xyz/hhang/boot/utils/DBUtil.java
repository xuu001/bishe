package xyz.hhang.boot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File( "../resources/config.properties")));
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url= properties.getProperty("url");
        String user = properties.getProperty("user");
        String password= properties.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }
    public static void close(Connection con, Statement st, ResultSet rs) throws SQLException {
        if (con != null) {
            con.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
    }
}
