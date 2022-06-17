package com.oze.hospitalmanager.services;

public interface IPatientService {
    void fetchProfiles();

    void downloadProfile(Long id);

    void deleteProfiles();
}
