package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.CommandeLivraisonDto;
import cm.acentice.ideale.dto.LivraisonDto;
import cm.acentice.ideale.exceptions.LivraisonImpossibleException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.CommandeLivraisonService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/v1")
@RestController
public class LivraisonController {

    private final CommandeLivraisonService livraisonService;

    public LivraisonController(CommandeLivraisonService livraisonService) {
        this.livraisonService = livraisonService;
    }

    @RequestMapping(value = "/livraisons", method = RequestMethod.POST)
    public CommandeLivraisonDto create(@RequestBody CommandeLivraisonDto commandeLivraisonDto) throws ResourceNotFoundException, LivraisonImpossibleException {
        return livraisonService.create(commandeLivraisonDto);
    }
}
