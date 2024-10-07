package com.example.demo.controller;

import com.example.demo.dto.ClassRegistrationForm;
import com.example.demo.entity.Klass;
import com.example.demo.entity.Student;
import com.example.demo.service.ClassService;
import com.example.demo.service.ClassStudentService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TermAndYearService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ClassController {
    private final ClassStudentService classStudentService;
    private final ClassService classService;
    private final StudentService studentService;
    private final TermAndYearService termAndYearService;

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

    @GetMapping("/studentClassEdit")
    public String studentClassEdit(Model model) {
        List<Student> studentList = studentService.fetchAll();
        List<ClassRegistrationForm> classRegistrationForms = new ArrayList<>();
        Date today = termAndYearService.getTodayAsDate();
        String[] classOptions = {"特選","α","β1","β2","サッカー1","サッカー2","N/A"};

        for (Student student : studentList) {
            ClassRegistrationForm classRegistrationForm = new ClassRegistrationForm();
            classRegistrationForm.setStudentId(student.getId());
            classRegistrationForm.setStudentName(student.getName());


            try {
                classRegistrationForm.setJapaneseClassName(classStudentService.findKlassForJapaneseByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setJapaneseClassName("N/A");
            }

            try {
                classRegistrationForm.setMathClassName(classStudentService.findKlassForMathByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setMathClassName("N/A");
            }

            try {
                classRegistrationForm.setEnglishClassName(classStudentService.findKlassForEnglishByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setEnglishClassName("N/A");
            }

            try {
                classRegistrationForm.setScienceClassName(classStudentService.findKlassForScienceByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setScienceClassName("N/A");
            }


            try {
                classRegistrationForm.setSocialClassName(classStudentService.findKlassForSocialByStudentAndDate(student, today).getName());
            } catch (Exception e) {
                classRegistrationForm.setSocialClassName("N/A");
            }



            classRegistrationForms.add(classRegistrationForm);
        }



        model.addAttribute("classRegistrationForms", classRegistrationForms);
        model.addAttribute("classOptions",classOptions);
        return "class/class_student_register";
    }

    @PostMapping("/submitClassRegistration")
    public String submitClassRegistration(@RequestParam Map<String,String>params){
        List<Student> studentList = studentService.fetchAll();
        Date today = termAndYearService.getTodayAsDate();

        for(Student student:studentList){
            Integer studentId = student.getId();

            //各科目のクラス名を取得
            String japaneseClassName = params.get("japaneseClassName_" + studentId);
            String mathClassName = params.get("mathClassName_" + studentId);
            String englishClassName = params.get("englishClassName_" + studentId);
            String scienceClassName = params.get("scienceClassName_" + studentId);
            String socialClassName = params.get("socialClassName_" + studentId);

            // それぞれの科目のクラスが変更されたかチェックし、変更された場合、あるいはもともと所属クラスが存在しない場合のみ保存

        }



}
