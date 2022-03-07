package com.niit.usertrackservice.service;

import com.niit.usertrackservice.exception.UserAlreadyExists;
import com.niit.usertrackservice.exception.UserNotFound;
import com.niit.usertrackservice.model.Tracks;
import com.niit.usertrackservice.model.User;

public interface UserTrackService {
    User registerUser(User user) throws UserAlreadyExists;
    User saveUserTrackToWishList(String userId, Tracks tracks) throws UserNotFound;
    User deleteUserTrackFromWishList(String userId,int trackId) throws Exception;
    User getAllUserTrackFromWishList(String userId) throws UserNotFound;
    User updateUserTrackToWishList(String userId,int trackId,String trackName) throws Exception;
}
