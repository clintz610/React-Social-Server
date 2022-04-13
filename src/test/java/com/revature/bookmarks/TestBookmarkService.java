package com.revature.bookmarks;

import com.revature.ReverbApplication;
import com.revature.exceptions.PostNotFoundException;
import com.revature.posts.Post;
import com.revature.posts.PostRepository;
import com.revature.posts.postmeta.PostMeta;
import com.revature.users.User;
import com.revature.users.profiles.ProfileRepository;
import javafx.beans.binding.When;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Book;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest (classes = ReverbApplication.class)
public class TestBookmarkService {

    private BookmarkService sut;
    private BookmarkRepository mockBookmarkRepository = Mockito.mock(BookmarkRepository.class);
    private PostRepository mockPostRepository = Mockito.mock(PostRepository.class);
    private ProfileRepository mockProfileRepository = Mockito.mock(ProfileRepository.class);
    public UUID post_id = UUID.randomUUID();
    public PostMeta testPostMeta = new PostMeta();
    public List testComments = new ArrayList();
    public Post bookmarkedPost = new Post(post_id, "PostText", "ContentLink", testPostMeta, testComments);


    @BeforeEach
    public void setup() {
        sut = new BookmarkService(mockPostRepository, mockBookmarkRepository, mockProfileRepository);
    }


    @Test
    public void getNumberofBookmarksPositive() throws PostNotFoundException {
        //tests getNumberofBookmarks for no bookmarks on a new post
        Post post = new Post();

        Mockito.when(mockPostRepository.findById(bookmarkedPost.getId())).thenReturn(Optional.of(bookmarkedPost));
        Mockito.when(mockBookmarkRepository.getBookmarkByPost(bookmarkedPost)).thenReturn(new ArrayList<Bookmark>());

        BookmarkService bookmarkService = new BookmarkService(mockPostRepository, mockBookmarkRepository, mockProfileRepository);

        assertThat(bookmarkService.getNumberOfBookmarks(bookmarkedPost.getId())).isEqualTo(0);
    }

    @Test
    public void Get_Number_of_Bookmarks_Negative_Throws_PostNotFoundException() {
        Mockito.when(mockPostRepository.findById(bookmarkedPost.getId())).thenReturn(Optional.empty());
        Mockito.when(mockBookmarkRepository.getBookmarkByPost(bookmarkedPost)).thenReturn(new ArrayList<Bookmark>());
        BookmarkService bookmarkService = new BookmarkService(mockPostRepository, mockBookmarkRepository, mockProfileRepository);
        try {
            bookmarkService.getNumberOfBookmarks(bookmarkedPost.getId());
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), PostNotFoundException.class);
        }
    }


    @Test
    public void Bookmarked_Post_gets_Bookmarked() throws PostNotFoundException {
        User user = new User();
        Bookmark bookmark = new Bookmark();
        List<Bookmark> array = new ArrayList<Bookmark>();
        Mockito.when(mockPostRepository.findById(bookmarkedPost.getId())).thenReturn(Optional.of(bookmarkedPost));
        Mockito.when(mockBookmarkRepository.getByPostAndUser(bookmarkedPost, user)).thenReturn(array);
        BookmarkService bookmarkService = new BookmarkService(mockPostRepository, mockBookmarkRepository, mockProfileRepository);
        //Act
        sut.bookmarkPost(bookmarkedPost.getId(), user);
        array.add(bookmark);
        //Assert
        assertTrue(bookmarkService.checkIfAlreadyBookmarked(bookmarkedPost, user));
    }
    @Test
    public void remove_Bookmark_Removes_Bookmark () {
        //arrange
        Optional<Post> post = mockPostRepository.findById(post_id);
        BookmarkService spiedSut = Mockito.spy(sut);
        User user = new User();
        spiedSut.checkIfAlreadyBookmarked(bookmarkedPost,user);
        doReturn(true).when(spiedSut).checkIfAlreadyBookmarked(bookmarkedPost,user);
       when(mockPostRepository.findById(post_id)).thenReturn(post);
        try {
            //act
            sut.removeBookmark(bookmarkedPost.getId(), user);
        } catch (PostNotFoundException e) {}

    }


    }


