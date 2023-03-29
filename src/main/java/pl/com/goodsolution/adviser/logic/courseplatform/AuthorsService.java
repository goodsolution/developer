package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.courseplatform.AuthorData;
import pl.com.goodsolution.adviser.domain.courseplatform.AuthorsFilter;

import java.util.List;

@Service
public class AuthorsService {

    private AuthorsJdbcRepository authorsJdbcRepository;

    public AuthorsService(AuthorsJdbcRepository authorsJdbcRepository) {
        this.authorsJdbcRepository = authorsJdbcRepository;
    }

    public Long create(AuthorData data) {
        validate(data);
        return authorsJdbcRepository.create(data);
    }

    public List<AuthorData> find(AuthorsFilter filter) {
        return authorsJdbcRepository.find(filter);
    }

    public void update(AuthorData data) {
        validate(data);
        authorsJdbcRepository.update(data);
    }

    public void delete(Long id) {
        try {
            authorsJdbcRepository.delete(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("You can't delete this author!");
        }
    }

    public void validate(String firstName, String lastName) {
        validate(new AuthorData(firstName, lastName));
    }

    private void validate(AuthorData data) {
        if(data.getFirstName() == null || data.getFirstName().equals("")) {
            throw new IllegalArgumentException("First name can't be empty");
        } else if (data.getFirstName().length() > 200) {
            throw new IllegalArgumentException("First name too long");
        } else if(data.getLastName() == null || data.getLastName().equals("")) {
            throw new IllegalArgumentException("Last name can't be empty");
        } else if(data.getLastName().length() > 200) {
            throw new IllegalArgumentException("Last name too long");
        }
    }
}
