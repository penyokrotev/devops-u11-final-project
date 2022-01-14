package com.example.finalproject.mockobjects;

import com.example.finalproject.components.FileReader;
import com.example.finalproject.components.FileSaver;
import com.example.finalproject.exceptions.MulticriteriaValidationException;
import com.example.finalproject.models.OfferMulticriteria;
import com.example.finalproject.models.User;
import com.example.finalproject.repositories.OfferRepository;
import com.example.finalproject.services.MulticriteriaService;
import com.example.finalproject.services.MulticriteriaServiceImpl;
import com.example.finalproject.services.UserService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MultriCriteriaServiceImplTests {

    @Mock
    OfferMulticriteria offerMulticriteria;

    @Mock
    UserService userService;

    @Mock
    FileReader fileReader;

    @Mock
    FileSaver fileSaver;

    @Mock
    OfferRepository offerRepository;

    @InjectMocks
    private MulticriteriaServiceImpl multicriteriaService;

    @Test
    public void updateShouldNotThrowException() {
        OfferMulticriteria offerMulticriteria1 = new OfferMulticriteria(1,
                0, 722, 0, 19, new BigDecimal(403.25));

        OfferMulticriteria offerMulticriteria2 = new OfferMulticriteria(2,
                0, 722, 20, 999, new BigDecimal(413.25));

        OfferMulticriteria offerMulticriteria3 = new OfferMulticriteria(3,
                723, 1309, 0, 19, new BigDecimal(529.63));

        OfferMulticriteria offerMulticriteria4 = new OfferMulticriteria(4,
                723, 1309, 20, 999, new BigDecimal(539.63));

        OfferMulticriteria offerMulticriteria5 = new OfferMulticriteria(5,
                1310, 2356, 0, 19, new BigDecimal(690.96));

        OfferMulticriteria offerMulticriteria6 = new OfferMulticriteria(6,
                1310, 2356, 20, 999, new BigDecimal(700.96));

        OfferMulticriteria offerMulticriteria7 = new OfferMulticriteria(7,
                2357, 2880, 0, 19, new BigDecimal(862.86));

        OfferMulticriteria offerMulticriteria8 = new OfferMulticriteria(8,
                2357, 2880, 20, 999, new BigDecimal(892.89));

        OfferMulticriteria offerMulticriteria9 = new OfferMulticriteria(9,
                2881, 4188, 0, 19, new BigDecimal(957.89));

        OfferMulticriteria offerMulticriteria10 = new OfferMulticriteria(10,
                2881, 4188, 20, 999, new BigDecimal(987.89));

        OfferMulticriteria offerMulticriteria11 = new OfferMulticriteria(11,
                4189, 5497, 0, 19, new BigDecimal(1076.62));

        OfferMulticriteria offerMulticriteria12 = new OfferMulticriteria(12,
                4189, 5497, 20, 999, new BigDecimal(1106.62));

        OfferMulticriteria offerMulticriteria13 = new OfferMulticriteria(13,
                5498, 9999, 0, 19, new BigDecimal(1193.25));

        OfferMulticriteria offerMulticriteria14 = new OfferMulticriteria(14,
                5498, 9999, 20, 999, new BigDecimal(1263.25));

        List<OfferMulticriteria> list = new ArrayList<>();
        list.add(offerMulticriteria1);
        list.add(offerMulticriteria2);
        list.add(offerMulticriteria3);
        list.add(offerMulticriteria4);
        list.add(offerMulticriteria5);
        list.add(offerMulticriteria6);
        list.add(offerMulticriteria7);
        list.add(offerMulticriteria8);
        list.add(offerMulticriteria9);
        list.add(offerMulticriteria10);
        list.add(offerMulticriteria11);
        list.add(offerMulticriteria12);
        list.add(offerMulticriteria13);
        list.add(offerMulticriteria14);

        MockMultipartFile multipartFile = new MockMultipartFile("file",
                "asdxlsx", "smth/jpg", "test".getBytes());

        User user = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");

        Mockito.when(fileReader.readExcelFile(multipartFile)).thenReturn(list);
        Mockito.when(userService.getCurrentUser()).thenReturn(user);
        Mockito.when(fileSaver.saveFile(multipartFile,"xlsx")).thenReturn("asd/asd/asd/asd");

        Assert.assertEquals(multicriteriaService.update(multipartFile), "successful");
    }


    @Test
    public void check_Base_amount() {
        OfferMulticriteria offerMulticriteria1 = new OfferMulticriteria(1,
                0, 1047, 0, 19, new BigDecimal(403.25));

        OfferMulticriteria offerMulticriteria2 = new OfferMulticriteria(2,
                0, 1047, 20, 999, new BigDecimal(413.25));

        Assert.assertFalse(offerMulticriteria2.getBaseAmount().compareTo(offerMulticriteria1.getBaseAmount()) < 0);
    }

    @Test
    public void check_Base_amount_For_Mistakes() throws MulticriteriaValidationException {
        OfferMulticriteria offerMulticriteria1 = new OfferMulticriteria(1,
                0, 1047, 0, 19, new BigDecimal(403.25));

        OfferMulticriteria offerMulticriteria2 = new OfferMulticriteria(2,
                0, 1047, 20, 999, new BigDecimal(413.25));

        Assert.assertTrue(offerMulticriteria2.getBaseAmount().compareTo(offerMulticriteria1.getBaseAmount()) > 0);
    }

}
