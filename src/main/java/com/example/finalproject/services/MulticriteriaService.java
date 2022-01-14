package com.example.finalproject.services;

import com.example.finalproject.exceptions.InvalidFileException;
import com.example.finalproject.exceptions.InvalidMulticriteriaException;
import com.example.finalproject.exceptions.MulticriteriaValidationException;
import com.example.finalproject.models.CurrentMulticriteria;
import org.springframework.web.multipart.MultipartFile;

public interface MulticriteriaService {

    String update(MultipartFile reapExcelDataFile) throws MulticriteriaValidationException, InvalidMulticriteriaException, InvalidFileException;

    CurrentMulticriteria getCurrentMulticriteria();
}
