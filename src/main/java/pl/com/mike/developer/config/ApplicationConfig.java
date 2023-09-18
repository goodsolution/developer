package pl.com.mike.developer.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableCaching
@EnableScheduling
public class ApplicationConfig {

    @Value("${cepik.page.url}")
    private String url;

    @Value("${masterdieta-path-to-products-demand-pdf}")
    private String pathToProductsDemandPdf;

    @Value("${masterdieta-path-to-fonts}")
    private String pathToFonts;

    @Value("${masterdieta-path-to-gallery}")
    private String pathToGallery;

    @Value("${course-platform-path-to-gallery}")
    private String coursePathToGallery;

    @Value("${course-platform-path-to-invoices-folder}")
    private String coursePathToInvoicesFolder;

    @Value("${course-platform-path-to-memes-folder}")
    private String coursePathToMemesFolder;

    @Value("${course-platform-path-to-course-attachments-folder}")
    private String coursePathToCourseAttachmentsFolder;

    @Value("${course-platform-path-to-files-folder}")
    private String coursePathToFilesFolder;

    @Value("${masterdieta-scheduler-update-order-status-hour}")
    private Integer schedulerUpdateOrderStatusHour;

    @Value("${masterdieta-scheduler-update-order-status-limit}")
    private Long schedulerUpdateOrderStatusLimit;

    @Value("${masterdieta-scheduler-update-order-status-disabled}")
    private Boolean schedulerUpdateOrderStatusDisabled;



    @Value("${masterdieta-scheduler-update-order-status-traditional-hour}")
    private Integer schedulerUpdateOrderStatusTraditionalHour;

    @Value("${masterdieta-scheduler-update-order-status-traditional-limit}")
    private Long schedulerUpdateOrderStatusTraditionalLimit;

    @Value("${masterdieta-scheduler-update-order-status-traditional-disabled}")
    private Boolean schedulerUpdateOrderStatusTraditionalDisabled;

    @Value("${masterdieta-scheduler-delivery_delivered-hour}")
    private Integer schedulerDeliveryDeliveredHour;

    @Value("${masterdieta-scheduler-delivery_delivered-limit}")
    private Long schedulerDeliveryDelivereLimit;

    @Value("${masterdieta-scheduler-delivery_delivered-disabled}")
    private Boolean schedulerDeliveryDeliveredDisabled;

    @Value("${masterdieta-scheduler-instagram-sync-disabled}")
    private Boolean schedulerInstagramSyncDisabled;

    @Value("${reply-to-email}")
    private String replyToEmail;

    @Value("${from-email}")
    private String fromEmail;

    @Value("${company.name}")
    private String companyName;

    @Value("${company.address.first.line}")
    private String companyAddressFirstLine;

    @Value("${company.address.second.line}")
    private String companyAddressSecondLine;

    @Value("${company.nip}")
    private String companyNip;

    @Value("${company.regon}")
    private String companyRegon;

    @Value("${company.contact.mail}")
    private String companyContactMail;

    @Value("${company.contact.mailto}")
    private String companyContactMailto;

    @Value("${course-platform.url}")
    private String coursePlatformUrl;

    @Value("${payments.payu.url}")
    private String paymentsPayuUrl;

    @Value("${payments.payu.client.id}")
    private String paymentsPayuClientId;

    @Value("${payments.payu.client.secret}")
    private String paymentsPayuClientSecret;

    @Value("${payments.payu.merchant.pos.id}")
    private String paymentsPayuMerchantPosId;

    @Value("${payments.payu.second.key}")
    private String paymentsPayuSecondKey;

    @Value("${password.reset.token.validity.time.in.minutes}")
    private Long passwordResetTokenValidityTimeInMinutes;

    @Value("${menu.courses.disabled}")
    private Boolean menuCoursesDisabled;

    @Value("${logo.front.path}")
    private String logoFrontPath;

    @Value("${logo.panel.path}")
    private String logoPanelPath;

    @Value("${favicon.path}")
    private String faviconPath;

    @Value("${app.name.front}")
    private String appNameFront;

    @Value("${app.name.panel}")
    private String appNamePanel;

    @Value("${company.country}")
    private String companyCountry;

    @Value("${company.page.url}")
    private String companyPageUrl;

    @Value("${developer.code}")
    private String developerCode;

    @Value("${developer.id}")
    private String developerId;

    public String getDeveloperId() {
        return developerId;
    }

    private String templateNameProductsDemandPdf = "products-demand-pdf.html";

    public String getUrl() {
        return url;
    }

    public String getDeveloperCode() {
        return developerCode;
    }

    public String getPathToProductsDemandPdf() {
        return pathToProductsDemandPdf;
    }

    public String getTemplateNameProductsDemandPdf() {
        return templateNameProductsDemandPdf;
    }

    public String getPathToFonts() {
        return pathToFonts;
    }

    public String getPathToGallery() {
        return pathToGallery;
    }

    public Integer getSchedulerUpdateOrderStatusHour() {
        return schedulerUpdateOrderStatusHour;
    }

    public Long getSchedulerUpdateOrderStatusLimit() {
        return schedulerUpdateOrderStatusLimit;
    }

    public Boolean getSchedulerUpdateOrderStatusDisabled() {
        return schedulerUpdateOrderStatusDisabled;
    }

    public Integer getSchedulerUpdateOrderStatusTraditionalHour() {
        return schedulerUpdateOrderStatusTraditionalHour;
    }

    public Long getSchedulerUpdateOrderStatusTraditionalLimit() {
        return schedulerUpdateOrderStatusTraditionalLimit;
    }

    public Boolean getSchedulerUpdateOrderStatusTraditionalDisabled() {
        return schedulerUpdateOrderStatusTraditionalDisabled;
    }

    public Integer getSchedulerDeliveryDeliveredHour() {
        return schedulerDeliveryDeliveredHour;
    }

    public Long getSchedulerDeliveryDelivereLimit() {
        return schedulerDeliveryDelivereLimit;
    }

    public Boolean getSchedulerDeliveryDeliveredDisabled() {
        return schedulerDeliveryDeliveredDisabled;
    }

    public Boolean getSchedulerInstagramSyncDisabled() {
        return schedulerInstagramSyncDisabled;
    }

    public String getCoursePathToGallery() {
        return coursePathToGallery;
    }

    public String getPaymentsPayuUrl() {
        return paymentsPayuUrl;
    }

    public String getPaymentsPayuClientId() {
        return paymentsPayuClientId;
    }

    public String getPaymentsPayuClientSecret() {
        return paymentsPayuClientSecret;
    }

    public String getPaymentsPayuMerchantPosId() {
        return paymentsPayuMerchantPosId;
    }

    public String getPaymentsPayuSecondKey() {
        return paymentsPayuSecondKey;
    }

    public String getReplyToEmail() {
        return replyToEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyAddressFirstLine() {
        return companyAddressFirstLine;
    }

    public String getCompanyAddressSecondLine() {
        return companyAddressSecondLine;
    }

    public String getCompanyNip() {
        return companyNip;
    }

    public String getCompanyRegon() {
        return companyRegon;
    }

    public String getCompanyContactMail() {
        return companyContactMail;
    }

    public String getCompanyContactMailto() {
        return companyContactMailto;
    }

    public String getCoursePlatformUrl() {
        return coursePlatformUrl;
    }

    public String getCoursePathToInvoicesFolder() {
        return coursePathToInvoicesFolder;
    }

    public Long getPasswordResetTokenValidityTimeInMinutes() {
        return passwordResetTokenValidityTimeInMinutes;
    }

    public String getCoursePathToMemesFolder() {
        return coursePathToMemesFolder;
    }

    public Boolean getMenuCoursesDisabled() {
        return menuCoursesDisabled;
    }

    public String getCoursePathToCourseAttachmentsFolder() {
        return coursePathToCourseAttachmentsFolder;
    }

    public String getLogoFrontPath() {
        return logoFrontPath;
    }

    public String getLogoPanelPath() {
        return logoPanelPath;
    }

    public String getAppNameFront() {
        return appNameFront;
    }

    public String getAppNamePanel() {
        return appNamePanel;
    }

    public String getCompanyCountry() {
        return companyCountry;
    }

    public String getCompanyPageUrl() {
        return companyPageUrl;
    }

    public String getFaviconPath() {
        return faviconPath;
    }

    public String getCoursePathToFilesFolder() {
        return coursePathToFilesFolder;
    }

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate =  new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }
}
