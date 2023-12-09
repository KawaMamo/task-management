package org.example.controllers;

import org.example.contract.requests.CreateCommentRequest;
import org.example.domain.Comment;
import org.example.services.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    Comment getComment(@RequestBody CreateCommentRequest request){
        return commentService.createCommit(request);
    }
}
