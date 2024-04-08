package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryJdbcTemplate implements JdbcTemplate{

    @Override
    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {
        System.out.println("sql = " + sql);
        System.out.println("pss = " + pss);

    }

    @Override
    public void update(String sql, Object... parameters) {

    }

    @Override
    public void update(PreparedStatementCreator psc, KeyHolder holder) {

    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss) {
        return null;
    }

    @Override
    public <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameters) {
        return null;
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException {
        return null;
    }

    @Override
    public <T> List<T> query(String sql, RowMapper<T> rm, Object... parameters) {
        return null;
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object... parameters) {
        return new PreparedStatementSetter() {
            @Override
            public void setParameters(PreparedStatement pstmt) throws SQLException {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i + 1, parameters[i]);
                }
            }
        };
    }
}
