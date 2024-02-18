package koders.codi.domain.review.dto;

import koders.codi.domain.review.entitiy.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewInfoDto {
    private Long reviewId;
    private String writerName;
    private String writerProfileImageUrl;
    private Double score;
    private String content;

    public static ReviewInfoDto of (final Review review) {
        return new ReviewInfoDto(
                review.getId(),
                review.getUser().getNickname(),
                review.getUser().getProfileImageUrl(),
                review.getScore(),
                review.getContent());
    }
}
