package com.jgp.dashboard.service;

import com.jgp.dashboard.dto.DataPointDto;
import com.jgp.dashboard.dto.HighLevelSummaryDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String INTEGER_DATA_POINT_TYPE = "INTEGER";
    private static final String DECIMAL_DATA_POINT_TYPE = "DECIMAL";

    @Override
    public HighLevelSummaryDto getHighLevelSummary() {
        final var highLevelSummaryMapper = new HighLevelSummaryMapper();
        return this.jdbcTemplate.queryForObject(HighLevelSummaryMapper.SCHEMA, highLevelSummaryMapper);
    }

    @Override
    public List<DataPointDto> getLoanDisbursedByGenderSummary(Long partnerId) {
        final DataPointMapper rm = new DataPointMapper(DECIMAL_DATA_POINT_TYPE);
        MapSqlParameterSource parameters = new MapSqlParameterSource("partnerId", partnerId);
        parameters.addValue("partnerId", partnerId);
        var sqlBuilder = new StringBuilder(DataPointMapper.LOANS_DISBURSED_BY_GENDER_SCHEMA);
        if (Objects.nonNull(partnerId)){
            sqlBuilder.append("where l.partner_id = :partnerId ");
        }
        sqlBuilder.append("group by 1;");

        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, rm);
    }

    @Override
    public List<DataPointDto> getBusinessOwnersTrainedByGenderSummary(Long partnerId) {
        final DataPointMapper rm = new DataPointMapper(INTEGER_DATA_POINT_TYPE);
        MapSqlParameterSource parameters = new MapSqlParameterSource("partnerId", partnerId);
        parameters.addValue("partnerId", partnerId);
        var sqlBuilder = new StringBuilder(DataPointMapper.BUSINESSES_TRAINED_BY_GENDER_SCHEMA);
        if (Objects.nonNull(partnerId)){
            sqlBuilder.append("where bpd.partner_id = :partnerId ");
        }
        sqlBuilder.append("group by 1;");

        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, rm);
    }

    @Override
    public List<DataPointDto> getLoanDisbursedByPipelineSourceSummary(Long partnerId) {
        final DataPointMapper rm = new DataPointMapper(DECIMAL_DATA_POINT_TYPE);
        MapSqlParameterSource parameters = new MapSqlParameterSource("partnerId", partnerId);
        parameters.addValue("partnerId", partnerId);
        var sqlBuilder = new StringBuilder(DataPointMapper.LOANS_DISBURSED_BY_PIPELINE_SCHEMA);
        if (Objects.nonNull(partnerId)){
            sqlBuilder.append("where l.partner_id = :partnerId ");
        }
        sqlBuilder.append("group by 1;");

        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, rm);
    }

    private static final class HighLevelSummaryMapper implements RowMapper<HighLevelSummaryDto> {

        public static final String SCHEMA = """
                    with highLevelSummary as (\s
                    select count(*) as businessesTrained,\s
                    0 as businessesLoaned, 0 as amountDisbursed,\s
                    0 as outStandingAmount from participants p\s
                    union
                    select 0 as businessesTrained, count(*) as businessesLoaned,\s
                    sum(loan_amount_accessed) as amountDisbursed, sum(loan_outstanding_amount) as outStandingAmount from loans l
                    )
                    select sum(businessesTrained) as businessesTrained, sum(businessesLoaned) as businessesLoaned,\s
                    sum(amountDisbursed) as amountDisbursed, sum(outStandingAmount) as outStandingAmount
                    from highLevelSummary;
                   \s""";

        @Override
        public HighLevelSummaryDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            final var businessesTrained = rs.getInt("businessesTrained");
            final var businessesLoaned = rs.getInt("businessesLoaned");
            final var amountDisbursed = rs.getBigDecimal("amountDisbursed");
            final var outStandingAmount = rs.getBigDecimal("outStandingAmount");
            return new HighLevelSummaryDto(businessesTrained, businessesLoaned, amountDisbursed, outStandingAmount);
        }
    }

    private static final class DataPointMapper implements ResultSetExtractor<List<DataPointDto>> {

        public static final String LOANS_DISBURSED_BY_GENDER_SCHEMA = """
                select p.owner_gender as dataKey, sum(l.loan_amount_accessed) as dataValue,\s
                SUM(l.loan_amount_accessed) * 100.0 / SUM(SUM(l.loan_amount_accessed)) OVER () AS percentage\s
                from loans l left join participants p on l.participant_id = p.id\s
                """;

        public static final String BUSINESSES_TRAINED_BY_GENDER_SCHEMA = """
                select p.owner_gender as dataKey, count(p.id) as dataValue,\s
                count(p.id) * 100.0 / count(count(p.id)) OVER () AS percentage\s
                from participants p inner join bmo_participants_data bpd on bpd.participant_id = p.id\s
                inner join partners p2 on p2.id = bpd.partner_id\s
                """;

        public static final String LOANS_DISBURSED_BY_PIPELINE_SCHEMA = """
                select l.pipeline_source as dataKey, sum(l.loan_amount_accessed) as dataValue,\s
                SUM(l.loan_amount_accessed) * 100.0 / SUM(SUM(l.loan_amount_accessed)) OVER () AS percentage\s
                from loans l\s
                """;

        private final String valueDataType;

        public DataPointMapper(String valueDataType) {
            this.valueDataType = valueDataType;
        }


        @Override
        public List<DataPointDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
            var dataPoints = new ArrayList<DataPointDto>();
            while (rs.next()){
                final var dataKey = rs.getString("dataKey");
                if (INTEGER_DATA_POINT_TYPE.equals(this.valueDataType)){
                    dataPoints.add(new DataPointDto(dataKey, String.valueOf(rs.getInt("dataValue")), String.valueOf(rs.getBigDecimal("percentage"))));
                } else if (DECIMAL_DATA_POINT_TYPE.equals(this.valueDataType)) {
                    dataPoints.add(new DataPointDto(dataKey, String.valueOf(rs.getBigDecimal("dataValue")), String.valueOf(rs.getBigDecimal("percentage"))));
                }else {
                    dataPoints.add(new DataPointDto(dataKey, rs.getString("dataValue"), String.valueOf(rs.getBigDecimal("percentage"))));
                }
            }
            return dataPoints;
        }
    }
}
