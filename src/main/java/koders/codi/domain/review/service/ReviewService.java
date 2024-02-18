package koders.codi.domain.review.service;

import koders.codi.domain.hospital.service.HospitalService;
import koders.codi.domain.review.dto.ReviewInfoDto;
import koders.codi.domain.review.dto.ReviewReqDto;
import koders.codi.domain.review.dto.ReviewEditDto;
import koders.codi.domain.hospital.entity.Hospital;
import koders.codi.domain.review.entitiy.Review;
import koders.codi.domain.hospital.exception.HospitalNotFoundException;
import koders.codi.domain.review.exception.ReviewNotFoundException;
import koders.codi.domain.hospital.repository.HospitalRepository;
import koders.codi.domain.review.repository.ReviewRepository;
import koders.codi.domain.user.entity.User;
import koders.codi.domain.user.exception.UserNotFoundException;
import koders.codi.domain.user.repository.UserRepository;
import koders.codi.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final HospitalRepository hospitalRepository;
    private final HospitalService hospitalService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ReviewInfoDto> getReviewList(final Long hospitalId) {
        final Hospital hospital =  hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotFoundException(ErrorCode.NOT_FOUND_HOSPITAL));
        return hospital.getReviews().stream()
                .map(ReviewInfoDto::of)
                .toList();
    }
    @Transactional
    public void createReview(final ReviewReqDto reviewReqDto, final Long userId){
        //병원이 존재하지 않을 경우 생성
        if(!hospitalRepository.existsByXAndY(reviewReqDto.getX(),reviewReqDto.getY())){
            hospitalService.createHospital(reviewReqDto.getX(),reviewReqDto.getY());
        }
        final Hospital hospital = hospitalRepository.findByXAndY(reviewReqDto.getX(),reviewReqDto.getY()).orElseThrow(() -> new HospitalNotFoundException(ErrorCode.NOT_FOUND_HOSPITAL));
        final User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER));
        //리뷰 생성
        reviewRepository.save(Review.of(reviewReqDto,hospital,user));
        //전체 score 업데이트
        hospital.updateHospitalScore();
    }
    @Transactional
    public void updateReview(final Long hospitalId, final Long reviewId, final ReviewEditDto reviewEditDto) {
        final Hospital hospital =  hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotFoundException(ErrorCode.NOT_FOUND_HOSPITAL));
        final Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(ErrorCode.NOT_FOUND_REVIEW));
        review.updateReview(reviewEditDto);
        //전체 score 업데이트
        hospital.updateHospitalScore();
    }
    @Transactional
    public void deleteReview(final Long hospitalId, final Long reviewId) {
        final Hospital hospital =  hospitalRepository.findById(hospitalId).orElseThrow(() -> new HospitalNotFoundException(ErrorCode.NOT_FOUND_HOSPITAL));
        final Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(ErrorCode.NOT_FOUND_REVIEW));
        hospital.getReviews().remove(review);
        reviewRepository.delete(review);
        //전체 score 업데이트
        hospital.updateHospitalScore();
    }
}
