package com.jgp.patner.service;


import com.jgp.patner.domain.Partner;
import com.jgp.patner.domain.PartnerRepository;
import com.jgp.patner.dto.PartnerDto;
import com.jgp.patner.exception.PartnerNotFoundException;
import com.jgp.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;


    @Transactional
    @Override
    public void createPartner(PartnerDto partnerDto) {
        try {
            this.partnerRepository.save(Partner.createPartner(partnerDto));
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    @Transactional
    @Override
    public void updatePartner(Long userId, PartnerDto partnerDto) {
        var currentPartner = this.partnerRepository.findById(userId)
                .orElseThrow(() -> new PartnerNotFoundException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));
        try {
            currentPartner.updatePartner(partnerDto);
            this.partnerRepository.save(currentPartner);
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public PartnerDto findPartnerById(Long userId) {
        return this.partnerRepository.findById(userId)
                .map(p -> new PartnerDto(p.getId(), p.getPartnerName(), p.getType().getName()))
                .orElseThrow(() -> new PartnerNotFoundException(CommonUtil.NO_RESOURCE_FOUND_WITH_ID));
    }

    @Override
    public List<PartnerDto> getAllPartners() {
        return this.partnerRepository.findAll().stream().map(p -> new PartnerDto(p.getId(), p.getPartnerName(), p.getType().getName())).toList();
    }

}
