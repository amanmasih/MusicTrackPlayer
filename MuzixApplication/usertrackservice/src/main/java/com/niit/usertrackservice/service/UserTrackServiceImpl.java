package com.niit.usertrackservice.service;

import com.niit.usertrackservice.exception.UserAlreadyExists;
import com.niit.usertrackservice.exception.UserNotFound;
import com.niit.usertrackservice.model.Tracks;
import com.niit.usertrackservice.model.User;
import com.niit.usertrackservice.repository.UserTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
public class UserTrackServiceImpl implements UserTrackService{
    UserTrackRepository userTrackRepository;
@Autowired
    public UserTrackServiceImpl(UserTrackRepository userTrackRepository) {
        this.userTrackRepository = userTrackRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExists {
        if (userTrackRepository.findById(user.getUserId()).isPresent()){
            throw new UserAlreadyExists();
        }
        return userTrackRepository.save(user);
    }

    @Override
    public User saveUserTrackToWishList(String userId, Tracks tracks) throws UserNotFound {
        if(userTrackRepository.findById(userId).isEmpty()){
            throw new UserNotFound();
        }
       User user=userTrackRepository.findById(userId).get();
        if(user.getTracksList()==null){
            user.setTracksList(Arrays.asList(tracks));
        }
        else{
            List<Tracks> tracksList =user.getTracksList();
            tracksList.add(tracks);
            user.setTracksList(tracksList);
        }
        return userTrackRepository.save(user);
    }

    @Override
    public User deleteUserTrackFromWishList(String userId, int trackId) throws Exception {
        if(userTrackRepository.findById(userId).isEmpty()) {
            throw new UserNotFound();
        }
        User user=userTrackRepository.findById(userId).get();
        List<Tracks> tracksList=user.getTracksList();
        if(tracksList.removeIf(p->p.getTrackId()==trackId)){
            System.out.println(tracksList);
            user.setTracksList(tracksList);
        }
        else {
            throw new Exception();
        }
        return userTrackRepository.save(user);
    }


    @Override
    public User getAllUserTrackFromWishList(String userId) throws UserNotFound {
    if (userTrackRepository.findById(userId).isEmpty()){
        throw new UserNotFound();
    }
        return userTrackRepository.findById(userId).get();
    }
    @Override
    public User updateUserTrackToWishList(String userId, int trackId,String trackName) throws Exception {
        if(userTrackRepository.findById(userId).isEmpty()){
            throw new UserNotFound();
        }
        User user=userTrackRepository.findById(userId).get();
        if(user.getTracksList()==null){
            throw new Exception();
        }
        else{
            List<Tracks> tracksList =user.getTracksList();
           for(Tracks track:tracksList){
               if(track.getTrackId()==trackId){
                   track.setTrackName(trackName);
                   System.out.println(tracksList);
               }
           }
        }
        return userTrackRepository.save(user);
    }
}
