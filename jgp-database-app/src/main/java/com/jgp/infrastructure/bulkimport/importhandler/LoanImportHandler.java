package com.jgp.infrastructure.bulkimport.importhandler;

import com.jgp.authentication.service.UserService;
import com.jgp.bmo.domain.BMOParticipantData;
import com.jgp.bmo.service.BMOClientDataService;
import com.jgp.finance.domain.Loan;
import com.jgp.infrastructure.bulkimport.constants.BMOConstants;
import com.jgp.infrastructure.bulkimport.constants.LoanConstants;
import com.jgp.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import com.jgp.infrastructure.bulkimport.data.Count;
import com.jgp.infrastructure.bulkimport.event.BulkImportEvent;
import com.jgp.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class LoanImportHandler implements ImportHandler {

    private final BMOClientDataService bmoDataService;
    private final ParticipantService clientService;
    private final UserService userService;
    List<Loan> loanDataList;
    private Workbook workbook;
    private List<String> statuses;

    @Override
    public Count process(BulkImportEvent bulkImportEvent) {
        this.workbook = bulkImportEvent.workbook();
        loanDataList = new ArrayList<>();
        statuses = new ArrayList<>();
        readExcelFile();
        return importEntity();
    }

    public void readExcelFile() {
        Sheet bmoSheet = workbook.getSheet(TemplatePopulateImportConstants.BMO_SHEET_NAME);
        Integer noOfEntries = ImportHandlerUtils.getNumberOfRows(bmoSheet, TemplatePopulateImportConstants.FIRST_COLUMN_INDEX);
        for (int rowIndex = 1; rowIndex <= noOfEntries; rowIndex++) {
            Row row;
            row = bmoSheet.getRow(rowIndex);
            if (ImportHandlerUtils.isNotImported(row, LoanConstants.STATUS_COL)) {
                loanDataList.add(readLoanData(row));
            }
        }
    }

    private Loan readLoanData(Row row) {
        final var status = ImportHandlerUtils.readAsString(LoanConstants.STATUS_COL, row);
        final var appFormSubmittedDate = ImportHandlerUtils.readAsDate(LoanConstants.APPLICATION_FORM_SUBMITTED_DATE_COL, row);
        final var pipeLineSource = ImportHandlerUtils.readAsString(LoanConstants.PIPELINE_SOURCE, row);
        final var loanStatus = ImportHandlerUtils.readAsString(LoanConstants.LOAN_STATUS, row);
        final var loanStatusEnum = null != loanStatus ? Loan.LoanStatus.valueOf(loanStatus.toUpperCase()) : Loan.LoanStatus.NEW;
        final var applicationDate = ImportHandlerUtils.readAsDate(LoanConstants.DATE_APPLIED, row);
        final var dateDisbursed = ImportHandlerUtils.readAsDate(LoanConstants.DATE_DISBURSED, row);
        final var amountAccessed = ImportHandlerUtils.readAsDouble(LoanConstants.VALUE_ACCESSED, row);
        final var valueAccessed = BigDecimal.valueOf(amountAccessed);
        final var loanDuration = ImportHandlerUtils.readAsInt(LoanConstants.LOAN_DURATION, row);
        String referredFIBusiness = ImportHandlerUtils.readAsString(LoanConstants.REFERRED_FI_BUSINESS_COL, row);
        LocalDate dateRecordedByPartner = ImportHandlerUtils.readAsDate(LoanConstants.DATE_RECORD_ENTERED_BY_PARTNER_COL, row);
        LocalDate recordedToJGPDBOnDate = ImportHandlerUtils.readAsDate(LoanConstants.DATE_RECORDED_TO_JGP_DB_COL, row);
        Integer taSessionsAttended = ImportHandlerUtils.readAsInt(LoanConstants.NUMBER_TA_SESSION_ATTENDED_COL, row);
        Boolean isRecommendedForFinance = "YES".equals(ImportHandlerUtils.readAsString(LoanConstants.RECOMMENDED_FOR_FINANCE_COL, row));
        LocalDate pipelineDecisionDate = ImportHandlerUtils.readAsDate(LoanConstants.DATE_OF_PIPELINE_DECISION_COL, row);
        String referredFIBusiness = ImportHandlerUtils.readAsString(LoanConstants.REFERRED_FI_BUSINESS_COL, row);
        LocalDate dateRecordedByPartner = ImportHandlerUtils.readAsDate(LoanConstants.DATE_RECORD_ENTERED_BY_PARTNER_COL, row);
        LocalDate recordedToJGPDBOnDate = ImportHandlerUtils.readAsDate(LoanConstants.DATE_RECORDED_TO_JGP_DB_COL, row);
        statuses.add(status);

        String jgpId = ImportHandlerUtils.readAsString(BMOConstants.JGP_ID_COL, row);
        if (null == jgpId){
            return null;
        }
        final var existingClient = this.clientService.findOneByJGPID(jgpId);
        if (existingClient.isPresent()){
            return existingClient.get();
        }

        return new Loan(Objects.nonNull(userService.currentUser()) ? userService.currentUser().getPartner() : null,
                existingClient.get(), 1001, pipeLineSource, loanStatusEnum, applicationDate, dateDisbursed, valueAccessed,
                loanDuration, isApplicantEligible, numberOfTAsAttended,
                taSessionsAttended, isRecommendedForFinance, pipelineDecisionDate,
                referredFIBusiness, dateRecordedByPartner, recordedToJGPDBOnDate, row.getRowNum());
    }
}
