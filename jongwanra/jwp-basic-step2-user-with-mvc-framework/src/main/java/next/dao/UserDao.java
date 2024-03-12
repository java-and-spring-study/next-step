package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import next.model.User;

public class UserDao {

    public void insert(User user) {
        try{
            JdbcTemplate jdbcTemplate = new JdbcTemplate() {

                @Override
                protected Object mapRow(ResultSet resultSet) throws SQLException {
                    return null;
                }

                @Override
                protected void setValues(PreparedStatement preparedStatement) throws SQLException{
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

            jdbcTemplate.update();
        }catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {

            @Override
            protected Object mapRow(ResultSet resultSet) throws SQLException {
                return null;
            }

            @Override
            protected void setValues(PreparedStatement preparedStatement) throws SQLException {
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
            jdbcTemplate.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAll() throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate() {

            @Override
            protected Object mapRow(ResultSet resultSet) throws SQLException {
                return new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
                    resultSet.getString("email"));
            }

            @Override
            protected String createQuery() {
                return "SELECT * FROM USERS";
            }

            @Override
            protected void setValues(PreparedStatement preparedStatement) {

            }
        };

        return jdbcTemplate.query()
            .stream()
            .map(obj -> (User) obj)
            .collect(Collectors.toList());
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {

                @Override
                protected Object mapRow(ResultSet resultSet) throws SQLException {
                    return new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"), resultSet.getString("email"));
                }

                @Override
                protected String createQuery() {
                    return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
                }

                @Override
                protected void setValues(PreparedStatement preparedStatement) throws SQLException {
                    preparedStatement.setString(1, userId);
                }
            };

        return (User) jdbcTemplate.queryForObject();
    }
}
