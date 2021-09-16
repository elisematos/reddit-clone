package com.redditclone.project.repository;

import com.redditclone.project.model.Comment;
import com.redditclone.project.model.Post;
import com.redditclone.project.model.User;
import com.sun.xml.internal.stream.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
