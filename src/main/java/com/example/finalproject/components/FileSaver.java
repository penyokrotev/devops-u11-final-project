package com.example.finalproject.components;

import com.example.finalproject.exceptions.InvalidFileException;
import com.example.finalproject.exceptions.InvalidTypeOfImageFileException;
import org.springframework.web.multipart.MultipartFile;

public interface FileSaver {

    String saveFile(MultipartFile imageFile, String fileType) throws InvalidTypeOfImageFileException, InvalidFileException;

    String checkIfImageTypeIsValid(String fileType) throws InvalidTypeOfImageFileException;
}
