package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

// 변하지 않는 라이브러리 부분,
// 변하지 않는 코드를 여기에다가 작성하자.
public abstract class JdbcTemplate {
	public void update() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			preparedStatement = connection.prepareStatement(createQuery());
			setValues(preparedStatement);
			preparedStatement.executeUpdate();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public List<Object> query() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try{

			connection = ConnectionManager.getConnection();
			String sql = createQuery();
			preparedStatement =connection.prepareStatement(sql);
			setValues(preparedStatement);
			resultSet = preparedStatement.executeQuery();
			List<Object> result = new ArrayList<>();
			while(resultSet.next()) {
				result.add(mapRow(resultSet));
			}
			return result;
		}finally {
			if(resultSet != null) {
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}

	public Object queryForObject() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try{

			connection = ConnectionManager.getConnection();
			String sql = createQuery();
			preparedStatement =connection.prepareStatement(sql);
			setValues(preparedStatement);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()) {
				return null;
			}
			return mapRow(resultSet);
		}finally {
			if(resultSet != null) {
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}

	protected abstract void setValues(PreparedStatement preparedStatement) throws SQLException;
	protected abstract String createQuery();

	protected abstract Object mapRow(ResultSet resultSet) throws SQLException;


}
