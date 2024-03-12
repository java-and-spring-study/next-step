package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDao {

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
        };

        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
        };

        final String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
        };

        final String sql = "UPDATE USERS SET password=?, email=?, name=? WHERE userId=?";

        PreparedStatementSetter preparedStatementSetter = (PreparedStatement preparedStatement) -> {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getUserId());
        };
        jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public List<User> findAll() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
        };

        PreparedStatementSetter preparedStatementSetter = (preparedStatement) -> {
        };
        RowMapper<User> rowMapper = (resultSet) -> new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                resultSet.getString("email"));
        final String sql = "SELECT * FROM USERS";

        return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
    }

    public User findByUserId(String userId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
        };

        final String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        RowMapper<User> rowMapper = (ResultSet resultSet) -> new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getString("email"));
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, userId);
        };
        return jdbcTemplate.queryForObject(sql, preparedStatementSetter, rowMapper);
    }


}
