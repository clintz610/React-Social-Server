package com.revature.users.profiles;

import com.revature.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/storage")
public class BucketController {

    private AmazonClientService amazonClient;

    @Autowired
    BucketController(AmazonClientService amazonClient) {
        this.amazonClient = amazonClient;
    }

    @PostMapping(path = "/uploadfile")
    public String uploadFile(@ModelAttribute Profile profile,  @RequestPart(value = "file") MultipartFile file, @AuthenticationPrincipal User user) {

        return this.amazonClient.uploadFile(file);
    }

//    @PostMapping(path = "/uploadfile")
//    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "file2") MultipartFile file2) {
//
//        String fileName= this.amazonClient.uploadFile(file);
//        String fileName2= this.amazonClient.uploadFile(file2);
//        return fileName+"----"+fileName2;
//    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @PostMapping(path = "/test")
    public ResponseEntity<String> loginUser(@AuthenticationPrincipal User user) {
        System.out.println("Hitting Testing Endpoint");
        return ResponseEntity.ok("Successfully added user with email " + user.getEmail());
    }

}