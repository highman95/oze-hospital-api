package com.oze.hospitalmanager.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "patients")
public class Patient extends Person {
    @Positive
    @Min(value = 2, message = "Age must be 2 and above")
    private Integer age;
    private LocalDate lastVisitDate;
}
