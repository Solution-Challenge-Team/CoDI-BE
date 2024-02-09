package koders.codi.domain.comment.service;

import koders.codi.domain.comment.dto.CommentReqDto;
import koders.codi.domain.comment.dto.CommentResDto;
import koders.codi.domain.comment.entity.Comment;
import koders.codi.domain.comment.repository.CommentRepository;
import koders.codi.domain.post.entity.Post;
import koders.codi.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public ResponseEntity createComment(CommentReqDto commentReqDto){
        try {
            if(!postRepository.existsById(commentReqDto.getPostId())){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("게시글이 존재하지않아 댓글을 작성할 수 없습니다");
            }else {
                Post post = postRepository.findById(commentReqDto.getPostId()).get();

                Comment comment = Comment.builder()
                        .content(commentReqDto.getContent())
                        .post(post)
                        .build();

                commentRepository.save(comment);

                return ResponseEntity.ok("댓글이 작성되었습니다\n" + "ID: " + comment.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    //댓글 id로 해당 댓글 조회
    public ResponseEntity getComment(Long commentId){
        try {
            Optional<Comment> c = commentRepository.findById(commentId);

            if (c.isPresent()) {
                Comment comment = c.get();

                return ResponseEntity.ok(new CommentResDto(comment.getId(), comment.getPost().getId(), comment.getContent()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 id 댓글이 없습니다");
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    //게시글 id로 해당 게시글의 모든 댓글 조회
    public ResponseEntity getAllComments(Long postId){
        try {
            List<Comment> comments = commentRepository.findByPostId(postId);

            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시글에 댓글이 없습니다");
            } else {
                List<CommentResDto> dtos = new ArrayList<>();

                for (Comment c : comments) {
                    dtos.add(new CommentResDto(c.getId(), c.getPost().getId(), c.getContent()));
                }

                return ResponseEntity.ok(dtos);
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    //댓글 수정
    public ResponseEntity modifyComment(long commentId, CommentReqDto commentReqDto){
        try {
            if (commentRepository.existsById(commentId)) {
                Comment comment = commentRepository.findById(commentId).get();
                comment.setContent(commentReqDto.getContent());     //내용만 수정 , 게시글 id는 수정 불가
                //게시글 id 바뀌었을때 예외처리 필요
                if(comment.getPost().getId() != commentReqDto.getPostId())
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글 id가 달라 수정할 수 없습니다");

                commentRepository.save(comment);

                return ResponseEntity.ok("댓글이 수정되었습니다");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 댓글을 찾을 수 없습니다");
            }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    public ResponseEntity deleteComment(Long commentId){
        try {
            if (commentRepository.existsById(commentId)) {
                Comment comment = commentRepository.findById(commentId).get();
                commentRepository.delete(comment);

                return ResponseEntity.ok("댓글이 삭제되었습니다");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 댓글을 찾을 수 없습니다");
            }
        } catch (Exception e){      //예외 처리 위해 예외 객체 생성
            e.printStackTrace();    //예외 발생시 콘솔에 예외 정보 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

}
