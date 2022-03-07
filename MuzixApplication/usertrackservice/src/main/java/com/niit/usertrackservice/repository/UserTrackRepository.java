package com.niit.usertrackservice.repository;

import com.niit.usertrackservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrackRepository extends MongoRepository<User,String> {
}
