package com.revature.users.profiles;

import com.revature.exceptions.ProfileNotFoundException;
import com.revature.users.User;
import com.revature.users.dtos.PicUrlDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBucketControllerIntegration {

    private AmazonClientService amazonClient;
    private ProfileService profileService;
    private BucketController bucket;

    @BeforeEach
    public void setup() {
        //mocks the repositories for each test
        amazonClient = Mockito.mock(AmazonClientService.class);
        profileService = Mockito.mock(ProfileService.class);
        bucket = new BucketController(amazonClient, profileService);
    }

    @After
    public void cleanup() {
        bucket = null;
    }

    @Test
    public void testUploadFile() throws ProfileNotFoundException {
        User u = new User();
        PicUrlDto expect = new PicUrlDto(UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"), "Http://Hello.com");
        Mockito.when(amazonClient.uploadFile(null)).thenReturn("Http://Hello.com");
        Mockito.when(profileService.updatePicUrl("header", null, UUID.fromString("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5"), u)).thenReturn(expect);
        boolean test1 = bucket.uploadFile(null, "header", "d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5", u) == expect;
    }

    @Test
    public void testDeleteFile() {
        Mockito.when(amazonClient.deleteFileFromS3Bucket(null)).thenReturn("OK");
        boolean test1 = bucket.deleteFile(null) == "OK";
        Assert.assertTrue(test1);
    }

    @Test
    public void testLoginUser(){
        User u=new User("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","dev4@dev.com", null,null, null);
        assertThat(bucket.loginUser(u).getStatusCode().compareTo(HttpStatus.OK));
    }





}