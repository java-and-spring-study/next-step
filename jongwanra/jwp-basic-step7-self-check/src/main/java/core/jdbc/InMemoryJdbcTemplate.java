package core.jdbc;

import java.util.List;

public class InMemoryJdbcTemplate implements JdbcTemplate{
    @Override
    public void update(String sql, PreparedStatementSetter pss) throws DataAccessException {

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
}
