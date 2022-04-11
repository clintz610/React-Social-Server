package com.revature.bookmarks;

import com.revature.ReverbApplication;
import com.revature.exceptions.PostNotFoundException;
import com.revature.posts.Post;
import com.revature.posts.PostRepository;
import com.revature.posts.postmeta.PostMeta;
import com.revature.users.User;
import com.revature.users.profiles.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest (classes = ReverbApplication.class)
public class TestBookmarkService {

    private BookmarkService sut;
    private BookmarkRepository mockBookmarkRepository = Mockito.mock(BookmarkRepository.class);
    private PostRepository mockPostRepository = Mockito.mock(PostRepository.class);
    private ProfileRepository mockProfileRepository = Mockito.mock(ProfileRepository.class);
    public UUID post_id = UUID.randomUUID();
    public PostMeta testPostMeta = new PostMeta();
    public List testComments = new ArrayList ();
    public Post bookmarkedPost = new Post(post_id,"Valid post", "hello", testPostMeta, testComments);
    // private UserRepos mockUserRepos = mock(UserRepos.class);
    @BeforeEach
    public void setup() {
        sut = new BookmarkService(mockPostRepository, mockBookmarkRepository, mockProfileRepository);
    }


    @Test
    public void getNumberofBookmarksPositive() throws PostNotFoundException {
        //tests getNumberofBookmarks for no bookmarks on a new post
        Post post = new Post();

        Mockito.when(mockPostRepository.findById(bookmarkedPost.getId().thenReturn(Optional.of(post));
        Mockito.when(mockBookmarkRepository.getBookmarkByPost(post)).thenReturn(new ArrayList<Bookmark>());

        BookmarkService bookmarkService = new BookmarkService(mockPostRepository, mockBookmarkRepository, mockProfileRepository);

        assertThat(bookmarkService.getNumberOfBookmarks(UUID.randomUUID())).isEqualTo(0);
    }

    @Test
    public void Bookmarked_Post_Is_Bookmarked() throws PostNotFoundException {
        Post post = new Post();
        User user = new User();
        Bookmark bookmark = new Bookmark();
        List<Bookmark> array = new ArrayList<Bookmark>();
        Mockito.when(mockPostRepository.findById(UUID.randomUUID())).thenReturn(Optional.of(post));
        Mockito.when(mockBookmarkRepository.getByPostAndUser(post, user)).thenReturn(array);
        BookmarkService bookmarkService = new BookmarkService(mockPostRepository, mockBookmarkRepository, mockProfileRepository);
        sut.bookmarkPost(UUID.randomUUID(), user);
        array.add(bookmark);
        assertTrue(bookmarkService.checkIfAlreadyBookmarked(post, user));
    }
}



