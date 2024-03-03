package com.massonus.rccnavigator.controllers;

import com.massonus.rccnavigator.dto.CompanyFilterDto;
import com.massonus.rccnavigator.entity.Company;
import com.massonus.rccnavigator.entity.Image;
import com.massonus.rccnavigator.entity.User;
import com.massonus.rccnavigator.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final ImageService imageService;
    private final KitchenCategoryService kitchenCategoryService;
    private final CompanyCountryService companyCountryService;
    private final RatingService ratingService;
    private final MessageService messageService;

    @Autowired
    public CompanyController(CompanyService companyService, ImageService imageService, KitchenCategoryService kitchenCategoryService, CompanyCountryService companyCountryService, RatingService ratingService, MessageService messageService) {
        this.companyService = companyService;
        this.imageService = imageService;
        this.kitchenCategoryService = kitchenCategoryService;
        this.companyCountryService = companyCountryService;
        this.ratingService = ratingService;
        this.messageService = messageService;
    }

    @GetMapping
    public String getAllCompanies(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(required = false) Long categoryId,
                                  @RequestParam(required = false) Long countryId) {

        final CompanyFilterDto companyFilterDto = new CompanyFilterDto();
        companyFilterDto.setCategoryId(categoryId);
        companyFilterDto.setCountryId(countryId);

        Integer pageSize = 3;
        Page<Company> companyPage = companyService.getCompaniesInPage(companyFilterDto, page, pageSize);

        model.addAttribute("categories", kitchenCategoryService.getAllCategories());
        model.addAttribute("types", companyCountryService.getAllTypes());
        model.addAttribute("companies", companyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("countryId", countryId);

        return "company/allCompanies";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add-company")
    public String getAddCompanyForm(Model model) {

        model.addAttribute("categories", kitchenCategoryService.getAllCategories());
        model.addAttribute("types", companyCountryService.getAllTypes());

        return "company/addCompany";

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/new-company")
    public String newCompany(@Valid Company company,
                             @RequestParam Long categoryId,
                             @RequestParam Long typeId,
                             @RequestParam("file") MultipartFile multipartFile) {

        Image uploadImage = imageService.upload(multipartFile);

        companyService.saveCompany(company, uploadImage, kitchenCategoryService.getCategoryById(categoryId), companyCountryService.getTypeById(typeId));

        return "redirect:/admin/panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String updateCompany(@PathVariable("id") Long id, Model model) {
        Company company = companyService.getCompanyById(id);
        model.addAttribute("categories", kitchenCategoryService.getAllCategories());
        model.addAttribute("types", companyCountryService.getAllTypes());
        model.addAttribute("company", company);
        return "company/companyEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/edit/{id}")
    public String saveUpdatedCompany(@PathVariable Long id,
                                     @RequestParam Long categoryId,
                                     @RequestParam Long typeId,
                                     @RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam String imageLink,
                                     Company company) {

        companyService.editCompany(id, company, kitchenCategoryService.getCategoryById(categoryId), companyCountryService.getTypeById(typeId), multipartFile, imageLink);

        return "redirect:/admin/panel";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        Company companyById = companyService.getCompanyById(id);
        companyService.deleteCompany(companyById);
        return "redirect:/admin/panel";
    }


    @RequestMapping("/sort")
    public String getAllSortedCompanies(@RequestParam String sort, Model model) {

        List<Company> companies = (companyService.getSortedCompanies(sort));

        model.addAttribute("companies", companies);
        model.addAttribute("types", companyCountryService.getAllTypes());
        model.addAttribute("categories", kitchenCategoryService.getAllCategories());

        return "company/allCompanies";
    }

    @GetMapping("/{id}")
    public String getCompany(@AuthenticationPrincipal User user, @PathVariable Long id, Model model) {
        Company company = companyService.getCompanyById(id);

        model.addAttribute("user", user);
        model.addAttribute("company", company);
        model.addAttribute("messages", messageService.getMessagesByCompanyId(id));
        return "company/companyInfo";
    }

    @PostMapping("/rate-company/{id}")
    public String rateCompany(@PathVariable Long id, @AuthenticationPrincipal User author, @RequestParam Integer rate) {
        Company companyById = companyService.getCompanyById(id);

        ratingService.rateCompany(author, companyById, rate);

        return "redirect:/companies/" + id;
    }

}
