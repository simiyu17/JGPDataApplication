package com.jgp.client.api;

import com.jgp.client.domain.Client;
import com.jgp.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAvailableClients(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
                                                                   @RequestParam(name = "pageSize", defaultValue = "200") Integer pageSize){
        final var sortedByDateCreated =
                PageRequest.of(pageNumber - 1, pageSize, Sort.by("dateCreated").descending());
        return new ResponseEntity<>(this.clientService.availableClients(sortedByDateCreated), HttpStatus.OK);
    }
}
