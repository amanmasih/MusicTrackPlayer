package com.niit.usertrackservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tracks {
    private int trackId;
    private  String trackName;
    private String trackDuration;
    private Artist artist;
}
