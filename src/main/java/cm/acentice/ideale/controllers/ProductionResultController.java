package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ProductionResultDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.repositories.ProductionResultRepos;
import cm.acentice.ideale.services.ProductionResultService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class ProductionResultController {

    private final ProductionResultService productionResultService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "/productionresults", method = RequestMethod.POST)
    public void create(@RequestBody ProductionResultDto productionResultDto) throws ResourceNotFoundException {
        productionResultService.create(productionResultDto);
    }
}
