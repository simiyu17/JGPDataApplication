package com.jgp.finance.service;

import com.jgp.finance.mapper.LoanMapper;
import com.jgp.finance.domain.Loan;
import com.jgp.finance.domain.LoanRepository;
import com.jgp.finance.dto.LoanDto;
import com.jgp.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import com.jgp.infrastructure.bulkimport.event.BulkImportEvent;
import com.jgp.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final ApplicationEventPublisher publisher;
    private final LoanMapper loanMapper;

    @Override
    public void createLoans(List<Loan> loans) {
        this.loanRepository.saveAll(loans);
    }

    @Override
    public void uploadBulkLoanData(MultipartFile file) {
        try {
            this.publisher.publishEvent(new BulkImportEvent(new XSSFWorkbook(file.getInputStream()), TemplatePopulateImportConstants.LOAN_ENTITY));
        }  catch (Exception e){
            throw new RuntimeException("Error while importing Loan Data: "+ e.getMessage());
        }
    }

    @Override
    public List<LoanDto> getLoans(Pageable pageable) {
        return this.loanMapper.toDto(this.loanRepository.findAll(pageable).stream().toList());
    }

    @Override
    public LoanDto findLoanById(Long loanId) {
        return this.loanRepository.findById(loanId).map(this.loanMapper::toDto).orElseThrow(() -> new RuntimeException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));

    }
}
