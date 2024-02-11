package koders.codi.domain.comment.controller;

import koders.codi.domain.comment.dto.CommentReqDto;
import koders.codi.domain.comment.entity.Comment;
import koders.codi.domain.comment.repository.CommentRepository;
import koders.codi.domain.comment.service.CommentService;
import koders.codi.domain.user.repository.UserRepository;
import koders.codi.domain.user.service.UserService;
import koders.codi.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestPart(value = "commentReqDto")CommentReqDto commentReqDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        long userId = userService.getUser(userDetails.getId()).getId();

        if(!userRepository.existsById(userId)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증이 필요합니다.");
        }

        return commentService.createComment(commentReqDto, userId);
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
                                        @RequestPart(value = "commentReqDto") CommentReqDto commentReqDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        long userId = userService.getUser(userDetails.getId()).getId();
        Comment comment = commentRepository.findById(commentId).get();

        if(userId != comment.getUser().getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글을 수정할 권한이 없습니다.");
        }

        return commentService.modifyComment(commentId, commentReqDto);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(@PathVariable long commentId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        long userId = userService.getUser(userDetails.getId()).getId();
        Comment comment = commentRepository.findById(commentId).get();

        if(userId != comment.getUser().getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글을 삭제할 권한이 없습니다.");
        }

        return commentService.deleteComment(commentId);
    }


}
