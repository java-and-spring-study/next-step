package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {

    public void insert(User user) {
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate() {
                @Override
                protected void setValues(User user, PreparedStatement preparedStatement) throws SQLException{
                    preparedStatement.setString(1, user.getUserId());
                    preparedStatement.setString(2, user.getPassword());
                    preparedStatement.setString(3, user.getName());
                    preparedStatement.setString(4, user.getEmail());
                }

                @Override
                protected String createQuery() {
                    return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
                }
            };

            jdbcTemplate.update(user);
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            protected void setValues(User user, PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getUserId());
            }

            @Override
            protected String createQuery() {
                return "UPDATE USERS SET password=?, email=?, name=? WHERE userId=?";
            }
        };

        try {
            jdbcTemplate.update(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAll() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try{
            con = ConnectionManager.getConnection();
            String sql = "SELECT * FROM USERS";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                User foundUser = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                    rs.getString("email"));
                users.add(foundUser);
            }
            return users;
        }finally {
            if(rs != null) {
                rs.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }
            if(con != null) {
                con.close();
            }
        }
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
