package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.PaiementDto;
import cm.acentice.ideale.exceptions.ExcessPaiementException;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.PaiementService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/v1")
@RestController
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @RequestMapping(value = "/paiements", method = RequestMethod.POST)
    public PaiementDto create(@RequestBody PaiementDto paiementDto) throws ResourceNotFoundException, ExcessPaiementException {
        return paiementService.create(paiementDto);
    }
}
