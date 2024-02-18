package koders.codi.domain.hospital.entity;

import koders.codi.global.exception.custom.CategoryNotFoundException;
import koders.codi.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Region {

    SEOUL("서울"),
    BUSAN("부산"),
    DAEGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    JEJU("제주"),
    SEJONG("세종"),
    GYEONGGI("경기");

    private final String value;

    public static Region isEqual(String region) {
        return Arrays.stream(Region.values())
                .filter(v -> v.getValue().equals(region))
                .findAny()
                .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.NOT_FOUND_CATEGORY));
    }
}
