package com.mountisome.aquareminder.utils;

import com.mountisome.aquareminder.bean.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.0.1/aquareminder?characterEncoding=utf-8";
    private static String username = "root"; // 用户名
    private static String password = "root"; // 密码

    public static Connection getConn() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = (Connection) DriverManager.getConnection(url, username, password); // 获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 登录
    public static int login(User user) {
        int result = 2; // 用户不存在
        Connection connection = getConn();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from user where name ='" + user.getName() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    if (user.getName().equals(resultSet.getString("name"))) {
                        if (user.getPwd().equals(resultSet.getString("pwd"))) {
                            result = 0; // 用户存在并且密码正确
                        }
                        else {
                            result = 1; // 密码不正确
                        }
                        break;
                    }
                }
                connection.close();
                statement.close();
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 返回user对象
    public static User queryUser(String name) {
        Connection connection = getConn();
        User user = new User();
        try {
            String sql = "select * from user where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int water = resultSet.getInt("water");
                int energy = resultSet.getInt("energy");
                int day = resultSet.getInt("day");
                int average_water = resultSet.getInt("average_water");
                int average_time = resultSet.getInt("average_time");
                user.setWater(water);
                user.setEnergy(energy);
                user.setDay(day);
                user.setAverage_water(average_water);
                user.setAverage_time(average_time);
            }
            preparedStatement.close();
            connection.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // 检查用户名是否存在
    public static int checkUser(User user) {
        int result = 0; // 用户不存在
        Connection connection = getConn();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from user where name ='" + user.getName() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    if (user.getName().equals(resultSet.getString("name"))) {
                        result = 1; // 用户已存在
                        break;
                    }
                }
                connection.close();
                statement.close();
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 注册
    public static void register(User user) {
        Connection connection = getConn();
        try {
            String sql = "insert into user(name,pwd,mailbox,water,energy,planted,day,average_water," +
                    "average_time,total_water,total_time) " + "values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPwd());
            preparedStatement.setString(3, user.getMailbox());
            preparedStatement.setInt(4, 0);
            preparedStatement.setInt(5, 0);
            preparedStatement.setString(6, "0000");
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, 0);
            preparedStatement.setInt(9, 0);
            preparedStatement.setInt(10, 0);
            preparedStatement.setInt(11, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 添加水量
    public static void addWater(String name, int water) {
        Connection connection = getConn();
        try {
            String sql = "update user set water = ? where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, water);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 一天已过，更新饮水量
    public static void updateWater(String name) {
        Connection connection = getConn();
        try {
            String sql = "update user set water = ? where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 一天已过，更新能量
    public static void updateEnergy(String name) {
        Connection connection = getConn();
        try {
            String sql = "update user set energy = energy + 1 where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 兑换树木
    public static void exchangeTree(String name, int energy, int treeId) {
        Connection connection = getConn();
        try {
            String sql = "update user set energy = energy - ? where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, energy);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            String planted = "0000";
            StringBuilder stringBuilder = new StringBuilder(planted);
            stringBuilder.setCharAt(treeId, '1');
            planted = stringBuilder.toString();
            sql = "update user set planted = ? where name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, planted);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 种植的树木
    public static String plantedTree(String name) {
        String planted = null;
        Connection connection = getConn();
        try {
            String sql = "select * from user where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                planted = resultSet.getString("planted");
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return planted;
    }

    // 更新喝水天数
    public static void updateDay(String name) {
        Connection connection = getConn();
        try {
            String sql = "update user set day = day + 1 where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 计算总喝水量
    public static void updateTotalWater(String name, int water) {
        Connection connection = getConn();
        try {
            String sql = "update user set total_water = total_water + ? where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, water);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 更新喝水总次数
    public static void updateTotalTime(String name) {
        Connection connection = getConn();
        try {
            String sql = "update user set total_time = total_time + 1 where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 更新平均喝水量
    public static void updateAverageWater(String name) {
        Connection connection = getConn();
        try {
            String sql = "select * from user where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            int day = 1;
            int total_water = 0;
            while (resultSet.next()) {
                day = resultSet.getInt("day");
                total_water = resultSet.getInt("total_water");
            }
            if (day == 0) {
                day = 1;
            }
            sql = "update user set average_water = ? where name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, total_water / day);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 更新平均次数
    public static void updateAverageTime(String name) {
        Connection connection = getConn();
        try {
            String sql = "select * from user where name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            int day = 1;
            int total_time = 0;
            while (resultSet.next()) {
                day = resultSet.getInt("day");
                total_time = resultSet.getInt("total_time");
            }
            if (day == 0) {
                day = 1;
            }
            sql = "update user set average_time = ? where name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, total_time / day);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 查询排名
    public static List<User> getRank() {
        Connection connection = getConn();
        List<User> userList = new ArrayList<>();
        try {
            String sql = "select name, energy from user order by energy desc";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                String name = resultSet.getString("name");
                int energy = resultSet.getInt("energy");
                user.setName(name);
                user.setEnergy(energy);
                userList.add(user);
            }
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }


}
