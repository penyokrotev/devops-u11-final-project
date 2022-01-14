package com.example.finalproject.mockobjects;

import com.example.finalproject.components.AuthenticationMediator;
import com.example.finalproject.exceptions.UserDoesntExistException;
import com.example.finalproject.models.User;
import com.example.finalproject.models.UserReg;
import com.example.finalproject.repositories.UserRepository;
import com.example.finalproject.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.swing.UIManager.get;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTests {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private AuthenticationMediator authentication;

    @InjectMocks
    private UserServiceImpl userService;




    @Test
    public void getAll_Should_Return_Users() {

        List<User> expectedList = new ArrayList<>();
        expectedList.add(new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", ""));
        expectedList.add(new User(2, "petko123@abv.bg", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", ""));
        Mockito.when(mockUserRepository.getAll()).thenReturn(expectedList);

        List<User> usersFound = userService.getAll();

        Assert.assertEquals(expectedList, usersFound);
    }

    @Test
    public void getByIdUser_Should_ReturnUser_When_IdExists() throws UserDoesntExistException {
        // Arrange
        Mockito.when(mockUserRepository.getById(1))
                .thenReturn(new User(1, "user@mail.com", "First Name", "Middle Name",
                        "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", ""));

        // Act
        User result = userService.getById(1);

        // Assert
        Assert.assertEquals(1, result.getId());
    }

    @Test(expected = UserDoesntExistException.class)
    public void getByIdUser_Should_ThrowException_When_IdNotExists() throws UserDoesntExistException {
        // Arrange

        // Act
        userService.getById(1);

        // Assert
        verify(mockUserRepository, Mockito.never()).getById(1);
    }

    @Test
    public void getByEmailUser_Should_ReturnUser_When_EmailExists() throws UserDoesntExistException {
        Mockito.when(mockUserRepository.getByName("user@mail.com")).thenReturn(new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", ""));

        User result = userService.getByName("user@mail.com");

        Assert.assertEquals("user@mail.com", result.getEmail());
    }

    @Test(expected = ResponseStatusException.class)
    public void getByEmailUser_Should_ThrowException_When_EmailNotExists() throws ResponseStatusException {
        // Arrange

        // Act
        userService.getByName("user@mail.com");

        // Assert
        verify(mockUserRepository, Mockito.never()).getByName("user@mail.com");
    }


    @Test
    public void create_Should_CallRepositoryCreate_When_UsernameIsUnique() {
        // Arrange
        User newUser = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");

        // Act
        userService.create(newUser);

        // Assert
        verify(mockUserRepository, Mockito.times(1)).create(newUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_Should_ThrowException_When_UsernameIsNotUnique() {
        // Arrange
        User newUser = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");
        Mockito.when(mockUserRepository.getAll())
                .thenReturn(Arrays.asList(
                        new User(1, "user@mail.com", "First Name", "Middle Name",
                                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", ""),
                        new User(2, "petko123@abv.bg", "First Name", "Middle Name",
                                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", ""),
                        new User(3, "petk456o@abv.bg", "First Name", "Middle Name",
                                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "")
                ));

        // Act
        userService.create(newUser);

        // Assert
        verify(mockUserRepository, Mockito.never()).create(newUser);
    }


    @Test
    public void update_should_update_when_user__exist() throws UserDoesntExistException {

        User newUser = new User(1, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");
        when(mockUserRepository.getByName("user@mail.com")).thenReturn(newUser);
        when(authentication.getCurrentAuthenticatedUser()).thenReturn(newUser.getEmail());
        userService.update(newUser);
        verify(mockUserRepository, times(1)).update(newUser);
    }


    @Test(expected = UserDoesntExistException.class)
    public void update_Should_ThrowException_When_UserIdNotExists() throws UserDoesntExistException {
        // Arrange
        User newUser = new User(2, "user@mail.com", "First Name", "Middle Name",
                "Last Name", LocalDate.of(1994, Month.JANUARY, 1), null, "0876765324", "");

        when(userService.getById(1)).thenReturn(null);

        // Act
        userService.update(newUser);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.never()).update(newUser);
    }

//TODO GetCurrentUser From UserServiceImpl


//    @Test
//    public void mockApplicationUser() {
//        UserReg applicationUser = mock(UserReg.class);
//        Authentication authentication = mock(Authentication.class);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);
//    }

//    @Test
//    public void mockAuthentication() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        // Mockito.whens() for your authorization object
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        Mockito.when(securityContext.getAuthentication()).
//
//                thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//    }
}
