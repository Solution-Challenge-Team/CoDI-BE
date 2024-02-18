package koders.codi.domain.hospital.dto;

import koders.codi.domain.hospital.entity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HospitalInfoDto {
    private Long hospitalId;
    private String name;
    private String address;
    private String hospitalType;
    private String phone;
    private String x;
    private String y;
    private Double score;

    public static HospitalInfoDto of(final Hospital hospital) {
        return new HospitalInfoDto(
                hospital.getApiId(),
                hospital.getName(),
                hospital.getAddress(),
                hospital.getHospitalType(),
                hospital.getPhone(),
                hospital.getX(),
                hospital.getY(),
                hospital.getScore()
        );
    }
}
