package com.redditclone.project.controller;

import com.redditclone.project.dto.CommentDto;
import com.redditclone.project.exception.PostNotFoundException;
import com.redditclone.project.exception.SpringRedditException;
import com.redditclone.project.service.CommentService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
@Api
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto)
            throws SpringRedditException, PostNotFoundException {
        commentService.save(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId)
            throws PostNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllcommentsForPost(postId));
    }
    
    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(userName));
    }
}
