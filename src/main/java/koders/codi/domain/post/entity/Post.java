package koders.codi.domain.post.entity;

import jakarta.persistence.*;
import koders.codi.domain.user.entity.User;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Post(String title, Category category, Disorder disorder, String content, User user){
        this.title = title;
        this.category = category;
        this.disorder = disorder;
        this.content = content;
        this.user = user;
    }
}
