package koders.codi.domain.post.repository;

import jakarta.transaction.Transactional;
import koders.codi.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    List<PostImage> findByPostId(Long postId);
    @Transactional
    void deleteByPostId(Long postId);
}
