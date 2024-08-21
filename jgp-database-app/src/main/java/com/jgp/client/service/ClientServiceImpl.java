package com.jgp.client.service;

import com.jgp.client.domain.Client;
import com.jgp.client.domain.ClientRepository;
import com.jgp.client.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client createClient(ClientDto clientDto) {
        return this.clientRepository.save(Client.createClient(clientDto));
    }

    @Override
    public Optional<Client> findOneByJGPID(String jgpId) {
        return this.clientRepository.findByJgpId(jgpId);
    }

    @Override
    public List<Client> availableClients(Pageable pageable) {
        return this.clientRepository.findAll(pageable).stream().toList();
    }
}
