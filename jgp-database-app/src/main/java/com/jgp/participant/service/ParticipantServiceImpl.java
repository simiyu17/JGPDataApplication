package com.jgp.participant.service;

import com.jgp.participant.domain.Participant;
import com.jgp.participant.domain.ParticipantRepository;
import com.jgp.participant.dto.ParticipantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository clientRepository;

    @Override
    public Participant createClient(ParticipantDto clientDto) {
        return this.clientRepository.save(Participant.createClient(clientDto));
    }

    @Override
    public Optional<Participant> findOneByJGPID(String jgpId) {
        return this.clientRepository.findByJgpId(jgpId);
    }

    @Override
    public List<Participant> availableClients(Pageable pageable) {
        return this.clientRepository.findAll(pageable).stream().toList();
    }
}
