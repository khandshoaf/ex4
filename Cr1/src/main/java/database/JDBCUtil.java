package database;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection(){
        Connection c = null;
        try {
            Driver driver = new Driver();
            //Đăng ký MySql driver với MySqlManager
            DriverManager.registerDriver(new Driver());

            //Các thông số
            String url = "jdbc:mySql://localhost:3306/ontap";
            String username = "root";
            String password = "123456";

            //tạo kết nối
            c = DriverManager.getConnection(url,username,password);
            System.out.println("Kết nối thành công đến csdl.");
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại đến csdl.");
            e.printStackTrace();
        }
        return c;
    }

    public static void closeConnection(Connection c){

    }
}
