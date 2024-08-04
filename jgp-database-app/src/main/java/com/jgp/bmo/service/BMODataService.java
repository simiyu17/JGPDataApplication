package com.jgp.bmo.service;

import com.jgp.bmo.domain.BMOData;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BMODataService {

    void createBMOData(List<BMOData> bmoDataListRequest);

    void uploadBMOData(MultipartFile file);

    List<BMOData> getBMODataRecords(Pageable pageable);

    BMOData findBMODataById(Long bmoId);

}
