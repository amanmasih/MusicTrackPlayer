package com.niit.usertrackservice.controller;

import com.niit.usertrackservice.exception.UserAlreadyExists;
import com.niit.usertrackservice.exception.UserNotFound;
import com.niit.usertrackservice.model.Tracks;
import com.niit.usertrackservice.model.User;
import com.niit.usertrackservice.service.UserTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UserTrackController {
    UserTrackService userTrackService;
    @Autowired
    public UserTrackController(UserTrackService userTrackService) {
        this.userTrackService = userTrackService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserAlreadyExists {
        try {
            return new ResponseEntity<>(userTrackService.registerUser(user), HttpStatus.CREATED);
        }
        catch (UserAlreadyExists e) {
            throw new UserAlreadyExists();
        }
        catch (Exception e){
           return new ResponseEntity<>("try after sometime",HttpStatus.CONFLICT);
        }
    }
    @PostMapping("userdata/saveUserTrack/{userId}")
    public ResponseEntity<?> saveUserTrackToWishList(@RequestBody Tracks tracks, @PathVariable String userId) throws UserNotFound {
        try {
            return new ResponseEntity<>(userTrackService.saveUserTrackToWishList(userId,tracks),HttpStatus.OK);
        }
        catch (UserNotFound e){
            throw new UserNotFound();
        }
        catch (Exception ex){
            return new ResponseEntity<>("try after sometime",HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("userdata/getAllTracks/{userId}")
    public ResponseEntity<?> getAllUserTrackFromWishList(@PathVariable String userId) throws UserNotFound {
        try {
            return new ResponseEntity<>(userTrackService.getAllUserTrackFromWishList(userId),HttpStatus.OK);
        } catch (UserNotFound e) {
           throw new UserNotFound();
        }
        catch (Exception e){
            return new ResponseEntity<>("try after sometime",HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("userdata/deleteTrack/{userId}/{trackId}")
    public ResponseEntity<?> deleteUserTrackFromWishList(@PathVariable String userId,@PathVariable int trackId) throws UserNotFound {
        try {
            return new ResponseEntity<>(userTrackService.deleteUserTrackFromWishList(userId,trackId),HttpStatus.OK);
        } catch (UserNotFound e) {
            throw new UserNotFound();
        }
        catch (Exception e){
            return new ResponseEntity<>("try after some time",HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("userdata/update/{userId}/{trackId}/{trackName}")
    public ResponseEntity<?> updateUserTrackFromWishList(@PathVariable String userId,@PathVariable int trackId,@PathVariable String trackName) throws UserNotFound {
        try {
            return new ResponseEntity<>(userTrackService.updateUserTrackToWishList(userId,trackId,trackName),HttpStatus.OK);
        } catch (UserNotFound e) {
            throw new UserNotFound();
        }
        catch (Exception e){
            return new ResponseEntity<>("try after some time",HttpStatus.NOT_FOUND);
        }
    }
}
