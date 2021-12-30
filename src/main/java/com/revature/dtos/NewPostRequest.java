package com.revature.dtos;


import lombok.Data;

@Data
public class NewPostRequest {
    private ContentType contentType;
    private String postText;
    private String contentLink;


}
