package koders.codi.domain.comment.controller;

import koders.codi.domain.comment.dto.CommentReqDto;
import koders.codi.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestPart(value = "commentReqDto")CommentReqDto commentReqDto){
        return commentService.createComment(commentReqDto);
    }

    @GetMapping("/get/{commentId}")
    public ResponseEntity getComment(@PathVariable long commentId){
        return commentService.getComment(commentId);
    }

    //해당 게시글에 대한 모든 댓글 조회
    @GetMapping("/get/all/{postId}")
    public ResponseEntity getAllComments(@PathVariable long postId){
        return commentService.getAllComments(postId);
    }

    @PutMapping("/modify/{commentId}")
    public ResponseEntity modifyComment(@PathVariable long commentId,
                                        @RequestPart(value = "commentReqDto") CommentReqDto commentReqDto){
        return commentService.modifyComment(commentId, commentReqDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(@PathVariable long commentId){
        return commentService.deleteComment(commentId);
    }


}
