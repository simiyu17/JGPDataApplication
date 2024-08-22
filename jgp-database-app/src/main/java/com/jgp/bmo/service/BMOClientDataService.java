package com.jgp.bmo.service;

import com.jgp.bmo.domain.BMOClientData;
import com.jgp.bmo.dto.BMOClientDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BMOClientDataService {

    void createBMOData(List<BMOClientData> bmoDataListRequest);

    void uploadBMOData(MultipartFile file);

    List<BMOClientDto> getBMODataRecords(Pageable pageable);

    BMOClientDto findBMODataById(Long bmoId);

}
