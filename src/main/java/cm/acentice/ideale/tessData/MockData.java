package cm.acentice.ideale.tessData;

import cm.acentice.ideale.dto.CommandeDto;
import cm.acentice.ideale.entities.Commande;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public static Commande getCommande(){
        Commande commande = Commande.builder()
                .idCommande(1l)
                .commentaires("good")
                .build();
        return commande;
    }

    public static CommandeDto getCommandeDto(){
        CommandeDto commandeDto = CommandeDto.builder()
                .idCommande(1l)
                .commentaires("good")
                .build();
        return commandeDto;
    }

    public static List<Commande> getListCommande(){
        List<Commande> commandes = new ArrayList<>();
        Commande commande = Commande.builder()
                .idCommande(1l)
                .commentaires("good")
                .build();
        commandes.add(commande);

        Commande commande2 = Commande.builder()
                .idCommande(2l)
                .commentaires("good2")
                .build();
        commandes.add(commande2);

        return commandes;
    }

    public static List<CommandeDto> getListCommandeDto(){
        List<CommandeDto> commandeDtoList = new ArrayList<>();
        CommandeDto commandeDto = CommandeDto.builder()
                .idCommande(1l)
                .commentaires("good")
                .build();
        commandeDtoList.add(commandeDto);

        CommandeDto commandeDto2 = CommandeDto.builder()
                .idCommande(2l)
                .commentaires("good2")
                .build();
        commandeDtoList.add(commandeDto2);

        return commandeDtoList;
    }

}
