package com.jgp.bmo.service;

import com.jgp.bmo.domain.BMOClientData;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BMOClientDataService {

    void createBMOData(List<BMOClientData> bmoDataListRequest);

    void uploadBMOData(MultipartFile file);

    List<BMOClientData> getBMODataRecords(Pageable pageable);

    BMOClientData findBMODataById(Long bmoId);

}
