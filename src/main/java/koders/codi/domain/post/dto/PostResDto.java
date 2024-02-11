package koders.codi.domain.post.dto;

import koders.codi.domain.post.entity.Category;
import koders.codi.domain.post.entity.Disorder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResDto {
    private Long postId;
    private Long userId;
    private String title;
    private Category category;
    private Disorder disorder;
    private String content;
    List<String> postImages;
}
