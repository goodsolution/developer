package pl.com.goodsolution.adviser.logic.courseplatform;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.com.goodsolution.adviser.config.ApplicationConfig;
import pl.com.goodsolution.adviser.config.courseplatform.CoursePlatformConfig;
import pl.com.goodsolution.adviser.domain.courseplatform.FileData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FilesService {

    private CoursePlatformConfig coursePlatformConfig;
    private ApplicationConfig applicationConfig;
    private FilesJdbcRepository filesJdbcRepository;
    private static final Logger log = LoggerFactory.getLogger(FilesService.class);

    public static final String PNG_CONTENT_TYPE = "image/png";
    public static final String PNG_FILE_EXTENSION = ".png";
    public static final String JPG_CONTENT_TYPE = "image/jpeg";
    public static final String JPG_FILE_EXTENSION = ".jpg";
    public static final String PDF_CONTENT_TYPE = "application/pdf";
    public static final String PDF_FILE_EXTENSION = ".pdf";
    public static final String ZIP_CONTENT_TYPE = "application/x-zip-compressed";
    public static final String ZIP_FILE_EXTENSION = ".zip";
    public static final String DOC_CONTENT_TYPE = "application/msword";
    public static final String DOC_FILE_EXTENSION = ".doc";

    public FilesService(CoursePlatformConfig coursePlatformConfig, ApplicationConfig applicationConfig, FilesJdbcRepository filesJdbcRepository) {
        this.coursePlatformConfig = coursePlatformConfig;
        this.applicationConfig = applicationConfig;
        this.filesJdbcRepository = filesJdbcRepository;
    }

    @Transactional
    public Long create(MultipartFile multipartFile) {
        validate(multipartFile);
        String name = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();
        FileData file = new FileData(name, multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize());
        Long fileId = filesJdbcRepository.create(file);
        saveOnServer(multipartFile, name);
        return fileId;
    }

    public FileData get(Long id) {
        return filesJdbcRepository.get(id);
    }

    public byte[] getFile(Long id) {
        FileData data = get(id);
        try {
            File file = ResourceUtils.getFile(applicationConfig.getCoursePathToFilesFolder() + data.getName());
            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("Error during getting file ID: " + id);
            return null;
        }
    }

    public String saveImageOnServer(MultipartFile imageFile) {
        try {
            String fileName = UUID.randomUUID().toString() + getFileExtension(imageFile);
            Path path = Paths.get(coursePlatformConfig.getPathToGallery() + fileName);
            Files.write(path, imageFile.getBytes());
            return fileName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String saveInvoiceOnServer(MultipartFile invoiceFile) {
        try {
            String fileName = UUID.randomUUID().toString() + getFileExtension(invoiceFile);
            Path path = Paths.get(applicationConfig.getCoursePathToInvoicesFolder() + fileName);
            Files.write(path, invoiceFile.getBytes());
            return fileName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String saveMemeOnServer(MultipartFile memeFile) {
        try {
            String fileName = UUID.randomUUID().toString() + getFileExtension(memeFile);
            Path path = Paths.get(applicationConfig.getCoursePathToMemesFolder() + fileName);
            Files.write(path, memeFile.getBytes());
            return fileName;
        } catch (NoSuchFileException ex) {
            throw new IllegalArgumentException("Invalid path for memes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveCourseAttachmentOnServer(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID().toString() + UUID.randomUUID();
            Path path = Paths.get(applicationConfig.getCoursePathToCourseAttachmentsFolder() + fileName);
            Files.write(path, file.getBytes());
            return fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String duplicateCourseAttachment(String fileName) {
        String duplicatedFileName = UUID.randomUUID().toString() + UUID.randomUUID();
        Path toDuplication = Paths.get(applicationConfig.getCoursePathToCourseAttachmentsFolder() + fileName);
        Path duplicationTarget = Paths.get(applicationConfig.getCoursePathToCourseAttachmentsFolder() + duplicatedFileName);
        try{
            Files.copy(toDuplication, duplicationTarget);
            return duplicatedFileName;
        } catch (Exception e) {
            throw new IllegalArgumentException("Something went wrong with course attachment duplication");
        }
    }

    public byte[] getMemeFile(String fileName) {
        try {
            File file = ResourceUtils.getFile(applicationConfig.getCoursePathToMemesFolder() + fileName);
            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("Error during getting meme file");
            return null;
        }
    }

    public void deleteMemeFromServer(String fileName) {
        try {
            Path fileToDeletePath = Paths.get(applicationConfig.getCoursePathToMemesFolder() + fileName);
            Files.delete(fileToDeletePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public byte[] getImage(String name) {
        try {
            File file = ResourceUtils.getFile(applicationConfig.getCoursePathToGallery() + name);
            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("Error during getting image");
            return null;
        }
    }

    public byte[] getInvoice(String name) {
        try {
            File file = ResourceUtils.getFile(applicationConfig.getCoursePathToInvoicesFolder() + name);
            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("Error during getting invoice");
            return null;
        }
    }

    public byte[] getCourseAttachment(String name) {
        try {
            File file = ResourceUtils.getFile(applicationConfig.getCoursePathToCourseAttachmentsFolder() + name);
            InputStream in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("Error during getting course attachment");
            return null;
        }
    }

    private String getFileExtension(MultipartFile file) {

        String contentType = file.getContentType();

        if(contentType == null) {
            throw new IllegalArgumentException("Wrong file");
        }

        switch (contentType) {
            case PNG_CONTENT_TYPE:
                return PNG_FILE_EXTENSION;
            case JPG_CONTENT_TYPE:
                return JPG_FILE_EXTENSION;
            case PDF_CONTENT_TYPE:
                return PDF_FILE_EXTENSION;
            case ZIP_CONTENT_TYPE:
                return ZIP_FILE_EXTENSION;
            case DOC_CONTENT_TYPE:
                return DOC_FILE_EXTENSION;
        }

        throw new IllegalArgumentException("Incorrect file type: " + contentType);

    }

    private void validate(MultipartFile file) {

        if(file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File can't be empty!");
        }

        if(file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
            throw new IllegalArgumentException("Original file name can't be empty");
        } else if(file.getOriginalFilename().length() > 5000) {
            throw new IllegalArgumentException("Original file name too long, max 5000 characters");
        } else if (file.getOriginalFilename().length() < 5) {
            throw new IllegalArgumentException("Original file name is too short, min 5 characters");
        }
    }

    private void saveOnServer(MultipartFile multipartFile, String name) {
        try {
            Path path = Paths.get(applicationConfig.getCoursePathToFilesFolder() + name);
            Files.write(path, multipartFile.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Failed to save file on server");
        }
    }
}