package core.jdbc;

import next.exception.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public void update(String sql,
                       PreparedStatementSetter statementSetter) throws DataAccessException {

        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            statementSetter.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Object... values) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            PreparedStatementSetter statementSetter = createPreparedStatementSetter(values);
            statementSetter.values(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }


    public <T> List<T> query(String sql,
                      PreparedStatementSetter statementSetter,
                      RowMapper<T> mapper) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            statementSetter.values(pstmt);

            List<T> objects = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    objects.add(mapper.mapRow(rs));
                }
            }

            return objects;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> List<T> query(String sql,
                             RowMapper<T> mapper,
                             Object... values) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            PreparedStatementSetter statementSetter = createPreparedStatementSetter(values);
            statementSetter.values(pstmt);

            List<T> objects = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    objects.add(mapper.mapRow(rs));
                }
            }

            return objects;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T queryForObject(String sql,
                                 PreparedStatementSetter statementSetter,
                                 RowMapper<T> rowMapper) throws DataAccessException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)){
            statementSetter.values(pstmt);

            T object;
            try (ResultSet rs = pstmt.executeQuery()) {
                object = rowMapper.mapRow(rs);
            }

            return object;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public <T> T queryForObject(String sql,
                                RowMapper<T> rowMapper,
                                Object... values) throws DataAccessException{


        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)){
            PreparedStatementSetter statementSetter = createPreparedStatementSetter(values);
            statementSetter.values(pstmt);

            T object;
            try (ResultSet rs = pstmt.executeQuery()) {
                object = rowMapper.mapRow(rs);
            }

            return object;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object... values) {

        return pstmt -> {
            int parameterIndex = 1;
            for (Object value : values) {
                pstmt.setString(parameterIndex, value.toString());
                parameterIndex++;
            }
        };
    }

}
