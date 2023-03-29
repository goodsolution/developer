package pl.com.goodsolution.adviser.auth;

public enum Roles {
    ADMINISTRATOR("Administrator"),
    ALEKSANDRA("Aleksandra"),
    CHEF("Szef Kuchni"),
    DAMIAN("Damian"),
    DIETICIAN("Dietetyk"),
    DRIVER("Kierowca"),
    DRIVER_MARCIN("Kierowca Marcin"),
    FOR_PEOPLE("4people"),
    IT("Paweł prog"),
    KASIA("Kasia"),
    KLAUDIA("Klaudia"),
    LIMITED_PRIVILEGES("Pracownik z ograniczonymi uprawnieniami"),
    PACKAGING("Pakowanie"),
    ROKSANA("Roksana"),
    SANDRA("Sandra"),
    SYSTEM_SERVICE("Obsługa Systemu"),
    SUPER_ADMINISTRATOR("Superadministrator"),
    TEST("Test8"),
    LIFE_ADVISOR("LIFE-ADVISOR");


    private String code;
    Roles(String code) {
        this.code = code;
    }

    String code() {
        return this.code;
    }
}
