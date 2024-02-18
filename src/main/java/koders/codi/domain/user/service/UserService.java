package koders.codi.domain.user.service;

import koders.codi.domain.post.service.ImageService;
import koders.codi.domain.user.dto.SignupDto;
import koders.codi.domain.user.dto.UserInfoDto;
import koders.codi.domain.user.entity.User;
import koders.codi.global.exception.custom.ImageException;
import koders.codi.domain.user.repository.UserRepository;
import koders.codi.global.exception.ErrorCode;
import koders.codi.domain.user.exception.UserEmailAlreadyExistException;
import koders.codi.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Transactional
    public void signup(SignupDto signupDto) {
        checkEmailDuplicated(signupDto.getEmail());
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        userRepository.save(User.of(signupDto, profileImageValidator(signupDto.getProfileImage())));
    }
    public void checkEmailDuplicated(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserEmailAlreadyExistException(ErrorCode.EMAIL_ALREADY_EXIST);
        }
    }
    public String profileImageValidator(MultipartFile profileImage) {
        String defaultProfileImageUrl = "https://storage.googleapis.com/codi-bucket/4726a73f-1c9a-42c4-89bb-f4747952ee29";
        try {
            if(profileImage != null && !profileImage.isEmpty()) {
                return imageService.uploadImage(profileImage);
            }
            else {
                return defaultProfileImageUrl;
            }
        } catch (IOException e) {
            throw new ImageException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
    }
    public UserInfoDto getUser(Long id) {
        return UserInfoDto.of(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorCode.NOT_FOUND_USER)));
    }
}