package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

// 변하지 않는 라이브러리 부분,
// 변하지 않는 코드를 여기에다가 작성하자.
public abstract class JdbcTemplate {
	public void update(String sql, PreparedStatementSetter preparedStatementSetter) {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
			sql)) {
			preparedStatementSetter.setValues(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(String sql, Object... values) {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
			sql)) {
			PreparedStatementSetter preparedStatementSetter = createPreparedStatementSetter(values);
			preparedStatementSetter.setValues(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> query(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
			sql)) {
			preparedStatementSetter.setValues(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<T> result = new ArrayList<>();
			while (resultSet.next()) {
				result.add(rowMapper.mapRow(resultSet));
			}

			return result;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... values) {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
			sql)) {
			PreparedStatementSetter preparedStatementSetter = createPreparedStatementSetter(values);
			preparedStatementSetter.setValues(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();

			List<T> result = new ArrayList<>();
			while (resultSet.next()) {
				result.add(rowMapper.mapRow(resultSet));
			}

			return result;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Todo
	 * ResultSet 을 close 해주지 않는다면 어떻게 될까?!
	 */
	public <T> T queryForObject(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			preparedStatementSetter.setValues(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			return rowMapper.mapRow(resultSet);
		} catch (RuntimeException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... values) {
		try (
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
		) {
			PreparedStatementSetter preparedStatementSetter = createPreparedStatementSetter(values);
			preparedStatementSetter.setValues(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			return rowMapper.mapRow(resultSet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private PreparedStatementSetter createPreparedStatementSetter(Object... values) {
		return (preparedStatement -> {
			for (int index = 0; index < values.length; index++) {
				preparedStatement.setString(index + 1, String.valueOf(values[index]));
			}
		});
	}

}
