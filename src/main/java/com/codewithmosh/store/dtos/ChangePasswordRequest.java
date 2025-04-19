package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
