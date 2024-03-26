package cn.eqxiu.mock.config;

import cn.eqxiu.mock.domain.entity.RequestParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RequestParamTypeHandler implements TypeHandler<List<RequestParam>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, List<RequestParam> parameter, JdbcType jdbcType) throws SQLException {
        try {
            String json = objectMapper.writeValueAsString(parameter);
            ps.setString(i, json);
        } catch (Exception e) {
            throw new SQLException("Error converting List<RequestParam> to JSON string: " + e.getMessage());
        }
    }

    @Override
    public List<RequestParam> getResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return parseJson(json);
    }

    @Override
    public List<RequestParam> getResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return parseJson(json);
    }

    @Override
    public List<RequestParam> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return parseJson(json);
    }

    private List<RequestParam> parseJson(String json) throws SQLException {
        try {
            if (json != null && !json.isEmpty()) {
                return objectMapper.readValue(json, new TypeReference<List<RequestParam>>() {
                });
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SQLException("Error converting JSON string to List<RequestParam>: " + e.getMessage());
        }
    }
}
