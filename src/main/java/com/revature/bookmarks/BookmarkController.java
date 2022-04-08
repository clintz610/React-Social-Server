package com.revature.bookmarks;

import com.revature.bookmarks.dto.BookmarkResponse;
import com.revature.exceptions.PostNotFoundException;
import com.revature.posts.Post;
import com.revature.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path= "api/bookmark")
public class BookmarkController {

    private BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService){
        this.bookmarkService = bookmarkService;
    }


    @GetMapping(path = "/get-number-of-bookmarks/{postId}")
    public ResponseEntity<Integer> getNumberOfBookmarks(@PathVariable String postId){
        try{
            Integer numBookmarks = bookmarkService.getNumberOfBookmarks(UUID.fromString(postId));
        } catch (PostNotFoundException e) {
            e.printStackTrace();
        }

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping(path = "/bookmark-post/{postId}")
    public void bookmarkPost(@PathVariable String postId, @AuthenticationPrincipal User user){

        try {
            bookmarkService.bookmarkPost(UUID.fromString(postId), user);
            ResponseEntity.ok();
        } catch (PostNotFoundException e) {
            e.printStackTrace();
            ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(path= "/remove-bookmark/{postId}")
    public void removeBookmark(@PathVariable String postId, @AuthenticationPrincipal User user){
        try{
            bookmarkService.removeBookmark(UUID.fromString(postId), user);
            ResponseEntity.ok();
        } catch (Exception e){
            ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path="/check-if-bookmarked/{postId}")
    public ResponseEntity<Boolean> checkIfBookmarked(@PathVariable String postId, @AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok(bookmarkService.checkIfAlreadyBookmarked(UUID.fromString(postId), user));

        } catch (PostNotFoundException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping(path="/get-all-bookmarks")
    public ResponseEntity<List<BookmarkResponse>> getAllBookmarksForUser(@AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok(bookmarkService.getBookmarkedPostForAuthUser(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();
    }
}
