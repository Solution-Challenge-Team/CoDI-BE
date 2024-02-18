package koders.codi.domain.review.entitiy;

import jakarta.persistence.*;
import koders.codi.domain.BaseTimeEntity;
import koders.codi.domain.hospital.entity.Hospital;
import koders.codi.domain.review.dto.ReviewReqDto;
import koders.codi.domain.review.dto.ReviewEditDto;
import koders.codi.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    private Double score;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public static Review of(final ReviewReqDto reviewReqDto, final Hospital hospital, final User user) {
        return new Review(
                null,
                reviewReqDto.getScore(),
                reviewReqDto.getContent(),
                hospital,
                user
        );
    }

    public void updateReview(final ReviewEditDto reviewEditDto) {
        if(reviewEditDto.getContent() != null) this.content = reviewEditDto.getContent();
        if(reviewEditDto.getScore() != null) this.score = reviewEditDto.getScore();
    }
}