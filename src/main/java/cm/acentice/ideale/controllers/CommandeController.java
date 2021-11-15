package cm.acentice.ideale.controllers;


import cm.acentice.ideale.dto.CommandeDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.exceptions.StockInsufficantException;
import cm.acentice.ideale.services.CommandeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/v1")
@RestController
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }
    @RequestMapping(value = "/commandes", method = RequestMethod.POST)
    CommandeDto create(@RequestBody CommandeDto commandeDto) throws ResourceNotFoundException, StockInsufficantException {
       return commandeService.create(commandeDto);
    }
}
