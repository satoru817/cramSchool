package com.example.demo.controller;

import com.example.demo.entity.Klass;
import com.example.demo.service.ClassService;
import com.example.demo.service.ClassStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ClassController {
    private final ClassStudentService classStudentService;
    private final ClassService classService;

    @GetMapping("/classEdit")
    public String classEdit(Model model){
        List<Klass> classList = classService.getAll();
        Klass klass = new Klass();
        model.addAttribute("classList",classList);
        model.addAttribute("klass",klass);
        return "class/classEditView";
    }

    @Transactional
    @GetMapping("/classDelete/{id}")
    public String deleteClass(@PathVariable("id")Integer id){
        classService.deleteById(id);
        return "redirect:/classEdit";
    }

    @PostMapping("/createClass")
    public String createClass(@Validated Klass klass,
                              BindingResult result,
                              RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("klass",klass);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            + Conventions.getVariableName(klass),result);
            return "redirect:/classEdit";
        }else{
            classService.save(klass);
            return "redirect:/classEdit";
        }
    }



}
