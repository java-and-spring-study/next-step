package next.dao;

import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.exception.DataAccessException;
import next.model.User;

public class UserDao {
    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        PreparedStatementSetter statementSetter = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        };
        JdbcTemplate insertJdbcTemplate = new JdbcTemplate();

        insertJdbcTemplate.update(sql, statementSetter);
    }


    public void update(User user) {
        // TODO 구현 필요함.
        String sql = "UPDATE USERS " +
                "SET userId = ?, password = ?, name = ?, email = ? " +
                "WHERE userId = ?";

        PreparedStatementSetter statementSetter = pstmt -> {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserId());
        };

        new JdbcTemplate().update(sql, statementSetter);
    }


    public List<User> findAll() {
        // TODO 구현 필요함.
        String sql = "SELECT userId, password, name, email FROM USERS";
        PreparedStatementSetter statementSetter = pstmt -> {};

        RowMapper<User> mapper = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));

        return new JdbcTemplate().query(sql, statementSetter, mapper);
    }

    public User findByUserId(String userId) {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?";

        PreparedStatementSetter statementSetter = pstmt -> pstmt.setString(1, userId);

        RowMapper<User> rowMapper = rs -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        return new JdbcTemplate().queryForObject(sql, statementSetter, rowMapper);
    }
}
