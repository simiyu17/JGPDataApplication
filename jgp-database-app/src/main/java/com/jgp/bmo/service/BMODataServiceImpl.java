package com.jgp.bmo.service;

import com.jgp.bmo.domain.BMOData;
import com.jgp.bmo.domain.BMODataRepository;
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
public class BMODataServiceImpl implements BMODataService {

    private final BMODataRepository bmoDataRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public void createBMOData(List<BMOData> bmoDataListRequest) {
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
    public List<BMOData> getBMODataRecords(Pageable pageable) {
        return this.bmoDataRepository.findAll(pageable).stream().toList();
    }

    @Override
    public BMOData findBMODataById(Long bmoId) {
        return this.bmoDataRepository.findById(bmoId).orElseThrow(() -> new RuntimeException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));
    }
}
