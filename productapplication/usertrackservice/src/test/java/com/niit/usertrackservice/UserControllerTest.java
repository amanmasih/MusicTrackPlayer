package com.niit.usertrackservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.usertrackservice.controller.UserTrackController;
import com.niit.usertrackservice.exception.UserAlreadyExists;
import com.niit.usertrackservice.exception.UserNotFound;
import com.niit.usertrackservice.model.Artist;
import com.niit.usertrackservice.model.Image;
import com.niit.usertrackservice.model.Tracks;
import com.niit.usertrackservice.model.User;
import com.niit.usertrackservice.service.UserTrackService;
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


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserTrackService userTrackService;

    @InjectMocks
    private UserTrackController userTrackController;

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
        mockMvc=MockMvcBuilders.standaloneSetup(userTrackController).build();

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
    public void givenUserToRegisterUser() throws Exception {
        when(userTrackService.registerUser(any())).thenReturn(user);
        mockMvc.perform(post("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(user))
        ).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(userTrackService,times(1)).registerUser(any());
    }
    @Test
    public void givenUserToRegisterUserNegativeCase() throws Exception {
        when(userTrackService.registerUser(any())).thenThrow(UserAlreadyExists.class);
        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(userTrackService,times(1)).registerUser(any());
    }
    @Test
    public void givenTrackToSaveTrackToUser() throws Exception {
        when(userTrackService.saveUserTrackToWishList(anyString(),any())).thenReturn(user);
        mockMvc.perform(post("/api/v1/saveUserTrack/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonToString(user))
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userTrackService,times(1)).saveUserTrackToWishList(anyString(),any());
    }
    @Test
    public void givenTrackToSaveTrackToUserNegativeCase()throws Exception
    {
        when(userTrackService.saveUserTrackToWishList(anyString(),any())).thenThrow(UserNotFound.class);
        mockMvc.perform(post("/api/v1/saveUserTrack/102")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(userTrackService,times(1)).saveUserTrackToWishList(anyString(),any());
    }
    @Test
    public void gettingAllUser() throws Exception {
        when(userTrackService.getAllUserTrackFromWishList(anyString())).thenReturn(user);
        mockMvc.perform(get("/api/v1/getAllTracks/101")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        verify(userTrackService,times(1)).getAllUserTrackFromWishList(anyString());
    }
    @Test
    public void gettingAllUserFailure() throws Exception {
        when(userTrackService.getAllUserTrackFromWishList(anyString())).thenThrow(UserNotFound.class);
        mockMvc.perform(get("/api/v1/getAllTracks/102")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
        verify(userTrackService,times(1)).getAllUserTrackFromWishList(anyString());
    }

    private static String jsonToString(final Object obj) throws JsonProcessingException
    {
        String result=null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.writeValueAsString(obj);
        }
        catch (JsonProcessingException e){
            result="error while conversion";
        }
        return result;
    }


}
