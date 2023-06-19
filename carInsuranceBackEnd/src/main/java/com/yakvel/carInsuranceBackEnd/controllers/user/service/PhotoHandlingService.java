package com.yakvel.carInsuranceBackEnd.controllers.user.service;

import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.models.Ticket;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class PhotoHandlingService {
    static final Pattern pattern = Pattern.compile("[.][A-z]*");
    @Value("${upload.photo}")
    private String uploadPhotoPath;

    public String photoHandling(LocalDateTime dateTime, List<MultipartFile> photos, Person person) {
        String photoNames = "";

        try {
            Path directory = getUploadPath(dateTime, person);

            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            for (MultipartFile photo : photos) {
                String newFileName = getNewFileName(photo);
                if ("".equals(photoNames)) {
                    photoNames = newFileName;
                } else {
                    photoNames = photoNames.concat(";" + newFileName);
                }
                Path filePath = directory.resolve(newFileName);
                photo.transferTo(filePath);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return photoNames;
    }

    private String getNewFileName(MultipartFile photo) {
        String originalFilename = photo.getOriginalFilename();
        String fileExtension = getExtractedFileExtension(originalFilename);
        String getRandomUUID = UUID.randomUUID().toString();
        return getRandomUUID + fileExtension;
    }

    private Path getUploadPath(LocalDateTime dateTime, Person person) {
        String directoryPath = String
                .format(
                        "%s/user%s/%s",
                        uploadPhotoPath, person.getId(), dateTime
                );
        return Paths.get(directoryPath);
    }

    private String getExtractedFileExtension(String fileName) {
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return matcher.group();
        }
        return ".jpeg";
    }

    public boolean deletePhotos(Ticket ticket, Person person) throws IOException {
        Path directory = getUploadPath(ticket.getDateOfIncident(), person);
        File dir = new File(directory.toUri());
        File[] directoryListening = dir.listFiles();
        List<Boolean> isEachDeleted = new ArrayList<>();
        if (directoryListening == null){
           return true;
        } else  {
            for (File file : directoryListening) {
                boolean isDeleted = file.delete();
                isEachDeleted.add(isDeleted);
            }
        }
        return isEachDeleted.size() == directoryListening.length;
    }
}
