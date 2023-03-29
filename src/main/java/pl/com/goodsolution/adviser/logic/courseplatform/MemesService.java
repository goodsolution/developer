package pl.com.goodsolution.adviser.logic.courseplatform;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.com.goodsolution.adviser.domain.courseplatform.MemeData;
import pl.com.goodsolution.adviser.domain.courseplatform.MemeDataToChange;
import pl.com.goodsolution.adviser.domain.courseplatform.MemesFilter;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemesService {

    private FilesService filesService;
    private MemesJdbcRepository memesJdbcRepository;
    public static final Long MEME_PAGE_SIZE = 5L;

    public MemesService(FilesService filesService, MemesJdbcRepository memesJdbcRepository) {
        this.filesService = filesService;
        this.memesJdbcRepository = memesJdbcRepository;
    }

    public void create(MemeData data) {

        validate(data);
        validateFile(data.getFile());

        String fileName = filesService.saveMemeOnServer(data.getFile());
        MemeData meme = new MemeData(data.getTitle(), fileName, data.getDescription(), data.getKeywords(), LocalDateTime.now(), data.getLanguage(), data.getCode());
        memesJdbcRepository.create(meme);
    }

    public List<MemeData> find(MemesFilter filter) {
        return memesJdbcRepository.find(filter);
    }

    public void update(MemeData data) {
        validate(data);
        memesJdbcRepository.update(data);
    }

    public void delete(Long id) {
        MemeData meme = find(new MemesFilter(id)).get(0);
        filesService.deleteMemeFromServer(meme.getFileName());
        memesJdbcRepository.delete(id);
    }

    public List<MemeData> findMemesForPage(Long page) {
        return find(new MemesFilter(page, MEME_PAGE_SIZE));
    }

    public void updatePhoto(Long memeId, MultipartFile memePhoto) {
        validateFile(memePhoto);
        MemeData meme = find(new MemesFilter(memeId)).get(0);
        String oldFileName = meme.getFileName();
        String newFileName = filesService.saveMemeOnServer(memePhoto);
        update(new MemeData(meme, newFileName));
        filesService.deleteMemeFromServer(oldFileName);
    }

    public void update(Long id, MemeDataToChange dataToChange) {
        MemeData meme = find(new MemesFilter(id)).get(0);
        update(new MemeData(meme, dataToChange));
    }

    public MemeData findByCode(String code) {
        List<MemeData> memes = find(new MemesFilter(code));
        if(memes.size() == 0) {
            throw new IllegalArgumentException("Meme not found");
        } else {
            return memes.get(0);
        }
    }

    private void validate(MemeData data) {
        String title = data.getTitle();
        if(title == null || title.equals("")) {
            throw new IllegalArgumentException("Title can't be empty");
        } else if (title.length() > 200) {
            throw new IllegalArgumentException("Title too long, max 200 characters");
        }

        String description = data.getDescription();
        if(description == null) {
            throw new IllegalArgumentException("Incorrect field 'description'");
        } else if(description.length() > 5000) {
            throw new IllegalArgumentException("Description too long, max 5000 characters");
        }

        String keywords = data.getKeywords();
        if(keywords == null) {
            throw new IllegalArgumentException("Incorrect field 'keywords'");
        } else if(keywords.length() > 500) {
            throw new IllegalArgumentException("Keywords too long, max 500 characters");
        }

        String language = data.getLanguage();
        if(language == null || language.equals("")){
            throw new IllegalArgumentException("Language can't be empty");
        } else if (language.length() > 20) {
            throw new IllegalArgumentException("Language too long, max 20 characters");
        }

        String code = data.getCode();
        if(code == null || code.equals("")) {
            throw new IllegalArgumentException("Code can't be empty");
        } else if (code.length() > 300) {
            throw new IllegalArgumentException("Code too long, max 300 characters");
        } else if (!isCodeUnique(code, data.getId())) {
            throw new IllegalArgumentException("Code must be unique, this code exist in other meme");
        }
    }

    private void validateFile(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            throw new IllegalArgumentException("You must choose file");
        }
    }

    private Boolean isCodeUnique(String code, Long memeId) {
        List<MemeData> memes = find(new MemesFilter(code));
        if(memeId != null) {
            memes.removeIf(meme -> meme.getId().equals(memeId));
        }
        return memes.size() == 0;
    }

}
