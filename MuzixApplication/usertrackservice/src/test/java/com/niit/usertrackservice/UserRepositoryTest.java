package com.niit.usertrackservice;

import com.niit.usertrackservice.model.Artist;
import com.niit.usertrackservice.model.Image;
import com.niit.usertrackservice.model.Tracks;
import com.niit.usertrackservice.model.User;
import com.niit.usertrackservice.repository.UserTrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    UserTrackRepository userTrackRepository;
    private User user;
    private Tracks tracks;
    private Artist artist;
    private Image image;
    private List<Tracks> tracksList;
    @BeforeEach
    public void setup(){
        user=new User("110","harish","123456",tracksList);
        tracks=new Tracks(1,"buleyana","5:01",artist);
        artist=new Artist("Arijit singh","male",image);
        image=new Image(12,"buleyana");
        tracksList= Arrays.asList(tracks);
    }
    @AfterEach
    public void dataReset(){
        user=null;
        tracks=null;
        artist=null;
        image=null;
        userTrackRepository.deleteAll();
    }
    @Test
    public void givenProductToSaveProduct(){
        userTrackRepository.insert(user);
        User user1= userTrackRepository.findById(user.getUserId()).get();
        assertNotNull(user1);
        //harish
        assertEquals("harish",user1.getUserName());
    }
    @Test
    public void givenProductToSaveProductNegativeCase(){
        userTrackRepository.insert(user);
        User user1= userTrackRepository.findById(user.getUserId()).get();
        assertNotEquals("raj",user.getUserName());
    }
    @Test
    public void gettingAllProduct(){
        userTrackRepository.insert(user);
        User user1=userTrackRepository.findById(user.getUserId()).get();
        assertEquals(user1,user1);
    }
    @Test
    public void gettingAllUserNegativeCase(){
        userTrackRepository.insert(user);
        User user1=userTrackRepository.findById(user.getUserId()).get();
        assertNotEquals("1",user1.getUserId());
    }
    @Test
    public void updatingTrack(){
      user.setUserName("ravi");
        userTrackRepository.insert(user);
        User user1= userTrackRepository.findById(user.getUserId()).get();
        assertEquals(user.getUserName(),user1.getUserName());
        assertEquals(user,user1);
    }
    @Test
    public void updateTrackNegativeCase(){
         user.setUserName("ravi");
        userTrackRepository.insert(user);
        User user1= userTrackRepository.findById(user.getUserId()).get();
         assertNotEquals("harish",user1.getUserName());
        assertNotEquals("1",user1.getUserId());

    }

}
