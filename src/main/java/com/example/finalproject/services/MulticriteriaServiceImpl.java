package com.example.finalproject.services;

import com.example.finalproject.components.FileReader;
import com.example.finalproject.components.FileSaver;
import com.example.finalproject.exceptions.InvalidFileException;
import com.example.finalproject.exceptions.InvalidMulticriteriaException;
import com.example.finalproject.exceptions.MulticriteriaValidationException;
import com.example.finalproject.models.MulticriteriaHistory;
import com.example.finalproject.models.CurrentMulticriteria;
import com.example.finalproject.models.OfferMulticriteria;
import com.example.finalproject.models.User;
import com.example.finalproject.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MulticriteriaServiceImpl implements MulticriteriaService {
    private static final String NOT_AN_EXCEL_FILE = "Not an Excel file!";
    private static final String TYPE_OF_FILE = "xlsx";
    private static final String DATA_IN_MULTICRITERIA_FILE_IS_NOT_VALID = "Data in Multicriteria file is not valid!";
    private static final String EXCEL_FILE_EM = "Make sure that excel files ends with (.xlsx)";

    private OfferRepository offerRepository;
    private UserService userService;
    private FileSaver fileSaver;
    private FileReader fileReader;

    @Autowired
    public MulticriteriaServiceImpl(OfferRepository offerRepository, UserService userService, FileSaver fileSaver, FileReader fileReader) {
        this.offerRepository = offerRepository;
        this.userService = userService;
        this.fileSaver = fileSaver;
        this.fileReader = fileReader;
    }

    @Override
    public String update(MultipartFile reapExcelDataFile) throws MulticriteriaValidationException, InvalidMulticriteriaException, InvalidFileException {
        String fileType = validateFileType(reapExcelDataFile.getOriginalFilename());

        List<OfferMulticriteria> multicriteriaList = fileReader.readExcelFile(reapExcelDataFile);

        validateMulticriteria(multicriteriaList);

        String fileDirectory;

        fileDirectory = fileSaver.saveFile(reapExcelDataFile, fileType);

        updateDB(fileDirectory);

        for (OfferMulticriteria o : multicriteriaList) {
            offerRepository.updateMulticriteria(o);
        }
        return "successful";
    }


    private String validateFileType(String originalFilename) throws InvalidMulticriteriaException {
        try {
            originalFilename = originalFilename.substring(originalFilename.length() - 4);
        } catch (NullPointerException ex) {
            throw new InvalidMulticriteriaException(EXCEL_FILE_EM);
        }
        if (!originalFilename.equals(TYPE_OF_FILE)) {
            throw new InvalidMulticriteriaException(NOT_AN_EXCEL_FILE);
        }
        return originalFilename;
    }


    private void validateMulticriteria(List<OfferMulticriteria> list) throws MulticriteriaValidationException {
        int ccMin = 0;
        int ccMax = 0;
        int carAgeMin = 0;
        int carAgeMax = 0;
        BigDecimal baseAmount = new BigDecimal(0);

        for (int i = 0; i < list.size(); i++) {
            OfferMulticriteria o = list.get(i);

            checkForNegativeNumbers(o);

            checkIfMinIsBiggerOrEqualToMax(o);

            if (i != 0) {
                if (o.getCcMin() == ccMin) {
                    validateCarAgeRange(o.getCarAgeMin(), carAgeMax);
                } else {
                    validateCubicCapacityRange(o.getCcMin(), ccMax);
                }
                checkBaseAmount(o, baseAmount);
            }


            ccMin = o.getCcMin();
            ccMax = o.getCcMax();
            carAgeMin = o.getCarAgeMin();
            carAgeMax = o.getCarAgeMax();
            baseAmount = o.getBaseAmount();
        }
    }

    private void checkBaseAmount(OfferMulticriteria o, BigDecimal baseAmount) throws MulticriteriaValidationException {
        if (o.getBaseAmount().compareTo(baseAmount) <= 0) {
            throw new MulticriteriaValidationException(DATA_IN_MULTICRITERIA_FILE_IS_NOT_VALID);
        }
    }

    private void validateCubicCapacityRange(int ccMin, int ccMax) throws MulticriteriaValidationException {
        if (ccMax + 1 != ccMin) {
            throw new MulticriteriaValidationException(DATA_IN_MULTICRITERIA_FILE_IS_NOT_VALID);
        }
    }

    private void validateCarAgeRange(int carAgeMin, int carAgeMax) throws MulticriteriaValidationException {
        if (carAgeMax + 1 != carAgeMin) {
            throw new MulticriteriaValidationException(DATA_IN_MULTICRITERIA_FILE_IS_NOT_VALID);
        }
    }

    private void checkForNegativeNumbers(OfferMulticriteria o) throws MulticriteriaValidationException {
        if (o.getCcMin() < 0 || o.getCcMax() < 0 || o.getCarAgeMin() < 0 || o.getCarAgeMax() < 0 || o.getBaseAmount().compareTo(new BigDecimal(0)) < 0) {
            throw new MulticriteriaValidationException(DATA_IN_MULTICRITERIA_FILE_IS_NOT_VALID);
        }
    }

    private void checkIfMinIsBiggerOrEqualToMax(OfferMulticriteria o) throws MulticriteriaValidationException {
        if (o.getCcMin() >= o.getCcMax() || o.getCarAgeMin() >= o.getCarAgeMax()) {
            throw new MulticriteriaValidationException(DATA_IN_MULTICRITERIA_FILE_IS_NOT_VALID);
        }
    }


    private void updateDB(String fileDirectory) {

        User user = userService.getCurrentUser();
        LocalDate currentDate = LocalDate.now();

        MulticriteriaHistory multicriteriaHistory = new MulticriteriaHistory();
        multicriteriaHistory.setAdmin(user);
        multicriteriaHistory.setDateOfUpdate(currentDate);
        multicriteriaHistory.setDirectory(fileDirectory);

        CurrentMulticriteria currentMulticriteria = new CurrentMulticriteria();
        currentMulticriteria.setMmruId(1);
        currentMulticriteria.setDirectory(fileDirectory.split("/")[3]);

        offerRepository.updateMulticriteriaInformation(multicriteriaHistory, currentMulticriteria);
    }

    @Override
    public CurrentMulticriteria getCurrentMulticriteria() {
        return offerRepository.getCurrentMulticriteria();
    }
}


