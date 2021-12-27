package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.StockVirtuelDto;
import cm.acentice.ideale.exceptions.ResourceNotFoundException;
import cm.acentice.ideale.models.StockVituel;

import java.util.List;

public interface StockVirtuelInt {
    public List<String> checkStockWithCommande(StockVituel vituel);
}
