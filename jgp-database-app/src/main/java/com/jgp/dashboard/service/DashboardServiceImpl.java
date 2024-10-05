package com.jgp.dashboard.service;

import com.jgp.dashboard.dto.DataPointDto;
import com.jgp.dashboard.dto.HighLevelSummaryDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.jgp.dashboard.dto.SeriesDataPointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final String INTEGER_DATA_POINT_TYPE = "INTEGER";
    private static final String DECIMAL_DATA_POINT_TYPE = "DECIMAL";
    private static final String PARTNER_ID_PARAM = "partnerId";
    private static final String FROM_DATE_PARAM = "fromDate";
    private static final String TO_DATE_PARAM = "toDate";

    @Override
    public HighLevelSummaryDto getHighLevelSummary(LocalDate fromDate, LocalDate toDate, Long partnerId) {
        final var highLevelSummaryMapper = new HighLevelSummaryMapper();
        if (Objects.isNull(fromDate) || Objects.isNull(toDate)){
            fromDate = getDefaultQueryDates().getLeft();
            toDate = getDefaultQueryDates().getRight();
        }
        var bpdWhereClause = "WHERE bpd.date_partner_recorded between :fromDate and :toDate ";
        var loanWhereClause = "WHERE l.date_disbursed between :fromDate and :toDate  ";
        MapSqlParameterSource parameters = new MapSqlParameterSource(FROM_DATE_PARAM, fromDate);
        parameters.addValue(TO_DATE_PARAM, toDate);

        if (Objects.nonNull(partnerId)) {
            parameters.addValue(PARTNER_ID_PARAM, partnerId);
            bpdWhereClause = String.format("%s and bpd.partner_id = :partnerId", bpdWhereClause);
            loanWhereClause = String.format("%s and l.partner_id = :partnerId", loanWhereClause);
        }
        var sqlQuery = String.format(HighLevelSummaryMapper.SCHEMA, bpdWhereClause, loanWhereClause);
        return this.namedParameterJdbcTemplate.queryForObject(sqlQuery, parameters, highLevelSummaryMapper);
    }

    @Override
    public List<DataPointDto> getLoanDisbursedByGenderSummary(LocalDate fromDate, LocalDate toDate, Long partnerId) {
        final DataPointMapper rm = new DataPointMapper(DECIMAL_DATA_POINT_TYPE);
        if (Objects.isNull(fromDate) || Objects.isNull(toDate)){
            fromDate = getDefaultQueryDates().getLeft();
            toDate = getDefaultQueryDates().getRight();
        }
        var loanWhereClause = "WHERE l.date_disbursed between :fromDate and :toDate  ";
        MapSqlParameterSource parameters = new MapSqlParameterSource(FROM_DATE_PARAM, fromDate);
        parameters.addValue(TO_DATE_PARAM, toDate);
        if (Objects.nonNull(partnerId)){
            parameters.addValue(PARTNER_ID_PARAM, partnerId);
            loanWhereClause = String.format("%s and l.partner_id = :partnerId", loanWhereClause);
        }
        var sqlQuery = String.format(DataPointMapper.LOANS_DISBURSED_BY_GENDER_SCHEMA+" group by 1;", loanWhereClause);
        return this.namedParameterJdbcTemplate.query(sqlQuery, parameters, rm);
    }

    @Override
    public List<DataPointDto> getBusinessOwnersTrainedByGenderSummary(LocalDate fromDate, LocalDate toDate, Long partnerId) {
        final DataPointMapper rm = new DataPointMapper(INTEGER_DATA_POINT_TYPE);
        if (Objects.isNull(fromDate) || Objects.isNull(toDate)){
            fromDate = getDefaultQueryDates().getLeft();
            toDate = getDefaultQueryDates().getRight();
        }lllllllllllllllllllllllllllllllllllllllllllllllll
        MapSqlParameterSource parameters = new MapSqlParameterSource(PARTNER_ID_PARAM, partnerId);
        parameters.addValue(PARTNER_ID_PARAM, partnerId);
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
        MapSqlParameterSource parameters = new MapSqlParameterSource(PARTNER_ID_PARAM, partnerId);
        parameters.addValue(PARTNER_ID_PARAM, partnerId);
        var sqlBuilder = new StringBuilder(DataPointMapper.LOANS_DISBURSED_BY_PIPELINE_SCHEMA);
        if (Objects.nonNull(partnerId)){
            sqlBuilder.append("where l.partner_id = :partnerId ");
        }
        sqlBuilder.append("group by 1;");

        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, rm);
    }

    @Override
    public List<DataPointDto> getLoansDisbursedByQualitySummary(Long partnerId) {
        final DataPointMapper rm = new DataPointMapper(DECIMAL_DATA_POINT_TYPE);
        MapSqlParameterSource parameters = new MapSqlParameterSource(PARTNER_ID_PARAM, partnerId);
        parameters.addValue(PARTNER_ID_PARAM, partnerId);
        var sqlBuilder = new StringBuilder(DataPointMapper.LOANS_DISBURSED_BY_QUALITY_SCHEMA);
        if (Objects.nonNull(partnerId)){
            sqlBuilder.append("where l.partner_id = :partnerId ");
        }
        sqlBuilder.append("group by 1;");

        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, rm);
    }

    @Override
    public List<SeriesDataPointDto> getTaNeedsByGenderSummary(Long partnerId) {
        final SeriesDataPointMapper rm = new SeriesDataPointMapper();
        MapSqlParameterSource parameters = new MapSqlParameterSource(PARTNER_ID_PARAM, partnerId);
        parameters.addValue(PARTNER_ID_PARAM, partnerId);
        var sqlBuilder = new StringBuilder(SeriesDataPointMapper.TA_NEEDS_BY_GENDER_SCHEMA);
        if (Objects.nonNull(partnerId)){
            sqlBuilder.append("where bpd.partner_id = :partnerId ");
        }
        sqlBuilder.append("group by 1, 2;");

        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, rm);
    }

    @Override
    public List<DataPointDto> getTaTrainingBySectorSummary(Long partnerId) {
        final DataPointMapper rm = new DataPointMapper(DECIMAL_DATA_POINT_TYPE);
        MapSqlParameterSource parameters = new MapSqlParameterSource(PARTNER_ID_PARAM, partnerId);
        parameters.addValue(PARTNER_ID_PARAM, partnerId);
        var sqlBuilder = new StringBuilder(DataPointMapper.BUSINESSES_TRAINED_BY_SECTOR_SCHEMA);
        if (Objects.nonNull(partnerId)){
            sqlBuilder.append("where bpd.partner_id = :partnerId ");
        }
        sqlBuilder.append("group by 1;");

        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), parameters, rm);
    }

    @Override
    public List<SeriesDataPointDto> getTrainingByPartnerByGenderSummary() {
        final SeriesDataPointMapper rm = new SeriesDataPointMapper();
        return this.namedParameterJdbcTemplate.query(SeriesDataPointMapper.TRAINING_BY_PARTNER_BY_GENDER_SCHEMA + "group by 1, 2;", rm);
    }

    @Override
    public List<SeriesDataPointDto> getLastThreeYearsAccessedLoanPerPartnerSummary() {
        final SeriesDataPointMapper rm = new SeriesDataPointMapper();
        return this.namedParameterJdbcTemplate.query(SeriesDataPointMapper.ACCESSED_AMOUNT_BY_PARTNER_BY_YEAR_SCHEMA, rm);
    }

    @Override
    public List<SeriesDataPointDto> getLoansAccessedVsOutStandingByPartnerSummary() {
        final SeriesDataPointMapper rm = new SeriesDataPointMapper();
        return this.namedParameterJdbcTemplate.query(SeriesDataPointMapper.LOAN_AMOUNT_ACCESSED_VS_OUTSTANDING_PER_PARTNER_BY_YEAR_SCHEMA, rm);
    }

    private static final class HighLevelSummaryMapper implements RowMapper<HighLevelSummaryDto> {

        public static final String SCHEMA = """
                    with highLevelSummary as (\s
                    select count(*) as businessesTrained,\s
                    0 as businessesLoaned, 0 as amountDisbursed,\s
                    0 as outStandingAmount from bmo_participants_data bpd %s \s
                    union
                    select 0 as businessesTrained, count(*) as businessesLoaned,\s
                    sum(loan_amount_accessed) as amountDisbursed, sum(loan_outstanding_amount) as outStandingAmount from loans l %s\s
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
                from loans l left join participants p on l.participant_id = p.id %s\s
                """;

        public static final String BUSINESSES_TRAINED_BY_GENDER_SCHEMA = """
                select p.owner_gender as dataKey, count(p.id) as dataValue,\s
                count(p.id) * 100.0 / count(count(p.id)) OVER () AS percentage\s
                from bmo_participants_data bpd inner join participants p on bpd.participant_id = p.id\s
                inner join partners p2 on p2.id = bpd.partner_id %s\s
                """;

        public static final String LOANS_DISBURSED_BY_PIPELINE_SCHEMA = """
                select l.pipeline_source as dataKey, sum(l.loan_amount_accessed) as dataValue,\s
                SUM(l.loan_amount_accessed) * 100.0 / SUM(SUM(l.loan_amount_accessed)) OVER () AS percentage\s
                from loans l\s
                """;

        public static final String LOANS_DISBURSED_BY_QUALITY_SCHEMA = """
                select l.loan_quality as dataKey, sum(l.loan_amount_accessed) as dataValue,\s
                SUM(l.loan_amount_accessed) * 100.0 / SUM(SUM(l.loan_amount_accessed)) OVER () AS percentage\s
                from loans l\s
                """;

        public static final String BUSINESSES_TRAINED_BY_SECTOR_SCHEMA = """
                select p.industry_sector as dataKey, count(p.id) as dataValue,\s
                count(p.id) * 100.0 / count(count(p.id)) OVER () AS percentage\s
                from participants p inner join bmo_participants_data bpd on bpd.participant_id = p.id\s
                inner join partners p2 on p2.id = bpd.partner_id\s
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
                if (DashboardServiceImpl.INTEGER_DATA_POINT_TYPE.equals(this.valueDataType)){
                    dataPoints.add(new DataPointDto(StringUtils.capitalize(dataKey), String.valueOf(rs.getInt("dataValue")), String.valueOf(rs.getBigDecimal("percentage"))));
                } else if (DECIMAL_DATA_POINT_TYPE.equals(this.valueDataType)) {
                    dataPoints.add(new DataPointDto(StringUtils.capitalize(dataKey), String.valueOf(rs.getBigDecimal("dataValue")), String.valueOf(rs.getBigDecimal("percentage"))));
                }else {
                    dataPoints.add(new DataPointDto(StringUtils.capitalize(dataKey), rs.getString("dataValue"), String.valueOf(rs.getBigDecimal("percentage"))));
                }
            }
            return dataPoints;
        }
    }

private static final class SeriesDataPointMapper implements ResultSetExtractor<List<SeriesDataPointDto>> {

    public static final String TA_NEEDS_BY_GENDER_SCHEMA = """
                SELECT unnest(string_to_array(p.ta_needs, ',')) AS name, p.owner_gender as seriesName, COUNT(*) AS value\s
                FROM participants p inner join bmo_participants_data bpd on bpd.participant_id = p.id\s
               \s""";

    public static final String TRAINING_BY_PARTNER_BY_GENDER_SCHEMA = """
                SELECT p2.partner_name AS name, p.owner_gender as seriesName, COUNT(*) AS value
                             FROM participants p inner join bmo_participants_data bpd on bpd.participant_id = p.id\s
                             inner join partners p2 on p2.id  = bpd.partner_id\s
               \s""";

    public static final String ACCESSED_AMOUNT_BY_PARTNER_BY_YEAR_SCHEMA = """
             SELECT p.partner_name as name,\s
             EXTRACT(YEAR FROM l.date_disbursed) AS seriesName,\s
             SUM(l.loan_amount_accessed) AS value\s
             FROM loans l inner join partners p on p.id = l.partner_id\s
             WHERE EXTRACT(YEAR FROM l.date_disbursed) >= EXTRACT(YEAR FROM current_date) - 2\s
             GROUP BY 1, 2\s
             ORDER BY 2 ASC;
            """;

    public static final String LOAN_AMOUNT_ACCESSED_VS_OUTSTANDING_PER_PARTNER_BY_YEAR_SCHEMA = """
             SELECT p.partner_name AS name,\s
             'ACCESSED' as seriesName, SUM(l.loan_amount_accessed) AS value
              FROM loans l\s
              inner join partners p on p.id  = l.partner_id
              group by 1, 2
              union\s
              SELECT p.partner_name AS name,\s
             'OUT-STANDING' as seriesName, SUM(l.loan_outstanding_amount) AS value
              FROM loans l\s
              inner join partners p on p.id  = l.partner_id
              group by 1, 2;
            """;

    @Override
    public List<SeriesDataPointDto> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var dataPoints = new ArrayList<SeriesDataPointDto>();
        var dataPointsMap = new HashMap<String, Map<String, Integer>>();
        while (rs.next()){
            final var taName = rs.getString("name");
            final var seriesName = rs.getString("seriesName");
            final var value = rs.getInt("value");

            if (dataPointsMap.containsKey(taName)){
                var genderMap = dataPointsMap.get(taName);
                if (genderMap.containsKey(seriesName)){
                    genderMap.put(seriesName, value + genderMap.get(seriesName));
                }else {
                    genderMap.put(seriesName, value);
                }
            }else {
                var genderMap = new HashMap<String, Integer>();
                genderMap.put(seriesName, value);
                dataPointsMap.put(taName, genderMap);
            }
        }
        for (Map.Entry<String, Map<String, Integer>> entry: dataPointsMap.entrySet()){
            var series = new HashSet<DataPointDto>();
            for (Map.Entry<String, Integer> seriesEntry: entry.getValue().entrySet()){
                series.add(new DataPointDto(StringUtils.capitalize(seriesEntry.getKey()), seriesEntry.getValue().toString(), ""));
            }
            dataPoints.add(new SeriesDataPointDto(StringUtils.capitalize(entry.getKey()), series));
        }
        return dataPoints;
    }
}


private Pair<LocalDate, LocalDate> getDefaultQueryDates(){
        final var dateToday = LocalDate.now();
        return new ImmutablePair<>(LocalDate.of(dateToday.getYear(), Month.JANUARY, 1), dateToday);
}

}
