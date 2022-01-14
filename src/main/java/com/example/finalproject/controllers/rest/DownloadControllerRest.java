package com.example.finalproject.controllers.rest;

import com.example.finalproject.exceptions.MulticriteriaValidationException;
import com.example.finalproject.services.MulticriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/download")
public class DownloadControllerRest {
    private static final String EXCEL_FOLDER_DIRECTORY = "../files/SafetyCarExcelFiles";
    private static final String MCRITERIA_SAVING_EXCEPTION = "Something went wrong with file saving, please make sure that everything is ok!";

    private MulticriteriaService multicriteriaService;

    @Autowired
    public DownloadControllerRest(MulticriteriaService multicriteriaService) {
        this.multicriteriaService = multicriteriaService;
    }

    @RequestMapping("/multicriteria")
    public void downloadPDFResource(HttpServletResponse response) {

        String fileName = multicriteriaService.getCurrentMulticriteria().getDirectory();

        response.setContentType("application/excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

        Path path = Paths.get(EXCEL_FOLDER_DIRECTORY, fileName);
        try {
            Files.copy(path, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException ex) {
            throw new MulticriteriaValidationException(MCRITERIA_SAVING_EXCEPTION);
        }

    }
}
