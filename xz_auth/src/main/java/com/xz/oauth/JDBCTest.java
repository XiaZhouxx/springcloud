package com.xz.oauth;

import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xz
 * @ClassName JDBCTest
 * @Description
 * @date 2020/4/5 0005 12:11
 **/
public class JDBCTest {
    public static void main(String[] args) throws SQLException {
        String user = "root";
        String password = "118756";
        String url = "jdbc:mysql://47.107.246.54:3306/blog";
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement statement = connection.prepareStatement("select * from blog.tb_user");
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) {
            System.out.println(resultSet.getString(2));
        }
        Thread t = new Thread();
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        service.getPoolSize();
        service.getTaskCount();
        service.getLargestPoolSize();
        service.getCompletedTaskCount();
    }
}
