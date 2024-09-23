package com.jgp.participant.service;

import com.jgp.participant.domain.Participant;
import com.jgp.participant.domain.ParticipantRepository;
import com.jgp.participant.dto.ParticipantDto;
import com.jgp.participant.exception.ParticipantNotFoundException;
import com.jgp.participant.mapper.ParticipantMapper;
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

    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;

    @Override
    public Participant createClient(ParticipantDto clientDto) {
        return this.participantRepository.save(Participant.createClient(clientDto));
    }

    @Override
    public Optional<Participant> findOneByJGPID(String jgpId) {
        return this.participantRepository.findByJgpId(jgpId);
    }

    @Override
    public ParticipantDto findParticipantById(Long participantId, boolean includeAccounts) {
        var participant =  this.participantRepository.findById(participantId)
                .map(this.participantMapper::toDto)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));

        if (includeAccounts){
        participant.jgpId().
        }

        return participant;
    }

    @Override
    public List<Participant> availableClients(Pageable pageable) {
        return this.participantRepository.findAll(pageable).stream().toList();
    }
}
