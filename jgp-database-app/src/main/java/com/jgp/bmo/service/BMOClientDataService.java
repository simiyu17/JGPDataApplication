package com.jgp.bmo.service;

import com.jgp.bmo.domain.BMOParticipantData;
import com.jgp.bmo.dto.BMOClientDto;
import com.jgp.bmo.dto.BMOParticipantSearchCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BMOClientDataService {

    void createBMOData(List<BMOParticipantData> bmoDataListRequest);

    void uploadBMOData(MultipartFile file);

    List<BMOClientDto> getBMODataRecords(BMOParticipantSearchCriteria searchCriteria,Pageable pageable);

    BMOClientDto findBMODataById(Long bmoId);

}
