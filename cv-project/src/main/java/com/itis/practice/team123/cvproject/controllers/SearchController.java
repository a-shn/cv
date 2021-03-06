package com.itis.practice.team123.cvproject.controllers;


import com.itis.practice.team123.cvproject.dto.FilterFormData;
import com.itis.practice.team123.cvproject.dto.WeightedStudentDto;
import com.itis.practice.team123.cvproject.enums.Education;
import com.itis.practice.team123.cvproject.models.Tag;
import com.itis.practice.team123.cvproject.repositories.LanguageRepository;
import com.itis.practice.team123.cvproject.repositories.TagsRepository;
import com.itis.practice.team123.cvproject.services.interfaces.LanguageService;
import com.itis.practice.team123.cvproject.services.interfaces.SearchService;
import com.itis.practice.team123.cvproject.services.interfaces.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class SearchController {
    //обратись к сервисам для предоставления тегов
    private final TagService tagService;
    private final SearchService searchService;
    //тоже самое обратись к сервисам
    private final LanguageService languageService;

    @PreAuthorize("permitAll()")
    @GetMapping(value = {"/api/search","/search/tags"})
    public @ResponseBody ResponseEntity<List<Tag>> tagsView() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = {"/search", "/api/search"})
    public @ResponseBody ResponseEntity<List<WeightedStudentDto>> competenceSave(FilterFormData formData) {
        return ResponseEntity.ok(searchService.getStudentsByFilters(formData));
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = {"/search"})
    public String searchView(Model model) {
        model.addAttribute("educations", Education.values());
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("languages", languageService.getAllLanguages());
        return "search";
    }

}
