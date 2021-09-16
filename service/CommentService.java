package com.redditclone.project.service;

import com.redditclone.project.dto.CommentDto;
import com.redditclone.project.exception.PostNotFoundException;
import com.redditclone.project.exception.SpringRedditException;
import com.redditclone.project.mapper.CommentMapper;
import com.redditclone.project.model.Comment;
import com.redditclone.project.model.NotificationEmail;
import com.redditclone.project.model.Post;
import com.redditclone.project.model.User;
import com.redditclone.project.repository.CommentRepository;
import com.redditclone.project.repository.PostRepository;
import com.redditclone.project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentDto commentDto) throws PostNotFoundException, SpringRedditException {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(
                post
                        .getUser()
                        .getUsername() + " posted a comment on your post." + post.getUrl());
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) throws SpringRedditException {
        mailService.sendmail(new NotificationEmail(
                (user.getUsername() + "commented on your post"), user.getEmail(), message)
        );
    }

    public List<CommentDto> getAllcommentsForPost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post).stream().map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
