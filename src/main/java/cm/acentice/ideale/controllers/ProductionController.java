package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.ProductionDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.services.ProductionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class ProductionController {

    private final ProductionService productionService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "/productions", method = RequestMethod.POST)
    public void create(@RequestBody ProductionDto productionDto) throws ResourceNotFoundException {
        productionService.create(productionDto);
    }
}
