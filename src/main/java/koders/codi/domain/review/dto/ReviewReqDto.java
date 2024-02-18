package koders.codi.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewReqDto {
    @NotBlank
    private String x;
    @NotBlank
    private String y;
    @Size(min = 0, max = 5)
    private Double score;
    private String content;
}
