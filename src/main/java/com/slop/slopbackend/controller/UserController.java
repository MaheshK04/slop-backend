package com.slop.slopbackend.controller;

import com.slop.slopbackend.dto.request.user.UpdateEmailIdReqDTO;
import com.slop.slopbackend.dto.request.user.UpdatePasswordReqDTO;
import com.slop.slopbackend.dto.request.user.UpdateUserReqDTO;
import com.slop.slopbackend.dto.response.user.UserResDTO;
import com.slop.slopbackend.entity.UserEntity;
import com.slop.slopbackend.exception.ApiRuntimeException;
import com.slop.slopbackend.service.UserService;
import com.slop.slopbackend.utility.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
// TODO: check if user is authorized to update his document
    @GetMapping("getUser")
    public UserResDTO getUserDetails(Authentication authentication){
        UserEntity userEntity=userService.getUserByEmailId(authentication.getName());
        return ModelMapperUtil.toUserResDTO(userEntity);
    }

    @PatchMapping("{id}")
    public Object updateUser(@RequestBody @Valid UpdateUserReqDTO updateUserReqDTO, @PathVariable UUID id){
        UserEntity userEntity=userService.updateUserById(updateUserReqDTO,id);
        return ModelMapperUtil.toUserResDTO(userEntity);
    }

   @PatchMapping("{id}/emailid")
   public UserResDTO updateUserEmailId(@RequestBody @Valid UpdateEmailIdReqDTO updateEmailIdReqDTO, @PathVariable UUID id){
        UserEntity userEntity=userService.updateEmailIdById(updateEmailIdReqDTO,id);
        return ModelMapperUtil.toUserResDTO(userEntity);
   }

    @PatchMapping("{id}/password")
    public UserResDTO updateUserPassword(@RequestBody @Valid UpdatePasswordReqDTO updatePasswordReqDTO, @PathVariable UUID id){
        UserEntity userEntity=userService.updatePasswordById(updatePasswordReqDTO.getPassword(),id);
        return ModelMapperUtil.toUserResDTO(userEntity);
    }
    @PatchMapping("{id}/profile-picture")
    public UserResDTO updateUserProfilePicture(@PathVariable @NotEmpty @Valid UUID id, @RequestParam("file") @NotEmpty @Valid MultipartFile file) throws IOException {
        if (file == null || file.isEmpty() || !Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            throw new ApiRuntimeException("Invalid file", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity=userService.updateUserProfilePictureById(id,file);
        return ModelMapperUtil.toUserResDTO(userEntity);
    }
    @GetMapping(path="{id}/profile-picture",produces = "image/jpeg")
    public ResponseEntity<byte[]> getUserProfilePicture(@PathVariable UUID id) throws IOException {
        byte[] imageData= userService.getUserProfilePicture(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
