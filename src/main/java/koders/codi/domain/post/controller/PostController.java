package koders.codi.domain.post.controller;

import koders.codi.domain.post.dto.PostReqDto;
import koders.codi.domain.post.dto.PostResDto;
import koders.codi.domain.post.entity.Post;
import koders.codi.domain.post.repository.PostRepository;
import koders.codi.domain.post.service.ImageService;
import koders.codi.domain.post.service.PostService;
import koders.codi.domain.user.repository.UserRepository;
import koders.codi.domain.user.service.UserService;
import koders.codi.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;
    private final ImageService imageService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostMapping("")
    public ResponseEntity createPost(@RequestPart(value = "postReqDto") PostReqDto postReqDto,
                                     @RequestPart(value = "image", required = false) List<MultipartFile> images,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        long userId = userService.getUser(userDetails.getId()).getId();

        if(!userRepository.existsById(userId)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증이 필요합니다.");
        }

        List<String> imageUrls = new ArrayList<>();
        images.forEach(file -> {
            if(!file.isEmpty()){
                String imageUrl = null;
                try{
                    //사진들을 gcs에 업로드하면서 db에 저장될 이미지 파일 url 반환
                    imageUrl = imageService.uploadImage(file);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
                imageUrls.add(imageUrl);

            }
        });

        return postService.createPost(postReqDto, imageUrls, userId);
    }

    //id로 해당 게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @GetMapping("/all")
    public ResponseEntity getAllPosts(){
        return postService.getAllPosts();
    }

    @PutMapping("/{postId}")
    public ResponseEntity modifyPost(@PathVariable Long postId,
                                     @RequestPart(value = "postReqDto") PostReqDto postReqDto,
                                     @RequestPart(value = "image", required = false) List<MultipartFile> images,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        long userId = userService.getUser(userDetails.getId()).getId();
        Post post = postRepository.findById(postId).get();

        if(userId != post.getUser().getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글을 수정할 권한이 없습니다.");
        }

        //기존 게시글에대한 이미지 삭제
        postService.deleteimage(postId);

        List<String> imageUrls = new ArrayList<>();
        images.forEach(file -> {
            if(!file.isEmpty()){
                String imageUrl = null;
                try{
                    //사진들을 gcs에 업로드하면서 db에 저장될 이미지 파일 url 반환
                    imageUrl = imageService.uploadImage(file);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
                imageUrls.add(imageUrl);

            }
        });

        return postService.modifyPost(postId, postReqDto, imageUrls);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deltePost(@PathVariable Long postId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetails){
        long userId = userService.getUser(userDetails.getId()).getId();
        Post post = postRepository.findById(postId).get();

        if(userId != post.getUser().getId()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("게시글을 삭제할 권한이 없습니다.");
        }

        return postService.deletePost(postId);
    }

}
