package koders.codi.domain.review.controller;

import koders.codi.domain.review.dto.ReviewInfoDto;
import koders.codi.domain.review.dto.ReviewReqDto;
import koders.codi.domain.review.dto.ReviewEditDto;
import koders.codi.domain.review.service.ReviewService;
import koders.codi.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hospitals")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{hospitalId}/reviews")
    public ResponseEntity<List<ReviewInfoDto>> getReviewList(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(reviewService.getReviewList(hospitalId));
    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> createReview(
                                             @RequestBody ReviewReqDto reviewReqDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        reviewService.createReview(reviewReqDto, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{hospitalId}/reviews/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long hospitalId,
                                             @PathVariable Long reviewId,
                                             @RequestBody ReviewEditDto reviewEditDto) {
        reviewService.updateReview(hospitalId, reviewId, reviewEditDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{hospitalId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long hospitalId,
                                             @PathVariable Long reviewId) {
        reviewService.deleteReview(hospitalId, reviewId);
        return ResponseEntity.ok().build();
    }
}
