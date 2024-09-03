package com.jgp.finance;

import com.jgp.finance.domain.Loan;
import com.jgp.finance.dto.LoanDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface LoanMapper {

    @Mapping(target = "participantName", expression = "java(null != loan.getParticipant() ? loan.getParticipant().getBusinessName() : null)")
    @Mapping(target = "loanStatus", expression = "java(null != loan.getLoanStatus() ? loan.getLoanStatus().getName() : null)")
    @Mapping(target = "loanQuality", expression = "java(null != loan.getLoanQuality() ? loan.getLoanQuality().getName() : null)")
    LoanDto toDto(Loan loan);

    List<LoanDto> toDto(List<Loan> loan);
}
