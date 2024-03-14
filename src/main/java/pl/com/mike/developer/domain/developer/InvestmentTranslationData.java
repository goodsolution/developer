package pl.com.mike.developer.domain.developer;

import javax.persistence.*;

@Entity
@Table(name = "investment_translations")
public class InvestmentTranslationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long translationId;

    @Column(name = "investment_id")
    private Integer investmentId;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "domain")
    private String domain;

    @Column(name = "value")
    private String value;

    public InvestmentTranslationData() {
    }

    public Long getTranslationId() {
        return translationId;
    }

    public void setTranslationId(Long translationId) {
        this.translationId = translationId;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
