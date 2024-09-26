package com.example.demo.controller;

import com.example.demo.entity.School;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentShow;
import com.example.demo.form.StudentForm;
import com.example.demo.service.SchoolService;
import com.example.demo.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    private StudentService studentService;
    private SchoolService schoolService;

    public StudentController(StudentService studentService, SchoolService schoolService){
        this.studentService = studentService;
        this.schoolService = schoolService;
    }

    @GetMapping("/studentRegister")
    public String studentRegister_g(Model model){
        if(!model.containsAttribute("studentForm")){
            StudentForm studentForm = new StudentForm();
            model.addAttribute("studentForm",studentForm);
        }

        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList",schoolList);

        return "student/student_register";
    }

    @PostMapping("/studentRegister")
    public String studentRegister_p(Model model,
                                    @Validated StudentForm studentForm,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes){
        if(!bindingResult.hasErrors()){

            Student student = new Student();
            student.setCode(studentForm.getCode());
            student.setName(studentForm.getName());
            student.setStatus(studentForm.getStatus());
            student.setSchool(schoolService.fetchById(studentForm.getSchoolId()));
            studentService.save(student);
            return "redirect:/studentShow";

        }else{
            System.out.println("there is a error");
            redirectAttributes.addFlashAttribute("studentForm",studentForm);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
            +"studentForm",bindingResult);

            return "redirect:/studentRegister";
        }
    }

    @GetMapping("/studentShow")
    public String studentShow_g(Model model){
        List<Student> studentList = studentService.fetchAll();

        List<StudentShow> studentShows = convertToStudentShowList(studentList,schoolService);

        model.addAttribute("studentShows",studentShows);
        return "student/student_show";
    }

    @GetMapping("/studentEdit/{id}")
    public String editStudent(@PathVariable(name="id") Integer id,
                              Model model){
        Student student = studentService.getById(id);
        StudentForm studentForm = convertStudentToStudentForm(student);

        model.addAttribute("studentForm" , studentForm);
        List<School> schoolList = schoolService.fetchAll();
        model.addAttribute("schoolList",schoolList);

        return "student/student_edit";
    }

    public List<StudentShow> convertToStudentShowList(List<Student> students,SchoolService schoolService){
        List<StudentShow> studentShows = new ArrayList<>();
        for(Student student:students){
            StudentShow studentShow = new StudentShow();
            studentShow.setId(student.getId());
            studentShow.setCode(student.getCode());
            studentShow.setName(student.getName());
            studentShow.setStatus(student.getStatus());
            School school = schoolService.fetchById(student.getSchool().getId());
            studentShow.setSchoolName(school.getName());
            studentShows.add(studentShow);
        }

        return studentShows;
    }

    public StudentForm convertStudentToStudentForm(Student student){
        return new StudentForm(student.getCode(),student.getName(),student.getStatus(),student.getSchool().getId());
    }





}
