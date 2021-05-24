package cm.acentice.ideale.constants;

public enum TypeFournisseur {

    SOCIETE("SOCIETE"),
    PARTICULIER("PARTICULIER");

    private final String name;

    TypeFournisseur(String name) {
        this.name = name;
    }
}
