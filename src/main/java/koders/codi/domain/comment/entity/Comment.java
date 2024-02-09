package koders.codi.domain.comment.entity;

import jakarta.persistence.*;
import koders.codi.domain.post.entity.Post;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

//    public Comment(String content){
//        this.content = content;
//    }
}
