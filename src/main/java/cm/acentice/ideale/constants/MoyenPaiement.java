package cm.acentice.ideale.constants;

public enum MoyenPaiement {

    ESPECE("ESPECE"),CHEQUE("CHEQUE"),CB("CB"),MTNMONEY("MTN_MONEY"),ORANGEMONEY("ORANGE_MONEY"),VIREMENTBANCAIRE("VIREMENT_BANCAIRE");
    private String type;

    MoyenPaiement(String type) {
        this.type = type;
    }
}
