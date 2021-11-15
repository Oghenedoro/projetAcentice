package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ClientDto;
import cm.acentice.ideale.services.ClientService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/v1")
@RestController
public class ClientController {

   private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    public ClientDto create(@RequestBody ClientDto clientDto){
        return clientService.create(clientDto);
    }
}
