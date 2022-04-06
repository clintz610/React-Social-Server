package com.revature.bookmarks;

import com.amazonaws.services.apigateway.model.Op;
import com.revature.bookmarks.dto.BookmarkResponse;
import com.revature.comments.Comment;
import com.revature.comments.dtos.AuthorDto;
import com.revature.comments.dtos.CommentRequest;
import com.revature.exceptions.PostNotFoundException;
import com.revature.posts.Post;
import com.revature.posts.PostRepository;
import com.revature.posts.dtos.PostResponse;
import com.revature.users.User;
import com.revature.users.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookmarkService {

    private PostRepository postRepository;
    private BookmarkRepository bookmarkRepository;
    private ProfileRepository profileRepository;

    @Autowired
    public BookmarkService(PostRepository postRepository, BookmarkRepository bookmarkRepository, ProfileRepository profileRepository){
        this.postRepository = postRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.profileRepository = profileRepository;
    }

    public Integer getNumberOfBookmarks(UUID postId) throws PostNotFoundException{
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()){
            return bookmarkRepository.getBookmarkByPost(post.get()).size();
        }
        throw new PostNotFoundException();
    }

    public void bookmarkPost(UUID postId, User user) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()){

            if(!checkIfAlreadyBookmarked(post.get(), user)){
                Bookmark bookmark = new Bookmark();
                bookmark.setPost(post.get());
                bookmark.setUser(user);
                bookmarkRepository.save(bookmark);
            } else {
                throw new IllegalStateException("Post is already bookmarked");
            }
        } else {
            throw new PostNotFoundException();
        }
    }

    public void removeBookmark(UUID postId, User user) throws PostNotFoundException {

        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()){
            if(checkIfAlreadyBookmarked(post.get(), user)){
                Post query = postRepository.getById(postId);
                Bookmark bookmark = bookmarkRepository.getByPostAndUser(query, user).get(0);
                bookmarkRepository.delete(bookmark);
            } else {
                throw new IllegalStateException("User has not bookmarked this post!");
            }
        } else {
            throw new PostNotFoundException();
        }
    }

    public boolean checkIfAlreadyBookmarked(UUID postId, User user) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()){
            return checkIfAlreadyBookmarked(post.get(), user);
        } else {
          throw new PostNotFoundException();
        }
    }

    public boolean checkIfAlreadyBookmarked(Post post, User user) {
        return !bookmarkRepository.getByPostAndUser(post, user).isEmpty();
    }

    public List<BookmarkResponse> getBookmarkedPostForAuthUser(User user) {
        List<Bookmark> bookmarkList = bookmarkRepository.getByUser(user);
        return getCommentsFromBookmark(bookmarkList);
    }

    private List<BookmarkResponse> getCommentsFromBookmark(List<Bookmark> bookmarks) {
        List<BookmarkResponse> refinedRepo = new LinkedList<>();

        for (int i = 0; i < bookmarks.size(); i++) {
            // Record the relevant data from the posts.
            Bookmark rawBookmark = bookmarks.get(i);
            BookmarkResponse refinedBookmark = new BookmarkResponse(rawBookmark);

            // Get the post's comments
            List<Comment> rawComments = rawBookmark.getPost().getComments();
            List<CommentRequest> refinedComments = new LinkedList<>();
            for (int j = 0; j < rawComments.size(); j++){
                // Record the relevant data for a comment.
                Comment rawComment = rawComments.get(j);
                CommentRequest refinedComment = new CommentRequest();

                // Get the simple values
                refinedComment.setCommentId(rawComment.getId().toString());
                refinedComment.setCommentText(rawComment.getCommentText());
                refinedComment.setDate(rawComment.getDate());

                // Create the author object we need
                AuthorDto refinedAuthor = new AuthorDto(rawComment.getAuthor(), profileRepository);
                refinedComment.setAuthor(refinedAuthor);

                // Add the result to the list
                refinedComments.add(refinedComment);

            }
            refinedBookmark.setComments(refinedComments);

            refinedRepo.add(refinedBookmark);
        }
        return refinedRepo;
    }
}
