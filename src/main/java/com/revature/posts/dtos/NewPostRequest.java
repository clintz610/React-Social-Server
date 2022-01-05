package com.revature.posts.dtos;


import com.revature.common.util.ContentType;
import lombok.Data;

@Data
public class NewPostRequest {
    private ContentType contentType;
    private String postText;
    private String contentLink;


}
