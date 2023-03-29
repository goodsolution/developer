package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.PeopleData;
import pl.com.goodsolution.adviser.domain.courseplatform.PeopleFilter;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactsService {
    private ContactsRepository contactsRepository;
    private CourseCustomersService courseCustomersService;
    private CustomerAuthoritiesService customerAuthoritiesService;

    public ContactsService(ContactsRepository contactsRepository, CourseCustomersService courseCustomersService, CustomerAuthoritiesService customerAuthoritiesService) {
        this.contactsRepository = contactsRepository;
        this.courseCustomersService = courseCustomersService;
        this.customerAuthoritiesService = customerAuthoritiesService;
    }

    public List<PeopleData> find(PeopleFilter filter) {
        return contactsRepository.find(filter);
    }

    public void delete(Long id) {

        PeopleData peopleData = find(new PeopleFilter(id)).get(0);
        contactsRepository.update(new PeopleData(peopleData,LocalDateTime.now()));
    }

//    public JobOfferData get(Long id) {
//        return find(new JobOffersFilter(id)).get(0);
//    }

    public void create(PeopleData data) {
        validate(data);
        contactsRepository.create(data);

    }

    private void validate(PeopleData data) {
        validateFirstName(data.getFirstName());
        validateLastName(data.getLastName());
        validateEmail(data.getEmail());
        validatePhone(data.getPhone());

    }

    private void validateFirstName(String firstName) {
        if (firstName == null || firstName.equals("")) {
            throw new IllegalArgumentException("First name can't be empty");
        } else if (firstName.length() > 1500) {
            throw new IllegalArgumentException("First name too long, max 1500 characters!");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || lastName.equals("")) {
            throw new IllegalArgumentException("Last name can't be empty");
        } else if (lastName.length() > 1500) {
            throw new IllegalArgumentException("Last name too long, max 1500 characters!");
        }
    }
    private void validateEmail(String email) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email can't be empty");
        } else if (email.length() > 1500) {
            throw new IllegalArgumentException("Email too long, max 1500 characters!");
        }
    }
    private void validatePhone(String phone) {
        if (phone == null || phone.equals("")) {
            throw new IllegalArgumentException("Phone can't be empty");
        } else if (phone.length() > 1500) {
            throw new IllegalArgumentException("Phone too long, max 1500 characters!");
        }
    }


}