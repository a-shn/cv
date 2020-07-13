package com.itis.practice.team123.cvproject.services.impl;

import com.itis.practice.team123.cvproject.enums.Role;
import com.itis.practice.team123.cvproject.models.Company;
import com.itis.practice.team123.cvproject.models.Teacher;
import com.itis.practice.team123.cvproject.models.User;
import com.itis.practice.team123.cvproject.services.interfaces.ProfileService;
import com.itis.practice.team123.cvproject.services.interfaces.UsersService;
import com.itis.practice.team123.cvproject.utils.Initializer;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.nio.file.AccessDeniedException;

import static com.itis.practice.team123.cvproject.enums.Role.*;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final UsersService usersService;

    @Override
    public String getProfile(User user, Model model) throws IllegalArgumentException {
        //костыль
        user = usersService.getUser(user.getId());

        switch (user.getRole()) {
            case TEACHER:
                loadTeacherInfo(user, model);
                return "teacherProfile";
            case COMPANY:
                loadCompanyInfo(user, model);
                return "companyView";
            case ADMIN:
                return "panel";
            default:
                throw new IllegalStateException("Unexpected value: " + user.getClass());
        }
    }

    private void loadCompanyInfo(User user, Model model) {
        Company company = (Company) user;
        model.addAttribute("id", company.getId());
        model.addAttribute("meil", company.getEmail());
        if (company.getDescription() != null) model.addAttribute("description", company.getDescription());
        if (company.getAddress() != null) model.addAttribute("address", company.getAddress());
        if (company.getName() != null) model.addAttribute("name", company.getName());
        if (company.getPhone() != null) model.addAttribute("phone", company.getPhone());
    }

    private void loadTeacherInfo(User user, Model model) {
        Teacher teacher = (Teacher) user;
        model.addAttribute("id", teacher.getId());
        if (teacher.getName() != null) model.addAttribute("name", teacher.getName());
        if (teacher.getPatronymic() != null) model.addAttribute("patronymic", teacher.getPatronymic());
        if (teacher.getSurname() != null) model.addAttribute("surname", teacher.getSurname());
        if (teacher.getInstitution() != null) model.addAttribute("institution", teacher.getInstitution());
        if (teacher.getLanguages() != null) model.addAttribute("languages", teacher.getLanguages());
        if (teacher.getAdditionalInfo() != null) model.addAttribute("info", teacher.getAdditionalInfo());
    }

    @Override
    public String getProfile(Long id, Model model) throws IllegalArgumentException {
        User user = usersService.getUser(id);
        return this.getProfile(user, model);
    }
}
