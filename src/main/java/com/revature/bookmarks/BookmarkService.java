package com.revature.bookmarks;

import com.amazonaws.services.apigateway.model.Op;
import com.revature.exceptions.PostNotFoundException;
import com.revature.posts.Post;
import com.revature.posts.PostRepository;
import com.revature.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookmarkService {

    private PostRepository postRepository;
    private BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkService(PostRepository postRepository, BookmarkRepository bookmarkRepository){
        this.postRepository = postRepository;
        this.bookmarkRepository = bookmarkRepository;
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

    public List<Bookmark> getBookmarkedPostForAuthUser(User user) {
        return bookmarkRepository.getByUser(user);
    }
}
