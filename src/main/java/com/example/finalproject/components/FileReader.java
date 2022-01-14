package com.example.finalproject.components;

import com.example.finalproject.models.OfferMulticriteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileReader {
    List<OfferMulticriteria> readExcelFile(MultipartFile excelFile);
}
