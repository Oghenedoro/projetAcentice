
package cm.acentice.ideale.interfaces;

import cm.acentice.ideale.dto.*;
import cm.acentice.ideale.entities.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DataPMapper {

    public ApprovisionnementMatieresPremieres mapFromDTOToEntity(ApprovisionnementMatieresPremieresDto matieresPremieresDto);
    public ApprovisionnementMatieresPremieresDto mapFromEntityToDTO(ApprovisionnementMatieresPremieres matieresPremieres);
    public Commande mapFromCmdDtoToCommande(CommandeDto commandeDto);
    public CommandeDto mapFromCmdToCommandeDto(Commande commande);
    public List<Commande>mapFromCommandeListDtoToCommandeList(List<CommandeDto>commandeDtoList);
    public List<CommandeDto>mapFromCommandeListToCommandeDtoList(List<Commande>commandeList);
    public Paiement mapFromPaimentDto_toPaiment(PaiementDto paiementDto);
    public PaiementDto mapFromPaiment_toPaimentDto(Paiement paiement);
    public List<PaiementDto>mapFromPaiementListToPaiementDtoList(List<Paiement>paiementList);
    public List<Paiement>mapFromPaiementDtoListToPaiementList(List<PaiementDto>paiementDtoList);
    public Client mapFromClientDto_toClient(ClientDto clientDto);
    public ClientDto mapFromClient_toClientDto(Client client);
    public Livraison mapFromLivraisonDto_toLivraison(LivraisonDto livraisonDto);
    public LivraisonDto mapFromLivraison_toLivraisonDto(Livraison livraison);
    public List<Livraison> mapFromLivraisonDtoList_toLivraisonList(List<LivraisonDto> livraisonDtos);
    public List<LivraisonDto> mapFromLivraisonList_toLivraisonListDto(List<Livraison> livraison);
    public LigneDeCommandeDto mapFromLigneDeCommande_toLigneDeCommandeDto(LigneDeCommande ligneDeCommande);
    public LigneDeCommande mapFromLigneDeCommandeDto_toLigneDeCommande(LigneDeCommandeDto ligneDeCommandeDto);
    public CommandeLivraison mapFromCommandeLivraisonDto_toCommandeLivraison(CommandeLivraisonDto commandeLivraisonDto);
    public CommandeLivraisonDto mapFromCommandeLivraison_toCommandeLivraisonDto(CommandeLivraison commandeLivraison);

}

