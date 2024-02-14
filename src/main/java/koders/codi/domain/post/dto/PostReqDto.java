package koders.codi.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import koders.codi.domain.post.entity.Category;
import koders.codi.domain.post.entity.Disorder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDto {
    private String title;
    private Category category;
    private Disorder disorder;
    private String content;
//    private Long userId;

    // Category내에 정의된 값만 가질수 있도록
    @JsonIgnore
    public boolean isValidCategory() {
        for (Category validCategory : Category.values()) {
            if (validCategory.equals(category)) {
                return true;
            }
        }
        return false;
    }

    @JsonIgnore
    public boolean isValidDisorder() {
        for (Disorder validDisorder : Disorder.values()) {
            if (validDisorder.equals(disorder)) {
                return true;
            }
        }
        return false;
    }

}
