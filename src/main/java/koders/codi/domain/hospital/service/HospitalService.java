package koders.codi.domain.hospital.service;

import koders.codi.domain.hospital.dto.HospitalInfoDto;
import koders.codi.domain.hospital.entity.Hospital;
import koders.codi.domain.hospital.entity.Region;
import koders.codi.domain.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;

    @Value("${kakao.rest-api-key}")
    private String kakaoApiKey;

    public void createHospital(final String x,final String y) {
        HospitalInfoDto hospitalInfoDto = getHospitalFromApi(x,y).get(0);
        //지역 카테고리 지정
        String region = hospitalInfoDto.getAddress().split(" ")[0];
        Hospital hospital = Hospital.of(hospitalInfoDto,Region.isEqual(region));
        hospitalRepository.save(hospital);
        hospital.updateHospitalScore();
    }
    public List<HospitalInfoDto> getHospitalList(String region, String x, String y, String page, String size, String radius){
        //score순으로 저장된 병원 리스트 가져옴
        List<Hospital> hospitalList = hospitalRepository.findAllByOrderByScoreDesc();
        List<HospitalInfoDto> hospitalInfoDtoList1 = hospitalList.stream()
                .filter(hospital -> hospital.getRegion().getValue().equals(region)) //카테고리 제한
                .map(HospitalInfoDto::of)
                .sorted(Comparator.comparingDouble(HospitalInfoDto::getScore).reversed()) //높은 점수 부터 출력
                .toList();
        //api에서 가져온 병원 리스트
        List<HospitalInfoDto> hospitalInfoDtoList2 = getHospitalListFromApi(region, x, y, page,size, radius);
        return Stream.concat(hospitalInfoDtoList1.stream(), hospitalInfoDtoList2.stream())
                .collect(Collectors.toList());
    }
    public List<HospitalInfoDto> getHospitalFromApi(String x, String y) {
        String url = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=HP8";
        if(x!=null && y!=null && !x.isEmpty() && !y.isEmpty()){
            url += "&x="+x+"&y="+y+"&radius=0.5";
        }
        WebClient client = WebClient
                .builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .build();

        //api 호출
        Map<?,?> response = client.get()
                .uri(url)
                .header("Authorization","KakaoAK "+ kakaoApiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        //java 객체로 변환
        List<?> document = Objects.requireNonNull(response).get("documents") != null ? (List<?>) response.get("documents") : null;
        List<HospitalInfoDto> hospitalInfoDtoList = new ArrayList<>();
        if (document != null) {
            for (Object o : document) {
                Map<String, String> map = (Map<String, String>) o;
                if (hospitalRepository.existsById(Long.parseLong(map.get("id")))) continue;   //이미 존재하는 병원이면 넘어감
                hospitalInfoDtoList.add(new HospitalInfoDto(
                        Long.parseLong(map.get("id")),
                        map.get("place_name"),
                        map.get("address_name"),
                        map.get("category_name").split(" ")[4],
                        map.get("phone"),
                        map.get("x"),
                        map.get("y"),
                        -1.0));
            }
        }
        return hospitalInfoDtoList;
    }

    public List<HospitalInfoDto> getHospitalListFromApi(String query, String x, String y, String page, String size, String radius) {
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json?category_group_code=HP8";
        if (x != null && y != null && !x.isEmpty() && !y.isEmpty()) {
            url += "&x=" + x + "&y=" + y;
        }
        if (page != null && !page.isEmpty()) {
            url += "&page=" + page;
        }
        if (size != null && !size.isEmpty()) {
            url += "&size=" + size;
        }

        if (radius != null && !radius.isEmpty()) {
            url += "&radius=" + radius;
        }
        WebClient client = WebClient
                .builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .build();


        //api 호출
        Map<?, ?> response = client.get()
                .uri(url + "&query=" + query)
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        List<?> document = Objects.requireNonNull(response).get("documents") != null ? (List<?>) response.get("documents") : null;
        List<HospitalInfoDto> hospitalInfoDtoList = new ArrayList<>();
        if (document != null) {
            for (Object o : document) {
                Map<String, String> map = (Map<String, String>) o;
                if (hospitalRepository.existsById(Long.parseLong(map.get("id")))) continue;   //이미 존재하는 병원이면 넘어감
                hospitalInfoDtoList.add(new HospitalInfoDto(
                        Long.parseLong(map.get("id")),
                        map.get("place_name"),
                        map.get("address_name"),
                        map.get("category_name").split(" ")[4],
                        map.get("phone"),
                        map.get("x"),
                        map.get("y"),
                        -1.0));
            }
        }
        return hospitalInfoDtoList;
    }
}
