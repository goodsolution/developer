package pl.com.goodsolution.adviser.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.com.goodsolution.adviser.config.ApplicationConfig;
import pl.com.goodsolution.adviser.domain.GalleryImageKind;
import pl.com.goodsolution.adviser.domain.ImageData;
import pl.com.goodsolution.adviser.domain.UploadResult;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class GalleryService {
    private static final Logger log = LoggerFactory.getLogger(GalleryService.class);
    private GalleryJdbcRepository galleryJdbcRepository;
    private ApplicationConfig applicationConfig;
    private CommonJdbcRepository commonJdbcRepository;

    public GalleryService(GalleryJdbcRepository galleryJdbcRepository, ApplicationConfig applicationConfig, CommonJdbcRepository commonJdbcRepository) {
        this.galleryJdbcRepository = galleryJdbcRepository;
        this.applicationConfig = applicationConfig;
        this.commonJdbcRepository = commonJdbcRepository;
    }

    public List<ImageData> findImages(Long[] dietId, String status, Long page, Long pageSize) {
        long startTime = System.currentTimeMillis();
        List<Map<String, Object>> rows = galleryJdbcRepository.findImages(dietId, status, pageSize * (page - 1L), pageSize);
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by {} is {}", "findStatistics", timeTaken);

        List<ImageData> list = new ArrayList<>();
        for (Map row : rows) {
            ImageData data = new ImageData(
                    Long.valueOf(String.valueOf(row.get("id"))),
                    (String) row.get("image_file_name"),
                    "file://" + applicationConfig.getPathToGallery());
            list.add(data);
        }
        return list;
    }

    public void deleteImage(Long id) {
        ImageData image = getImage(id);
        galleryJdbcRepository.deleteImage(id);
    }

    private void removeImage(ImageData image) {
        File file = new File(applicationConfig.getPathToGallery() + image.getFileName());
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            throw new IllegalStateException("Problem during delete image: " + image.getFileName() + " " + e.getMessage());
        }
    }

    public void modifyImage(ImageData image) {
        try {
            validateImage(image);
        } catch (IOException e) {
            throw new IllegalStateException("Problem during modify image: " + image.getFileName() + " " + e.getMessage());
        }
        galleryJdbcRepository.modifyImage(image);
        galleryJdbcRepository.deleteAssignedDiets(image.getId());
        assignDiets(image);
    }

    public void addImage(ImageData image) {
        try {
            validateImage(image);
        } catch (IOException e) {
            throw new IllegalStateException("Problem during add image: " + image.getFileName() + " " + e.getMessage());
        }
        galleryJdbcRepository.addImage(image);
        Long id = commonJdbcRepository.getLastInsertedId();
        if (image.getDietIds() != null) {
            for (Long dietId : image.getDietIds()) {
                galleryJdbcRepository.createAssignedDiet(id, dietId);
            }
        }
    }

    public UploadResult storeFile(Long id, MultipartFile file) throws IOException {
        String fileName =  UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(applicationConfig.getPathToGallery() + fileName);
        Files.write(fileNameAndPath, file.getBytes());
        return new UploadResult(fileName);
    }

    public ImageData getImage(Long id) {
        ImageData image = galleryJdbcRepository.getImage(id);
        image = new ImageData(image, getAssignedDiets(id));
        return image;
    }

    public Long[] getAssignedDiets(Long id) {
        return galleryJdbcRepository.getAssignedDiets(id);
    }

    public void clean() {
        log.trace("Gallery cleaner started");

        List<ImageData> images = findImages(null, null, 1L, 1000000L);
        Set<String> existingFiles = new HashSet<>();
        for (ImageData image : images) {
            existingFiles.add(image.getFileName());
        }


        File actual = new File(applicationConfig.getPathToGallery());
        for( File f : actual.listFiles()){
            if (f.isFile()) {
                log.debug(f.getName());
                if (!existingFiles.contains(f.getName())) {
                    removeImage(new ImageData(f.getName()));
                    log.debug("Removed: " + f.getName());
                }
            }
        }
        log.trace("Gallery cleaner finished");
    }

    private void validateImage(ImageData image) throws IOException {
        Path path = new File(applicationConfig.getPathToGallery() + image.getFileName()).toPath();
        String mimeType = Files.probeContentType(path);
        log.debug("mimeType: " + mimeType);
        if (incorrectMimeType(mimeType)) {
            throw new IllegalArgumentException("Do galerii możesz dodawać tylko pliki *.jpg lub *.png");
        }
        if (incorrectSize(path, image.getKind())) {
            throw new IllegalArgumentException("Niepoprawny rozmiar zdjęcia, prawidłowy rozmiar: " + getValidWidth(image.getKind()) + "x" + getValidHeight(image.getKind()));
        };
    }

    private int getValidWidth(GalleryImageKind kind) {
        switch (kind) {
            case MENU:
                return 1237;
            case GALLERY_VERTICAL:
                return 369;
            case GALLERY_HORIZONTAL:
                return 1180;
        }
        return -1;
    }

    private int getValidHeight(GalleryImageKind kind) {
        switch (kind) {
            case MENU:
                return 933;
            case GALLERY_VERTICAL:
                return 320;
            case GALLERY_HORIZONTAL:
                return 500;
        }
        return -1;
    }

    private boolean incorrectSize(Path path, GalleryImageKind kind) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(path.toFile());
        int width  = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        log.debug("width: " + width + " height: " + height);
        if (width != getValidWidth(kind)) {
            return true;
        }
        if (height != getValidHeight(kind)) {
            return true;
        }
        return false;
    }

    private boolean incorrectMimeType(String mimeType) {
        Set<String> acceptedMimeTypes = new HashSet<>();
        acceptedMimeTypes.add("image/png");
        acceptedMimeTypes.add("image/jpg");
        acceptedMimeTypes.add("image/jpeg");

        if (acceptedMimeTypes.contains(mimeType)) {
            return false;
        }
        return true;
    }

    private void assignDiets(ImageData image) {
        if (image.getDietIds() != null) {
            for (Long dietId : image.getDietIds()) {
                galleryJdbcRepository.createAssignedDiet(image.getId(), dietId);
            }
        }
    }
}
