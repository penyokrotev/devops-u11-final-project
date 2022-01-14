package com.example.finalproject.components;

import com.example.finalproject.exceptions.InvalidMulticriteriaException;
import com.example.finalproject.models.OfferMulticriteria;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class FileReaderImpl implements FileReader {
    private static final String EXCEL_FILE_CORRUPT_EM = "Excel file is corrupt! Failed to read it.";

    public List<OfferMulticriteria> readExcelFile(MultipartFile excelFile) {
        XSSFWorkbook workbook;
        List<OfferMulticriteria> multicriteriaList = new ArrayList<>();
        try {
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        } catch (IOException ex) {
            throw new InvalidMulticriteriaException(EXCEL_FILE_CORRUPT_EM);
        }

        XSSFSheet worksheet = workbook.getSheetAt(0);


        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            OfferMulticriteria offerMulticriteria = new OfferMulticriteria();

            XSSFRow row = worksheet.getRow(i);
            offerMulticriteria.setMulticriteriaId(i);
            offerMulticriteria.setCcMin((int) row.getCell(0).getNumericCellValue());
            offerMulticriteria.setCcMax((int) row.getCell(1).getNumericCellValue());
            offerMulticriteria.setCarAgeMin((int) row.getCell(2).getNumericCellValue());
            offerMulticriteria.setCarAgeMax((int) row.getCell(3).getNumericCellValue());
            offerMulticriteria.setBaseAmount(new BigDecimal(row.getCell(4).getNumericCellValue()));
            multicriteriaList.add(offerMulticriteria);
        }

        return multicriteriaList;
    }
}
