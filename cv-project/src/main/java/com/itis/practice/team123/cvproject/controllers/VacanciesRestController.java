package com.itis.practice.team123.cvproject.controllers;

import com.itis.practice.team123.cvproject.dto.VacancyDto;
import com.itis.practice.team123.cvproject.models.Company;
import com.itis.practice.team123.cvproject.models.Vacancy;
import com.itis.practice.team123.cvproject.security.details.UserDetailsImpl;
import com.itis.practice.team123.cvproject.services.interfaces.VacanciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VacanciesRestController {

    private final VacanciesService vacanciesService;

    @GetMapping("/api/vacancies")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<List<Vacancy>> getVacanciesForCompany(@AuthenticationPrincipal UserDetailsImpl<Company> userDetails) {
        return ResponseEntity.ok().body(vacanciesService.getAllVacanciesForCompany(userDetails.getUser()));
    }

    @GetMapping("/api/company/{companyId}/vacancies")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Vacancy>> getVacancies(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok().body(vacanciesService.getAllVacanciesByCompanyId(companyId));
    }

    @GetMapping("/api/vacancies/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VacancyDto> getVacancy(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(vacanciesService.getVacancy(id));
    }

    @PostMapping("/vacancies/newVacancy")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> addVacancy(VacancyDto vacancyDto,
                                        @AuthenticationPrincipal UserDetailsImpl<Company> userDetails) {
        vacanciesService.addNewVacancy(vacancyDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/vacancies/deleteVacancy/{id}")
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<?> deleteVacancy(@PathVariable("id") Long id) {
        vacanciesService.deleteVacancy(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
