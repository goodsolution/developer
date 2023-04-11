package pl.com.mike.developer.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.mike.developer.domain.CustomerData;
import pl.com.mike.developer.domain.CustomersFilter;
import pl.com.mike.developer.domain.DictionaryData;
import pl.com.mike.developer.logic.courseplatform.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DictionariesService {
    private static final Logger log = LoggerFactory.getLogger(DictionariesService.class);

    private DictionariesJdbcRepository dictionariesJdbcRepository;
    private CustomersJdbcRepository customersJdbcRepository;

    public DictionariesService(DictionariesJdbcRepository dictionariesJdbcRepository, CustomersJdbcRepository customersJdbcRepository) {
        this.dictionariesJdbcRepository = dictionariesJdbcRepository;
        this.customersJdbcRepository = customersJdbcRepository;
    }

    public List<DictionaryData> getDictionary(DictionaryType type) {
        return getDictionary(type, LanguagesUtil.getCurrentLanguage());
    }

    public List<DictionaryData> getDictionary(DictionaryType type, Locale locale) {
        return getDictionary(type, LanguagesUtil.prepareLanguageFrom(locale));
    }

    @Cacheable(CacheType.DICTIONARIES)
    public List<DictionaryData> getDictionary(DictionaryType type, Language lang) {
        if (DictionaryType.ORDER_PAYMENT_METHODS == type) {
            return getOrderPaymentMethods(lang);
        } else if (DictionaryType.PAYMENT_PAYMENT_METHODS == type) {
            return getPaymentPaymentMethods(lang);
        } else if (DictionaryType.PAYMENT_STATUSES == type) {
            return getPaymentStatuses(lang);
        } else if (DictionaryType.SHIPMENT_TYPES == type) {
            return getShipmentTypes(lang);
        } else if (DictionaryType.ORDER_STATUSES == type) {
            return getOrderStatuses(lang);
        } else if (DictionaryType.DRIVERS == type) {
            return getActiveDrivers(lang);
        } else if (DictionaryType.CITIES == type) {
            return getCities(lang);
        } else if (DictionaryType.DIETS == type) {
            return getActiveDiets(lang);
        } else if (DictionaryType.WEEKEND_OPTIONS == type) {
            return getWeekendOptions(lang);
        } else if (DictionaryType.YES_NO == type) {
            return getYesNo(lang);
        } else if (DictionaryType.CUSTOMER_AUTHORITIES == type) {
            return getCustomerAuthorities(lang);
        } else if (DictionaryType.PRODUCTS == type) {
            return getProducts(lang);
        } else if (DictionaryType.EXTRAS == type) {
            return getExtras(lang);
        } else if (DictionaryType.CUSTOMERS == type) {
            return getCustomers(lang);
        } else if (DictionaryType.CUSTOMER_GROUP_STATUSES == type) {
            return getCustomerGroupStatuses(lang);
        } else if (DictionaryType.CUSTOMER_TYPES == type) {
            return getCustomerTypes(lang);
        } else if (DictionaryType.CUSTOMER_GROUPS == type) {
            return getCustomerGroups(lang);
        } else if (DictionaryType.CUSTOMER_STATUSES == type) {
            return getCustomerStatuses(lang);
        } else if (DictionaryType.ALLERGENS == type) {
            return getAllergens(lang);
        }
//        else if (DictionaryType.GALLERY_IMAGE_KIND == type) {
//            return getGalleryImageKind(lang);
//      }
        else if (DictionaryType.LANGUAGES == type) {
            return getLanguages(lang);
        } else if (DictionaryType.ROLES == type) {
            return getRoles(lang);
        } else if (DictionaryType.AUTHORS == type) {
            return getAuthors(lang);
        } else if (DictionaryType.COURSE_ORDER_STATUSES == type) {
            return getCourseOrderStatuses(lang);
        } else if (DictionaryType.INVOICE_TYPES == type) {
            return getInvoiceTypes(lang);
        } else if (DictionaryType.BILLING_TYPES == type) {
            return getBillingTypes(lang);
        } else if (DictionaryType.COUNTRIES == type) {
            return getCountries(lang);
        } else if (DictionaryType.COURSE_VISIBILITY_STATUSES == type) {
            return getCourseVisibilityStatuses(lang);
        } else if (DictionaryType.LESSON_COMMENT_STATUSES == type) {
            return getLessonCommentStatuses(lang);
        } else if (DictionaryType.LESSON_VISIBILITY_STATUSES == type) {
            return getLessonVisibilityStatuses(lang);
        } else if (DictionaryType.MODULE_VISIBILITY_STATUSES == type) {
            return getModuleVisibilityStatuses(lang);
        } else if (DictionaryType.COURSE_SALE_STATUSES == type) {
            return getCourseSaleStatuses(lang);
        } else if (DictionaryType.LESSON_TYPES == type) {
            return getLessonTypes(lang);
        } else if (DictionaryType.MOVIE_LINK_TYPES == type) {
            return getMovieLinkTypes(lang);
        } else if (DictionaryType.VOIVODESHIP == type) {
            return getVoivodeshipTypes(lang);
        }
        throw new IllegalArgumentException("Dictionary no defined: " + type + " for language: " + lang);
    }

    @Cacheable(CacheType.DICTIONARY_CUSTOMER)
    public List<DictionaryData> getDictionaryCustomers(String substring) {
        List<DictionaryData> result = getCustomers(Language.PL);
        return result.stream().filter(o -> o.getValue().toLowerCase().contains(substring.toLowerCase())).collect(Collectors.toList());
    }

    public DictionaryData getDictionaryElementByCode(String code, DictionaryType type) {
        return getDictionaryElementByCode(code, type, LanguagesUtil.getCurrentLanguage());
    }

    public DictionaryData getDictionaryElementByCode(String code, DictionaryType type, Language lang) {
        if (DictionaryType.ORDER_PAYMENT_METHODS == type) {
            return null; //TODO
        } else if (DictionaryType.PAYMENT_STATUSES == type) {
            return null; //TODO
        } else if (DictionaryType.SHIPMENT_TYPES == type) {
            return null; //TODO
        } else if (DictionaryType.ORDER_STATUSES == type) {
            return null; //TODO
        } else if (DictionaryType.DRIVERS == type) {
            return null; //TODO
        } else if (DictionaryType.DIETS == type) {
            return null; //TODO
        } else if (DictionaryType.YES_NO == type) {
            return getYesNo(lang).stream().filter(dictElem -> dictElem.getCode().equalsIgnoreCase(code)).findFirst().get();
        }
//        else if (DictionaryType.GALLERY_IMAGE_KIND == type) {
//            return getGalleryImageKind(lang).stream().filter(dictElem -> dictElem.getCode().equalsIgnoreCase(code)).findFirst().get();
//        }
        else if (DictionaryType.ROLES == type) {
            return getRoles(lang).stream().filter(dictElem -> dictElem.getCode().equalsIgnoreCase(code)).findFirst().get();
        } else if (DictionaryType.LANGUAGES == type) {
            return getLanguages(lang).stream().filter(dictElem -> dictElem.getCode().equalsIgnoreCase(code)).findFirst().get();
        }


        throw new IllegalArgumentException("Dictionary no defined: " + type + " for language: " + lang);
    }

    public DictionaryData getDictionaryElementById(Long id, DictionaryType type, Language lang) {
        if (type.equals(DictionaryType.PAYMENT_STATUSES) && id.equals(2L)) {
            id = 0L;
        }
        final Long i = id;
        try {
            return getDictionary(type, lang).stream().filter(status -> Objects.equals(status.getId(), i)).findFirst().get();
        } catch (NoSuchElementException ex) {
            log.debug("No value for: id: " + id + " dict: " + type.name() + " lang: " + lang.name());
            throw ex;
        }
    }

    private DictionaryData getDictionaryElementByValue(String value, DictionaryType type, Language lang) {
        try {
            return getDictionary(type, lang).stream().filter(status -> Objects.equals(status.getValue().toUpperCase(), value.toUpperCase())).findFirst().get();
        } catch (NoSuchElementException ex) {
            log.debug("No value for: value: " + value + " dict: " + type.name() + " lang: " + lang.name());
            throw ex;
        }
    }

    public String getColorCodeByElementId(Long id, DictionaryType type, Language lang) {
        return getCodeByElementId(id, type, lang);
    }

    public String getCodeByElementId(Long id, DictionaryType type, Language lang) {
        if (DictionaryType.PAYMENT_STATUSES == type || DictionaryType.ORDER_STATUSES == type) {
            return getDictionary(type, lang).stream().filter(status -> Objects.equals(status.getId(), id)).findFirst().map(DictionaryData::getCode).orElse("");
        } else {
            return null;
        }
    }

    @Cacheable(CacheType.DICTIONARIES_BY_ID)
    public String getDictionaryValueById(Long id, DictionaryType type, Language lang) {
        return getDictionaryElementById(id, type, lang).getValue();
    }

    @Cacheable(CacheType.DICTIONARIES_BY_VALUE)
    public Long getDictionaryIdByValue(String value, DictionaryType type, Language lang) {
        return getDictionaryElementByValue(value, type, lang).getId();
    }

    private List<DictionaryData> getYesNo(Language lang) {
        return Language.PL == lang ?
                Arrays.asList(new DictionaryData("true", "Tak", lang.getCode()), new DictionaryData("false", "Nie", lang.getCode())) :
                Arrays.asList(new DictionaryData("true", "Yes", lang.getCode()), new DictionaryData("false", "No", lang.getCode()));
    }

    private List<DictionaryData> getCustomerAuthorities(Language lang) {

        List<DictionaryData> list = new ArrayList<>();

        if (Language.US == lang || Language.EN == lang) {
            list.add(new DictionaryData(CustomerAuthority.ADMIN.getCode(), "Admin", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.USER.getCode(), "User", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.EMPLOYER.getCode(), "Employer", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.TEACHER.getCode(), "Teacher", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.STUDENT.getCode(), "Student", lang.getCode()));
        } else if (Language.PL == lang) {
            list.add(new DictionaryData(CustomerAuthority.ADMIN.getCode(), "Admin", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.USER.getCode(), "Użytkownik", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.EMPLOYER.getCode(), "Pracodawca", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.TEACHER.getCode(), "Nauczyciel", lang.getCode()));
            list.add(new DictionaryData(CustomerAuthority.STUDENT.getCode(), "Kursant", lang.getCode()));
        }

        return list;
    }


    private List<DictionaryData> getOrderPaymentMethods(Language lang) {
        return dictionariesJdbcRepository.getPaymentMethods(lang);
    }

    private List<DictionaryData> getPaymentPaymentMethods(Language lang) {
        List<DictionaryData> dictionaryDataList = new ArrayList<>();

        DictionaryData dictionaryData = new DictionaryData(
                1L,
                "Płatność gotówką",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);
        dictionaryData = new DictionaryData(
                2L,
                "Płatność kartą",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);
        dictionaryData = new DictionaryData(
                3L,
                "Przelew tradycyjny",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);

        dictionaryData = new DictionaryData(
                4L,
                "Przelew ekspresowy",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);

        dictionaryData = new DictionaryData(
                5L,
                "Gratis",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);

        dictionaryData = new DictionaryData(
                6L,
                "Zwrot",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);

        dictionaryData = new DictionaryData(
                7L,
                "Płatność Bitcoin",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);
        return dictionaryDataList;
    }

    private List<DictionaryData> getPaymentStatuses(Language lang) {
        return dictionariesJdbcRepository.getPaymentStatuses(lang);
    }

    private List<DictionaryData> getShipmentTypes(Language lang) {
        return dictionariesJdbcRepository.getShipmentTypes(lang);
    }

    private List<DictionaryData> getOrderStatuses(Language lang) {
        return dictionariesJdbcRepository.getOrderStatuses(lang);
    }

    private List<DictionaryData> getActiveDrivers(Language lang) {
        return dictionariesJdbcRepository.getActiveDrivers(lang);
    }

    private List<DictionaryData> getCities(Language lang) {
        return dictionariesJdbcRepository.getCities(lang);
    }

    private List<DictionaryData> getActiveDiets(Language lang) {
        return dictionariesJdbcRepository.getActiveDiets(lang);
    }

    private List<DictionaryData> getProducts(Language lang) {
        return dictionariesJdbcRepository.getActiveProducts(lang);
    }

    private List<DictionaryData> getExtras(Language lang) {
        return dictionariesJdbcRepository.getActiveExtras(lang);
    }

    private List<DictionaryData> getCustomers(Language lang) {
        List<Map<String, Object>> rows = customersJdbcRepository.findCustomers(new CustomersFilter(Boolean.TRUE));

        List<CustomerData> customers = new ArrayList<>();
        for (Map row : rows) {
            customers.add(
                    new CustomerData(
                            Long.valueOf(String.valueOf(row.get("id"))),
                            String.valueOf(row.get("name")),
                            String.valueOf(row.get("surname")),
                            String.valueOf(row.get("email"))
                    )
            );
        }
        //IMP

        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        for (CustomerData customer : customers) {
            DictionaryData dictionaryData = new DictionaryData(
                    customer.getId(),
                    customer.getFirstName() + " " + customer.getLastName(),
                    lang.getCode(),
                    customer.getEmail(),
                    null
            );
            dictionaryDataList.add(dictionaryData);
        }
        return dictionaryDataList;
    }

//    private List<DictionaryData> getGalleryImageKind(Language lang) {
//        List<DictionaryData> dictionaryDataList = new ArrayList<>();
//
//        DictionaryData dictionaryData = new DictionaryData(
//                0L,
//                GalleryImageKind.MENU.code(),
//                "Do menu",
//                lang.getCode()
//        );
//        dictionaryDataList.add(dictionaryData);
//        dictionaryData = new DictionaryData(
//                1L,
//                GalleryImageKind.GALLERY_HORIZONTAL.code(),
//                "Do galerii poziomy",
//                lang.getCode()
//        );
//        dictionaryDataList.add(dictionaryData);
//        dictionaryData = new DictionaryData(
//                1L,
//                GalleryImageKind.GALLERY_VERTICAL.code(),
//                "Do galerii pionowy",
//                lang.getCode()
//        );
//        dictionaryDataList.add(dictionaryData);
//        return dictionaryDataList;
//    }

    private List<DictionaryData> getWeekendOptions(Language lang) {
        List<DictionaryData> dictionaryDataList = new ArrayList<>();

        DictionaryData dictionaryData = new DictionaryData(
                0L,
                "Nie",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);
        dictionaryData = new DictionaryData(
                1L,
                "Tak",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);
        dictionaryData = new DictionaryData(
                2L,
                "Bez niedziel",
                lang.getCode()
        );
        dictionaryDataList.add(dictionaryData);
        return dictionaryDataList;
    }

    private List<DictionaryData> getCustomerGroupStatuses(Language lang) {
        return dictionariesJdbcRepository.getCustomerGroupStatuses(lang);
    }

    private List<DictionaryData> getCustomerTypes(Language lang) {
        List<DictionaryData> dictionaryDataList = new ArrayList<>();
        dictionaryDataList.add(new DictionaryData("retail", "Detaliczny", lang.getCode()));
        dictionaryDataList.add(new DictionaryData("wholesale", "Hurtowy", lang.getCode()));

        return dictionaryDataList;
    }

    private List<DictionaryData> getCustomerGroups(Language lang) {
        return dictionariesJdbcRepository.getCustomerGroups(lang);
    }

    private List<DictionaryData> getCustomerStatuses(Language lang) {
        return dictionariesJdbcRepository.getCustomerStatuses(lang);
    }

    private List<DictionaryData> getAllergens(Language lang) {
        return dictionariesJdbcRepository.getAllergens(lang);
    }

    private List<DictionaryData> getLanguages(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (Language.US == lang || Language.EN == lang) {
            list.add(new DictionaryData("en", "English", lang.getCode()));
            list.add(new DictionaryData("pl", "Polish", lang.getCode()));
        } else if (Language.PL == lang) {
            list.add(new DictionaryData("en", "Angielski", lang.getCode()));
            list.add(new DictionaryData("pl", "Polski", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getRoles(Language lang) {
        return dictionariesJdbcRepository.getRoles(lang);
    }

    private List<DictionaryData> getAuthors(Language lang) {
        return dictionariesJdbcRepository.getAuthors(lang);
    }

    private List<DictionaryData> getCourseOrderStatuses(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (Language.US == lang || Language.EN == lang) {
            list.add(new DictionaryData("p", "Paid", lang.getCode()));
            list.add(new DictionaryData("u", "Unpaid", lang.getCode()));
            list.add(new DictionaryData("c", "Canceled", lang.getCode()));
            list.add(new DictionaryData("w", "Pending", lang.getCode()));
        } else if (Language.PL == lang) {
            list.add(new DictionaryData("p", "Opłacone", lang.getCode()));
            list.add(new DictionaryData("u", "Nieopłacone", lang.getCode()));
            list.add(new DictionaryData("c", "Anulowane", lang.getCode()));
            list.add(new DictionaryData("w", "Oczekujące", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getInvoiceTypes(Language lang) {

        List<DictionaryData> list = new ArrayList<>();

        if (Language.US == lang || Language.EN == lang) {
            list.add(new DictionaryData(InvoiceType.STANDARD.getCode(), "Standard", lang.getCode()));
            list.add(new DictionaryData(InvoiceType.CORRECTION.getCode(), "Correction", lang.getCode()));
        } else if (Language.PL == lang) {
            list.add(new DictionaryData(InvoiceType.STANDARD.getCode(), "Standardowa", lang.getCode()));
            list.add(new DictionaryData(InvoiceType.CORRECTION.getCode(), "Korekcyjna", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getBillingTypes(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (Language.US == lang || Language.EN == lang) {
            list.add(new DictionaryData(BillingType.PRIVATE_PERSON.getCode(), "Private person", lang.getCode()));
            list.add(new DictionaryData(BillingType.COMPANY.getCode(), "Company", lang.getCode()));
        } else if (Language.PL == lang) {
            list.add(new DictionaryData(BillingType.PRIVATE_PERSON.getCode(), "Osoba prywatna", lang.getCode()));
            list.add(new DictionaryData(BillingType.COMPANY.getCode(), "Firma", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getCountries(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        String[] countryCodes = Locale.getISOCountries();

        for (String countryCode : countryCodes) {
            Locale locale = new Locale("", countryCode);
            String code = locale.getCountry().toLowerCase();
            String value = locale.getDisplayCountry(Locale.forLanguageTag(lang.toString()));
            list.add(new DictionaryData(code, value, lang.getCode()));
        }
        return list;
    }

    private List<DictionaryData> getCourseVisibilityStatuses(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == Language.PL) {
            list.add(new DictionaryData(CourseVisibilityStatus.INVISIBLE.getValue(), "Niewidoczny", lang.getCode()));
            list.add(new DictionaryData(CourseVisibilityStatus.VISIBLE.getValue(), "Widoczny", lang.getCode()));
        } else {
            list.add(new DictionaryData(CourseVisibilityStatus.INVISIBLE.getValue(), "Invisible", lang.getCode()));
            list.add(new DictionaryData(CourseVisibilityStatus.VISIBLE.getValue(), "Visible", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getLessonCommentStatuses(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (Language.PL == lang) {
            list.add(new DictionaryData(LessonCommentStatus.INVISIBLE.getValue(), "Niewidoczny", lang.getCode()));
            list.add(new DictionaryData(LessonCommentStatus.VISIBLE.getValue(), "Widoczny", lang.getCode()));
        } else {
            list.add(new DictionaryData(LessonCommentStatus.INVISIBLE.getValue(), "Invisible", lang.getCode()));
            list.add(new DictionaryData(LessonCommentStatus.VISIBLE.getValue(), "Visible", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getLessonVisibilityStatuses(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (Language.PL == lang) {
            list.add(new DictionaryData(LessonVisibilityStatus.INVISIBLE.getValue(), "Niewidoczny", lang.getCode()));
            list.add(new DictionaryData(LessonVisibilityStatus.VISIBLE.getValue(), "Widoczny", lang.getCode()));
        } else {
            list.add(new DictionaryData(LessonVisibilityStatus.INVISIBLE.getValue(), "Invisible", lang.getCode()));
            list.add(new DictionaryData(LessonVisibilityStatus.VISIBLE.getValue(), "Visible", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getModuleVisibilityStatuses(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (Language.PL == lang) {
            list.add(new DictionaryData(ModuleVisibilityStatus.INVISIBLE.getValue(), "Niewidoczny", lang.getCode()));
            list.add(new DictionaryData(ModuleVisibilityStatus.VISIBLE.getValue(), "Widoczny", lang.getCode()));
        } else {
            list.add(new DictionaryData(ModuleVisibilityStatus.INVISIBLE.getValue(), "Invisible", lang.getCode()));
            list.add(new DictionaryData(ModuleVisibilityStatus.VISIBLE.getValue(), "Visible", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getCourseSaleStatuses(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == Language.PL) {
            list.add(new DictionaryData(CourseSaleStatus.OPEN.getCode(), "Otwarta", lang.getCode()));
            list.add(new DictionaryData(CourseSaleStatus.CLOSED.getCode(), "Zamknięta", lang.getCode()));
        } else {
            list.add(new DictionaryData(CourseSaleStatus.OPEN.getCode(), "Open", lang.getCode()));
            list.add(new DictionaryData(CourseSaleStatus.CLOSED.getCode(), "Closed", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getLessonTypes(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == Language.PL) {
            list.add(new DictionaryData(LessonType.STANDARD.getCode(), "Standardowa", lang.getCode()));
            list.add(new DictionaryData(LessonType.TASK.getCode(), "Zadanie", lang.getCode()));
        } else {
            list.add(new DictionaryData(LessonType.STANDARD.getCode(), "Standard", lang.getCode()));
            list.add(new DictionaryData(LessonType.TASK.getCode(), "Task", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getMovieLinkTypes(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == Language.PL) {
            list.add(new DictionaryData(MovieLinkType.NO_MOVIE.getCode(), "Bez filmu", lang.getCode()));
            list.add(new DictionaryData(MovieLinkType.YOUTUBE.getCode(), "YouTube", lang.getCode()));
            list.add(new DictionaryData(MovieLinkType.VIMEO.getCode(), "Vimeo", lang.getCode()));
        } else {
            list.add(new DictionaryData(MovieLinkType.NO_MOVIE.getCode(), "No movie", lang.getCode()));
            list.add(new DictionaryData(MovieLinkType.YOUTUBE.getCode(), "YouTube", lang.getCode()));
            list.add(new DictionaryData(MovieLinkType.VIMEO.getCode(), "Vimeo", lang.getCode()));
        }

        return list;
    }

    private List<DictionaryData> getVoivodeshipTypes(Language lang) {
        List<DictionaryData> list = new ArrayList<>();

        if (lang == Language.PL) {
            list.add(new DictionaryData("02", "DOLNOŚLĄSKIE", lang.getCode()));
            list.add(new DictionaryData("04", "KUJAWSKO-POMORSKIE", lang.getCode()));
            list.add(new DictionaryData("06", "LUBELSKIE", lang.getCode()));
            list.add(new DictionaryData("08", "LUBUSKIE", lang.getCode()));
            list.add(new DictionaryData("10", "ŁÓDZKIE", lang.getCode()));
            list.add(new DictionaryData("12", "MAŁOPOLSKIE", lang.getCode()));
            list.add(new DictionaryData("14", "MAZOWIECKIE", lang.getCode()));
            list.add(new DictionaryData("16", "OPOLSKIE", lang.getCode()));
            list.add(new DictionaryData("18", "PODKARPACKIE", lang.getCode()));
            list.add(new DictionaryData("20", "PODLASKIE", lang.getCode()));
            list.add(new DictionaryData("22", "POMORSKIE", lang.getCode()));
            list.add(new DictionaryData("24", "ŚLĄSKIE", lang.getCode()));
            list.add(new DictionaryData("26", "ŚWIĘTOKRZYSKIE", lang.getCode()));
            list.add(new DictionaryData("28", "WARMIŃSKO-MAZURSKIE", lang.getCode()));
            list.add(new DictionaryData("30", "WIELKOPOLSKIE", lang.getCode()));
            list.add(new DictionaryData("32", "ZACHODNIOPOMORSKIE", lang.getCode()));
            list.add(new DictionaryData("XX", "NIEOKREŚLONE", lang.getCode()));
        } else {
            list.add(new DictionaryData("02", "DOLNOŚLĄSKIE", lang.getCode()));
            list.add(new DictionaryData("04", "KUJAWSKO-POMORSKIE", lang.getCode()));
            list.add(new DictionaryData("06", "LUBELSKIE", lang.getCode()));
            list.add(new DictionaryData("08", "LUBUSKIE", lang.getCode()));
            list.add(new DictionaryData("10", "ŁÓDZKIE", lang.getCode()));
            list.add(new DictionaryData("12", "MAŁOPOLSKIE", lang.getCode()));
            list.add(new DictionaryData("14", "MAZOWIECKIE", lang.getCode()));
            list.add(new DictionaryData("16", "OPOLSKIE", lang.getCode()));
            list.add(new DictionaryData("18", "PODKARPACKIE", lang.getCode()));
            list.add(new DictionaryData("20", "PODLASKIE", lang.getCode()));
            list.add(new DictionaryData("22", "POMORSKIE", lang.getCode()));
            list.add(new DictionaryData("24", "ŚLĄSKIE", lang.getCode()));
            list.add(new DictionaryData("26", "ŚWIĘTOKRZYSKIE", lang.getCode()));
            list.add(new DictionaryData("28", "WARMIŃSKO-MAZURSKIE", lang.getCode()));
            list.add(new DictionaryData("30", "WIELKOPOLSKIE", lang.getCode()));
            list.add(new DictionaryData("32", "ZACHODNIOPOMORSKIE", lang.getCode()));
            list.add(new DictionaryData("XX", "NIEOKREŚLONE", lang.getCode()));
        }
        return list;
    }
}
