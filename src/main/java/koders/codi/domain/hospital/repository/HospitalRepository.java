package koders.codi.domain.hospital.repository;

import koders.codi.domain.hospital.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long>  {
    Optional<Hospital> findByXAndY(String x, String y);
    List<Hospital> findAllByOrderByScoreDesc();
    boolean existsByXAndY(String x, String y);
}
