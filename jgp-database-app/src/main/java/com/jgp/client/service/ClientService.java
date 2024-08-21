package com.jgp.client.service;

import com.jgp.client.domain.Client;
import com.jgp.client.dto.ClientDto;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client createClient(ClientDto clientDto);

    Optional<Client> findOneByJGPID(@NonNull String jgpId);

    List<Client> availableClients(Pageable pageable);
}
