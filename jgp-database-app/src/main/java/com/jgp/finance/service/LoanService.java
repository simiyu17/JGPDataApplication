package com.jgp.finance.service;

import com.jgp.finance.domain.Loan;
import com.jgp.finance.dto.LoanDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoanService {

    void createLoans(List<Loan> loans);

    void uploadBulkLoanData(MultipartFile file);

    List<LoanDto> getLoans(Pageable pageable);

    LoanDto findLoanById(Long loanId);
}