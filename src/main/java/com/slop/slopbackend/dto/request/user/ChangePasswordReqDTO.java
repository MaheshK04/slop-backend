package com.slop.slopbackend.dto.request.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordReqDTO {
    @NotEmpty
    @Size(min=5,max = 32)
    private String password;

    @NotEmpty
    private String otp;
}
