package com.niit.usertrackservice.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Artist {
    private String artistName;
    private String artistGender;
    private Image image;
}
