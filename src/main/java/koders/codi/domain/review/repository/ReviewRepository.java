package koders.codi.domain.review.repository;

import koders.codi.domain.hospital.entity.Hospital;
import koders.codi.domain.review.entitiy.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
