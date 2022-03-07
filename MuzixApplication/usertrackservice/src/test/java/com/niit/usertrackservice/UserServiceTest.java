package com.niit.usertrackservice;

import com.niit.usertrackservice.controller.UserTrackController;
import com.niit.usertrackservice.exception.UserAlreadyExists;
import com.niit.usertrackservice.exception.UserNotFound;
import com.niit.usertrackservice.model.Artist;
import com.niit.usertrackservice.model.Image;
import com.niit.usertrackservice.model.Tracks;
import com.niit.usertrackservice.model.User;
import com.niit.usertrackservice.repository.UserTrackRepository;
import com.niit.usertrackservice.service.UserTrackServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserTrackRepository userTrackRepository;

    @InjectMocks
    private UserTrackServiceImpl userTrackService;

    private User user;
    private Tracks tracks;
    private Artist artist;
    private Image image;
    private List<Tracks> tracksList;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void setUp()
    {
        user=new User("101","harish","123456",tracksList);
        tracks=new Tracks(1,"buleyana","5:01",artist);
        artist=new Artist("Arijit singh","male",image);
        image=new Image(12,"buleyana");
        tracksList= Arrays.asList(tracks);
        mockMvc= MockMvcBuilders.standaloneSetup(userTrackService).build();

    }
    @AfterEach
    public void resetData()
    {
        user=null;
        tracks=null;
        artist=null;
        image=null;
    }
    @Test
    public void givenProductToSaveProduct() throws UserAlreadyExists {
        when(userTrackRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(null));
        when(userTrackRepository.save(user)).thenReturn(user);
        assertEquals(user,userTrackService.registerUser(user));
        verify(userTrackRepository,times(1)).findById(user.getUserId());
        verify(userTrackRepository,times(1)).save(user);
    }
    @Test
    public void givenProductToSaveProductFailure(){
        when(userTrackRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
        assertThrows(UserAlreadyExists.class,()->userTrackService.registerUser(user));
        verify(userTrackRepository,times(1)).findById(user.getUserId());
        verify(userTrackRepository,times(0)).save(user);
    }
//    @Test
//    public void givenProductToDeleteProduct() throws Exception {
//        when(userTrackRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
//
//        User flag=userTrackService.deleteUserTrackFromWishList(user.getUserId(),tracks.getTrackId());
//
//        //assertEquals(user,flag);
//        verify(userTrackRepository,times(1)).findById(user.getUserId());
//        verify(userTrackRepository,times(1)).save(user);
//    }
    @Test
    public void givenProductToDeleteProductFailure(){
        when(userTrackRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFound.class,()->userTrackService.deleteUserTrackFromWishList(user.getUserId(),tracks.getTrackId()));

    }
    @Test
    public void gettingAllUser() throws Exception {
        when(userTrackRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
        assertEquals(user,userTrackService.getAllUserTrackFromWishList(user.getUserId()));
        verify(userTrackRepository,times(2)).findById(user.getUserId());
    }
    @Test
    public void gettingAllUserFailure() throws Exception {
        when(userTrackRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(null));
        assertThrows(UserNotFound.class,()->userTrackService.getAllUserTrackFromWishList(user.getUserId()));

    }
}
