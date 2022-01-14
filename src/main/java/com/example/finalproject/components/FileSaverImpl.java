package com.example.finalproject.components;

import com.example.finalproject.exceptions.InvalidFileException;
import com.example.finalproject.exceptions.InvalidTypeOfImageFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class FileSaverImpl implements FileSaver {

    private static final String SAVE_IMAGE_FOLDER = "../files/SafetyCarImagesOVRC/";
    private static final String IMAGE_TYPE_EM = "Image should be png/jpg/jpeg";
    private static final String[] LIST_OF_IMAGE_FILE_TYPES_ALLOWED = {"png", "jpg", "jpeg"};
    private static final String IMAGE_SAVE_EM = "Problem with saving the image occurred";
    private static final String EXCEL_TYPE_OF_FILE = "xlsx";
    private static final String NOT_AN_EXCEL_FILE = "Not an Excel file!";
    private static final String SAVE_EXCEL_FOLDER = "../files/SafetyCarExcelFiles/";
    private static final String IMAGES_OVRC = "/images/ovrc/";


    public String saveFile(MultipartFile imageFile, String fileType) throws InvalidTypeOfImageFileException, InvalidFileException {
        String saveFileFolder = SAVE_IMAGE_FOLDER;
        String errorMessage = IMAGE_SAVE_EM;
        String dbFileFolder = IMAGES_OVRC;
        if (fileType.equals(EXCEL_TYPE_OF_FILE)) {
            saveFileFolder = SAVE_EXCEL_FOLDER;
            errorMessage = NOT_AN_EXCEL_FILE;
            dbFileFolder = SAVE_EXCEL_FOLDER;
        }
        try {
            byte[] bytes = imageFile.getBytes();
            String dateTime = LocalDateTime.now().toString();
            dateTime = dateTime.replace(":", "-");
            dateTime = dateTime.replace("T", "-");
            dateTime = dateTime.replace(".", "-");
            Path path = Paths.get(saveFileFolder + dateTime + "." + fileType);
            Files.write(path, bytes);
            return String.format("%s%s.%s", dbFileFolder, dateTime, fileType);
        } catch (Exception ex) {
            throw new InvalidFileException(errorMessage);
        }
    }

    public String checkIfImageTypeIsValid(String fileType) throws InvalidTypeOfImageFileException {
        List<String> listOfImageTypes = Arrays.asList(LIST_OF_IMAGE_FILE_TYPES_ALLOWED);
        if (!listOfImageTypes.contains(fileType)) {
            throw new InvalidTypeOfImageFileException(IMAGE_TYPE_EM);
        } else {
            return fileType;
        }
    }

}
