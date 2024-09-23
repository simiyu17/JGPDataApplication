package com.jgp.bmo.api;

import com.jgp.bmo.dto.BMOClientDto;
import com.jgp.bmo.dto.BMOParticipantSearchCriteria;
import com.jgp.bmo.service.BMOClientDataService;
import com.jgp.shared.dto.ApiResponseDto;
import com.jgp.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/bmos")
public class BMOClientController {

    private final BMOClientDataService bmoDataService;

    @GetMapping
    public ResponseEntity<List<BMOClientDto>> getAvailableBMODataRecords(@RequestParam(name = "partnerId", required = false) Long partnerId,
                                                                         @RequestParam(name = "participantId", required = false) Long participantId,
                                                                        @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                                                          @RequestParam(name = "pageSize", defaultValue = "200") Integer pageSize){
        final var sortedByDateCreated =
                PageRequest.of(pageNumber - 1, pageSize, Sort.by("dateCreated").descending());
        return new ResponseEntity<>(this.bmoDataService.getBMODataRecords(new BMOParticipantSearchCriteria(partnerId, participantId), sortedByDateCreated), HttpStatus.OK);
    }

    @PostMapping("upload-template")
    public ResponseEntity<ApiResponseDto> createBMOParticipantData(@RequestParam("excelFile") MultipartFile excelFile) {
        if (excelFile.isEmpty()) {
            return new ResponseEntity<>(new ApiResponseDto(false, CommonUtil.NO_FILE_TO_UPLOAD), HttpStatus.BAD_REQUEST);
        }
        this.bmoDataService.uploadBMOData(excelFile);
        return new ResponseEntity<>(new ApiResponseDto(true, CommonUtil.RESOURCE_CREATED), HttpStatus.CREATED);
    }

    @GetMapping("{bmoId}")
    public ResponseEntity<BMOClientDto> getBMOParticipantData(@PathVariable("bmoId") Long bmoId){
        return new ResponseEntity<>(this.bmoDataService.findBMODataById(bmoId), HttpStatus.OK);
    }

}
