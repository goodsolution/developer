package pl.com.goodsolution.adviser.logic.courseplatform;

import java.util.HashMap;
import java.util.Map;

public enum CustomerAuthority {
    ADMIN("ADMIN", "ROLE_ADMIN"),
    USER("USER", "ROLE_USER"),
    TEACHER("TEACHER", "ROLE_TEACHER"),
    EMPLOYER("EMPLOYER", "ROLE_EMPLOYER"),
    STUDENT("STUDENT", "ROLE_STUDENT"),
    JAVA_SCRIPT("JAVA_SCRIPT", "ROLE_JAVA_SCRIPT"),
    JAVA("JAVA", "ROLE_JAVA"),
    SQL("SQL", "ROLE_SQL"),
    SPRING_BOOT("SPRING_BOOT", "ROLE_SPRING_BOOT"),
    LIFE_ADVISER_USER("LIFE_ADVISER_USER", "ROLE_LIFE_ADVISER_USER");

    private String code;
    private String codeWithRole;

    CustomerAuthority(String code, String codeWithRole) {
        this.code = code;
        this.codeWithRole = codeWithRole;
    }

    public String getCode() {
        return code;
    }

    public String getCodeWithRole() {
        return codeWithRole;
    }

    private static final Map<String, CustomerAuthority> authoritiesWithCodeWithRole = new HashMap<>();

    static {
        for(CustomerAuthority customerAuthority : CustomerAuthority.values()){
            authoritiesWithCodeWithRole.put(customerAuthority.codeWithRole, customerAuthority);
        }
    }
    public static CustomerAuthority findByCodeWithRole(String codeWithRole){
        return authoritiesWithCodeWithRole.get(codeWithRole);
    }
}
