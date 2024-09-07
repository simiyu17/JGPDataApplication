package com.jgp.dashboard.service;

import com.jgp.dashboard.dto.DataPointDto;
import com.jgp.dashboard.dto.HighLevelSummaryDto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public HighLevelSummaryDto getHighLevelSummary() {
        final var highLevelSummaryMapper = new HighLevelSummaryMapper();
        return this.jdbcTemplate.queryForObject(HighLevelSummaryMapper.SCHEMA, highLevelSummaryMapper);
    }

    @Override
    public List<DataPointDto> getLoanDisbursedByGenderSummary() {
        return List.of();
    }

    private static final class HighLevelSummaryMapper implements RowMapper<HighLevelSummaryDto> {

        public final static String SCHEMA = """
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
}
