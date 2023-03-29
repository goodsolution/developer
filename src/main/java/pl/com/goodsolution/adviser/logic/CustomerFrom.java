package pl.com.goodsolution.adviser.logic;

public enum CustomerFrom {
    ADMINISTRATION_PANEL ("Panel administracyjny"),
    NEWSLETTER ("Newsletter"),
    OTHER ("Inne"),
    FACEBOOK ("Facebook"),
    GOOGLE ("Google"),
    WWW_PAGE ("Strona WWW"),
    NONE ("---");
    private String text;

    CustomerFrom(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
