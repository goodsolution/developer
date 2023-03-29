package pl.com.goodsolution.adviser.logic.itube;

import org.springframework.stereotype.Service;
import pl.com.goodsolution.adviser.domain.itube.ITubeData;
import pl.com.goodsolution.adviser.domain.itube.ITubeFilter;
import pl.com.goodsolution.adviser.domain.itube.ITubePutRequestAdmin;
import pl.com.goodsolution.adviser.domain.itube.ITubeSearchFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ITubeService {
    private ITubeJdbcRepository iTubeJdbcRepository;
    private Validator validator;

    public ITubeService(ITubeJdbcRepository iTubeJdbcRepository, Validator validator) {
        this.iTubeJdbcRepository = iTubeJdbcRepository;
        this.validator = validator;
    }

    public List<ITubeData> find(ITubeFilter filter) {
        List<ITubeData> iTubeData = iTubeJdbcRepository.find(filter);
        Optional<List<ITubeData>> optionalITubeData = Optional.of(iTubeData);
        if (optionalITubeData.isPresent()) {
            return iTubeData;
        } else {
            throw new NoSuchElementException();
        }
    }

    public void updateFromPanelAdmin(ITubePutRequestAdmin dataToUpdate) {
        validator.validateTitlePl(new ITubeData(dataToUpdate.getTitlePl(), dataToUpdate.getTitleEn(), dataToUpdate.getKeywords(), dataToUpdate.getLink()));
        validator.validateTitleEn(new ITubeData(dataToUpdate.getTitlePl(), dataToUpdate.getTitleEn(), dataToUpdate.getKeywords(), dataToUpdate.getLink()));
        validator.validateKeyWords(new ITubeData(dataToUpdate.getTitlePl(), dataToUpdate.getTitleEn(), dataToUpdate.getKeywords(), dataToUpdate.getLink()));
        validator.validateWWW(new ITubeData(dataToUpdate.getTitlePl(), dataToUpdate.getTitleEn(), dataToUpdate.getKeywords(), dataToUpdate.getLink()));
        iTubeJdbcRepository.update(new ITubeData(
                dataToUpdate.getId(),
                dataToUpdate.getTitlePl(),
                dataToUpdate.getTitleEn(),
                dataToUpdate.getKeywords(),
                dataToUpdate.getLink(),
                LocalDateTime.now(),
                null
        ));
    }

    public List<ITubeData> find(ITubeSearchFilter filter) {
        List<ITubeData> iTubeData = iTubeJdbcRepository.find(filter);
        Optional<List<ITubeData>> optionalITubeData = Optional.of(iTubeData);
        if (optionalITubeData.isPresent()) {
            return iTubeData;
        } else {
            throw new NoSuchElementException();
        }
    }

    public void delete(Long id) {
        Optional<ITubeData> iTubeDataOptional = iTubeJdbcRepository.find(new ITubeFilter(id)).stream().findFirst();
        if (iTubeDataOptional.isPresent()) {
            ITubeData iTubeData = iTubeDataOptional.get();
            iTubeJdbcRepository.update(new ITubeData(
                    iTubeData.getId(),
                    iTubeData.getTitlePl(),
                    iTubeData.getTitleEn(),
                    iTubeData.getKeywords(),
                    iTubeData.getLink(),
                    iTubeData.getCreateDateTime(),
                    LocalDateTime.now()));
        } else {
            throw new NoSuchElementException();
        }
    }

    public void create(ITubeData data) {
        validator.validateTitlePl(data);
        validator.validateTitleEn(data);
        validator.validateKeyWords(data);
        validator.validateWWW(data);
        iTubeJdbcRepository.create(data);
    }

}
