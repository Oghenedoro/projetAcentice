package cm.acentice.ideale.constants;

public enum CommandeStatut {

    COMMANDE_ENREGISTREE("COMMANDE_ENREGISTREE"),STOCK_VALIDEE("STOCK_VALIDE"),STOCK_EN_ATTENTE("STOCK_EN_ATTENTE"),
    LIVRAISON_COMPLET("LIVRAISON_COMPLET"), PAIEMENTPARTIEL("PAIEMENT_PARTIEL"), PAIEMENT_COMPLET("PAIEMENT_COMPLET"),LIVRAISON_PARTIEL("LIVRAISON_PARTIEL"), PAIEMENT_DEPASSE("PAIEMENT_DEPASSE");

    private String name;

    private CommandeStatut(String name) {
    }
}