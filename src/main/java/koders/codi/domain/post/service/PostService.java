package koders.codi.domain.post.service;

import jakarta.transaction.Transactional;
import koders.codi.domain.post.dto.PostReqDto;
import koders.codi.domain.post.dto.PostResDto;
import koders.codi.domain.post.entity.Post;
import koders.codi.domain.post.entity.PostImage;
import koders.codi.domain.post.repository.PostImageRepository;
import koders.codi.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    //게시글 작성
    public ResponseEntity createPost(PostReqDto postReqDto, List<String> postImages){

        try {
            if (!postReqDto.isValidCategory()) {      //카테고리를 enum값 내에서만 정의할 수 있도록
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 카테고리입니다.");
            }
            if (!postReqDto.isValidDisorder()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 장애유형입니다.");
            }

            //post 생성
            Post post = new Post(postReqDto.getTitle(),
                    postReqDto.getCategory(), postReqDto.getDisorder(), postReqDto.getContent());

            //db에 저장
            postRepository.save(post);

            //post에 대한 사진 저장
            for(String imgUrl : postImages){
                PostImage postImage = PostImage.builder()
                        .imageUrl(imgUrl)
                        .post(post)
                        .build();

                postImageRepository.save(postImage);
            }
            return ResponseEntity.ok("게시글이 작성되었습니다\n"+ "ID: "+ post.getId());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }

    }

    //id로 해당 게시글 조회
    public ResponseEntity getPost(Long postId){
        try {
            Optional<Post> p = postRepository.findById(postId);

            if (p.isPresent()) {
                Post post = p.get();
                //게시글 id에 대한 사진 모두 반환
                List<PostImage> postImages = postImageRepository.findByPostId(postId);

                List<String> imgUrls = postImages.stream()
                        .map(PostImage::getImageUrl)   //postimage객체에서 image(url) 추출
                        .collect(Collectors.toList());  //url 경로를 리스트로 반환

                PostResDto postResDto = new PostResDto(post.getId(), post.getTitle(), post.getCategory(), post.getDisorder(), post.getContent(), imgUrls);
                return ResponseEntity.ok(postResDto);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 id 게시글이 없습니다");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    //모든 게시글 조회
    public ResponseEntity getAllPosts(){
        //모든 post 조회
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new RuntimeException("존재하는 게시글이 없습니다");
        }

        List<PostResDto> postResDtos = new ArrayList<>();   //nullpointerexception 방지위해 객체 생성해둠

        for(Post p : posts){
            List<String> imgUrls = new ArrayList<>();
            //각 post에 해당하는 postimage 조회
            List<PostImage> postImages = postImageRepository.findByPostId(p.getId());

            //각 postimage의 url 추출
            for(PostImage image : postImages){
                imgUrls.add(image.getImageUrl());
            }
            postResDtos.add(new PostResDto(p.getId(), p.getTitle(), p.getCategory(), p.getDisorder(), p.getContent(), imgUrls));
        }

        return ResponseEntity.ok(postResDtos);
    }

    //게시글 수정
    public ResponseEntity modifyPost(Long postId, PostReqDto postReqDto, List<String> postImages) {
        try {
            if (postRepository.existsById(postId)) {
                Post post = postRepository.findById(postId).get();
                post.setTitle(postReqDto.getTitle());
                post.setCategory(postReqDto.getCategory());
                post.setDisorder(postReqDto.getDisorder());
                post.setContent(postReqDto.getContent());

                postRepository.save(post);  //수정된 게시글 db에 저장

                //기존 이미지는 컨트롤러에서 삭제 후 여기서 새로운 이미지 저장
                for(String imgUrl: postImages){
                    PostImage postImage = PostImage.builder()
                            .post(post)
                            .imageUrl(imgUrl)
                            .build();
                    postImageRepository.save(postImage);
                }

                return ResponseEntity.ok("게시글이 수정되었습니다");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시글을 찾을 수 없습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }

    }

    public void deleteimage(Long postId){
        postImageRepository.deleteByPostId(postId);
    }

    @Transactional  //게시글 삭제
    public ResponseEntity deletePost(Long postId){
        try {
            if (postRepository.existsById(postId)) {
                Post post = postRepository.findById(postId).get();
                //게시글 이미지 삭제
                postImageRepository.deleteByPostId(post.getId());
                //게시글 삭제
                postRepository.delete(post);


                return ResponseEntity.ok("게시글이 삭제되었습니다");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 게시글을 찾을 수 없습니다");
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

}
