package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

	protected abstract void setValues(PreparedStatement preparedStatement) throws SQLException;
	protected abstract String createQuery();


}
