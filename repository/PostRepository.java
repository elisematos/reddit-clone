package com.redditclone.project.repository;

import com.redditclone.project.model.Post;
import com.redditclone.project.model.Subreddit;
import com.redditclone.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
