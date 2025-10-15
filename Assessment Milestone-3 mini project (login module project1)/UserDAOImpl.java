package com.app.dao;

import com.app.model.User;
import java.sql.*;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/spring_login";
    private static final String USER = "root";
    private static final String PASS = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(User user) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO users (username,password,empid,email) VALUES (?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmpid());
            ps.setString(4, user.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User validateUser(String username, String password) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
