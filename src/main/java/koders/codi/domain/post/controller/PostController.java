package koders.codi.domain.post.controller;

import koders.codi.domain.post.dto.PostReqDto;
import koders.codi.domain.post.dto.PostResDto;
import koders.codi.domain.post.service.ImageService;
import koders.codi.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final ImageService imageService;

    @PostMapping("/create")
    public ResponseEntity createPost(@RequestPart(value = "postReqDto") PostReqDto postReqDto,
                                     @RequestPart(value = "image", required = false) List<MultipartFile> images){

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

        return postService.createPost(postReqDto, imageUrls);
    }

    //id로 해당 게시글 조회
    @GetMapping("/get/{postId}")
    public PostResDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @GetMapping("/get/all")
    public List<PostResDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @PutMapping("/modify/{postId}")
    public ResponseEntity modifyPost(@PathVariable Long postId,
                                     @RequestPart(value = "postReqDto") PostReqDto postReqDto,
                                     @RequestPart(value = "image", required = false) List<MultipartFile> images){

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

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity deltePost(@PathVariable Long postId){
        return postService.deletePost(postId);
    }

}
