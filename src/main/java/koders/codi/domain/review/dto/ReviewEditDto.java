package koders.codi.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewEditDto {
    private Double score;
    private String content;
}
