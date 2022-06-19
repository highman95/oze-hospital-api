package com.oze.hospitalmanager.models;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = Constants.NAME_IS_REQUIRED)
    private String name;
}
