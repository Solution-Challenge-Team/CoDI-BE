package koders.codi.domain.hospital.controller;

import koders.codi.domain.hospital.dto.HospitalInfoDto;
import koders.codi.domain.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("/api/hospitals")
    public ResponseEntity<List<HospitalInfoDto>> getHospitalList(@RequestParam String region,
                                                                 @RequestParam String page,
                                                                 @RequestParam String size) {
        return ResponseEntity.ok(hospitalService.getHospitalList(region,null, null, page, size, null));
    }
}
