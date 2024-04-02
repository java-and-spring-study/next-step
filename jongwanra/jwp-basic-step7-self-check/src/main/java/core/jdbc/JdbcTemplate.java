package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface JdbcTemplate {
    void update(String sql, PreparedStatementSetter pss) throws DataAccessException;


    void update(String sql, Object... parameters);

    void update(PreparedStatementCreator psc, KeyHolder holder) ;


    <T> T queryForObject(String sql, RowMapper<T> rm, PreparedStatementSetter pss);

    <T> T queryForObject(String sql, RowMapper<T> rm, Object... parameters);

    <T> List<T> query(String sql, RowMapper<T> rm, PreparedStatementSetter pss) throws DataAccessException;

    <T> List<T> query(String sql, RowMapper<T> rm, Object... parameters);
}
