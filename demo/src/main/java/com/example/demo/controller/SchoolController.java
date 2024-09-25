package com.example.demo.controller;

import com.example.demo.entity.School;
import com.example.demo.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SchoolController {
    private SchoolService schoolService;

    public SchoolController(SchoolService schoolService){
        this.schoolService = schoolService;
    }

    @GetMapping("/registerSchool")
    public String registerSchool_g(Model model){
        if(!model.containsAttribute("school")){
            School school = new School();
            model.addAttribute("school",school);
        }
        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList",schoolList);

        return "/school/school_register";
    }

    @PostMapping("/registerSchool")
    public String registerSchool_p(Model model,
                                   @ModelAttribute @Validated School school,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){
        if(!bindingResult.hasErrors()){
            schoolService.save(school);
            return registerSchool_g(model);
        }else{
            redirectAttributes.addFlashAttribute("school",school);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            +"school",bindingResult);

            return "redirect:/registerSchool";
        }

    }

    @GetMapping("/school/delete/{id}")
    public String delete(@PathVariable(name="id")Integer id, RedirectAttributes redirectAttributes){
        schoolService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage","学校を削除しました。");
        return "redirect:/registerSchool";
    }

}
