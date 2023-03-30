package pl.com.mike.developer.domain.adviser;

import java.util.Objects;

public class AdviceKey {
    private String domain;
    private String appId;
    private String lang;

    public AdviceKey(String domain, String appId) {
        this.domain = domain;
        this.appId = appId;
        this.lang = "pl";
    }

    public String getDomain() {
        return domain;
    }

    public String getAppId() {
        return appId;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdviceKey that = (AdviceKey) o;
        return domain.equals(that.domain) &&
                appId.equals(that.appId) &&
                lang.equals(that.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, appId, lang);
    }
}
