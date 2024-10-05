package com.example.demo.controller;

import com.example.demo.entity.Status;
import com.example.demo.service.StatusService;
import com.example.demo.service.StatusStudentService;
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
public class StatusController {
    private final StatusService statusService;
    private final StatusStudentService statusStudentService;

    @GetMapping("/editStatus")
    public String editStatus(Model model){
        if(!model.containsAttribute("status")){
            Status status = new Status();
            model.addAttribute("status",status);
        }
        List<Status> statusList = statusService.findAll();
        model.addAttribute("statusList",statusList);


        return "status/statusEditView";
    }

    @Transactional
    @GetMapping("/statusDelete/{id}")
    public String deleteStatus(@PathVariable("id")Integer id){
        statusService.deleteStatus(id);
        return "redirect:/editStatus";
    }

    @PostMapping("/registerStatus")
    public String registerStatus(@Validated Status status,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("status",status);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            + Conventions.getVariableName(status),bindingResult);
            return "redirect:/editStatus";
        }else{
            statusService.save(status);
            return "redirect:/editStatus";
        }
    }
}
