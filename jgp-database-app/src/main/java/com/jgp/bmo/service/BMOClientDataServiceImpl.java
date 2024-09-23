package com.jgp.bmo.service;

import com.jgp.bmo.domain.BMOParticipantData;
import com.jgp.bmo.domain.BMOClientDataRepository;
import com.jgp.bmo.domain.predicate.BMOPredicateBuilder;
import com.jgp.bmo.dto.BMOClientDto;
import com.jgp.bmo.dto.BMOParticipantSearchCriteria;
import com.jgp.bmo.mapper.BMOClientMapper;
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
public class BMOClientDataServiceImpl implements BMOClientDataService {

    private final BMOClientDataRepository bmoDataRepository;
    private final ApplicationEventPublisher publisher;
    private final BMOClientMapper bmoClientMapper;
    private final BMOPredicateBuilder bmoPredicateBuilder;

    @Override
    public void createBMOData(List<BMOParticipantData> bmoDataListRequest) {
        this.bmoDataRepository.saveAll(bmoDataListRequest);
    }

    @Override
    public void uploadBMOData(MultipartFile file) {
        try {
            this.publisher.publishEvent(new BulkImportEvent(new XSSFWorkbook(file.getInputStream()), TemplatePopulateImportConstants.BMO_ENTITY));
        }  catch (Exception e){
            throw new RuntimeException("Error while importing BMO Data: "+ e.getMessage());
        }
    }

    @Override
    public List<BMOClientDto> getBMODataRecords(BMOParticipantSearchCriteria searchCriteria, Pageable pageable) {
        return this.bmoClientMapper.toDto(this.bmoDataRepository.findAll(this.bmoPredicateBuilder.buildPredicateForSearchLoans(searchCriteria), pageable).stream().toList());
    }

    @Override
    public BMOClientDto findBMODataById(Long bmoId) {
        return this.bmoDataRepository.findById(bmoId).map(this.bmoClientMapper::toDto).orElseThrow(() -> new RuntimeException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));
    }
}
