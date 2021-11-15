package cm.acentice.ideale.services;

import cm.acentice.ideale.dto.ClientDto;
import cm.acentice.ideale.entities.Client;
import cm.acentice.ideale.interfaces.DataPMapper;
import cm.acentice.ideale.repositories.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientService {

    private final DataPMapper dataPMapper;
    private final ClientRepository clientRepository;

    public ClientDto create(ClientDto clientDto){
        Client client = dataPMapper.mapFromClientDto_toClient(clientDto);
        client = clientRepository.save(client);
        clientDto = dataPMapper.mapFromClient_toClientDto(client);

        return clientDto;
    }
}
