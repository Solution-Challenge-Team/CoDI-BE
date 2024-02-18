package koders.codi.domain.hospital.entity;

import jakarta.persistence.*;
import koders.codi.domain.hospital.dto.HospitalInfoDto;
import koders.codi.domain.review.entitiy.Review;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Hospital {
    @Id
    @Column(name = "hospital_id")
    private Long apiId;
    private String name;
    private String address;
    Region region;
    private String hospitalType;
    private String phone;
    private String x;
    private String y;
    private Double score;

    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Review> reviews;

    public static Hospital of(final HospitalInfoDto hospitalInfoDto, Region region){
        return new Hospital(
                hospitalInfoDto.getHospitalId(),
                hospitalInfoDto.getName(),
                hospitalInfoDto.getAddress(),
                region,
                hospitalInfoDto.getHospitalType(),
                hospitalInfoDto.getPhone(),
                hospitalInfoDto.getX(),
                hospitalInfoDto.getY(),
                0.0,
                new ArrayList<>()
        );
    }

    public void updateHospitalScore() {
        this.score = Double.parseDouble(String.format("%.2f", reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0)));
    }
}
