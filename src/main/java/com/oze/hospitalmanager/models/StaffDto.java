package com.oze.hospitalmanager.models;

import javax.validation.constraints.NotBlank;

import com.oze.hospitalmanager.utils.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    @NotBlank(message = Constants.NAME_IS_REQUIRED)
    private String name;
}
