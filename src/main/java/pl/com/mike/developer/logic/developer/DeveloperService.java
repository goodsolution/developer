package pl.com.mike.developer.logic.developer;

import org.springframework.stereotype.Service;
import pl.com.mike.developer.domain.developer.DeveloperData;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<DeveloperData> getDeveloperById(DeveloperSearchFilter filter) {
        List<DeveloperData> developers = new ArrayList<>();
        if (developerRepository.findById(filter.getId()).isPresent()) {
            developerRepository.findById(filter.getId()).ifPresent(developer -> developers.add(
                    new DeveloperData(
                            developer.getId(),
                            developer.getName(),
                            developer.getAddressCountry(),
                            developer.getAddressStreet(),
                            developer.getAddressBuildingNumber(),
                            developer.getAddressFlatNumber(),
                            developer.getAddressPostalCode(),
                            developer.getTelephoneNumber(),
                            developer.getFaxNumber(),
                            developer.getEmail(),
                            developer.getTaxIdentificationNumber(),
                            developer.getCityId(),
                            developer.getLogoUrl(),
                            developer.getCode()
                    ))
            );
        } else {
            throw new NoSuchElementException();
        }
        return developers;
    }

    public DeveloperData getDeveloperByCode(DeveloperSearchFilter filter) {
        if (developerRepository.getDeveloperDataByCode(filter.getCode()).isPresent()) {
            return developerRepository.getDeveloperDataByCode(filter.getCode())
                    .map(developer -> new DeveloperData(
                            developer.getId(),
                            developer.getName(),
                            developer.getAddressCountry(),
                            developer.getAddressStreet(),
                            developer.getAddressBuildingNumber(),
                            developer.getAddressFlatNumber(),
                            developer.getAddressPostalCode(),
                            developer.getTelephoneNumber(),
                            developer.getFaxNumber(),
                            developer.getEmail(),
                            developer.getTaxIdentificationNumber(),
                            developer.getCityId(),
                            developer.getLogoUrl(),
                            developer.getCode()
                    )).orElseThrow(NoSuchElementException::new);
        } else {
            throw new NoSuchElementException();
        }
    }

}
