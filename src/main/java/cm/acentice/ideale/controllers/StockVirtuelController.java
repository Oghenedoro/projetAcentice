package cm.acentice.ideale.controllers;

import cm.acentice.ideale.dto.StockVirtuelDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.models.StockVituel;
import cm.acentice.ideale.services.StockVirtuelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/v1")
@RequiredArgsConstructor
@RestController
public class StockVirtuelController {

    private final StockVirtuelService stockVirtuelService;

    @RequestMapping(value = "/stockvirtuels", method = RequestMethod.POST)
    public List<String> checkStockWithCommande(@RequestBody StockVituel vituel) {
        return stockVirtuelService.checkStockWithCommande(vituel);
    }
}
