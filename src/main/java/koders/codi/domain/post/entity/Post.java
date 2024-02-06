package koders.codi.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String title;

    @Column
    private Category category;

    @Column
    private Disorder disorder;

    @Column
    private String content;

//    @ManyToOne
//    @JoinColumn(name="user_id", nullable = false)
//    private User user;
    public Post(String title, Category category, Disorder disorder, String content){
        this.title = title;
        this.category = category;
        this.disorder = disorder;
        this.content = content;
//        this.user = new User();
    }
}
