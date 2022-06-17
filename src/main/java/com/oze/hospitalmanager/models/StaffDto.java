package com.oze.hospitalmanager.models;

import javax.validation.constraints.NotEmpty;

import com.oze.hospitalmanager.utils.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffDto {
    @NotEmpty(message = Constants.NAME_IS_REQUIRED)
    private String name;
}
