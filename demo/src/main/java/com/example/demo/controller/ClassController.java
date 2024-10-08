package com.example.demo.controller;

import com.example.demo.dto.ClassRegistrationForm;
import com.example.demo.entity.Klass;
import com.example.demo.entity.KlassStudent;
import com.example.demo.entity.Student;
import com.example.demo.repository.ClassRepository;
import com.example.demo.repository.ClassStudentRepository;
import com.example.demo.service.ClassService;
import com.example.demo.service.ClassStudentService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TermAndYearService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ClassController {
    private final ClassStudentService classStudentService;
    private final ClassStudentRepository classStudentRepository;
    private final ClassService classService;
    private final StudentService studentService;
    private final TermAndYearService termAndYearService;
    private final ClassRepository classRepository;

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
    public String submitClassRegistration(@RequestParam Map<String, String> params) {
        List<Student> studentList = studentService.fetchAll();
        Date today = termAndYearService.getTodayAsDate();

        for (Student student : studentList) {
            System.out.println("処理中のstudent: " + student.getName() );
            Integer studentId = student.getId();

            String japaneseClassName = params.get("japaneseClassName" + studentId);
            System.out.println("japaneseClassName_" + japaneseClassName);

            String mathClassName = params.get("mathClassName" + studentId);
            System.out.println("mathClassName_" + mathClassName);

            String englishClassName = params.get("englishClassName" + studentId);
            System.out.println("englishClassName_" + englishClassName);

            String scienceClassName = params.get("scienceClassName" + studentId);
            System.out.println("scienceClassName_" + scienceClassName);

            String socialClassName = params.get("socialClassName" + studentId);
            System.out.println("socialClassName_" + socialClassName);


            // Fetch previous class names safely
            List<KlassStudent> japaneseKlassList = classStudentRepository.findKlassForJapaneseByStudentAndDate(student, today);
            String prevJapaneseClassName = getPrevClassName(japaneseKlassList.isEmpty() ? null : japaneseKlassList.getFirst());
            System.out.println("prevJapaneseClassName: " + prevJapaneseClassName);

            List<KlassStudent> mathKlassList = classStudentRepository.findKlassForMathByStudentAndDate(student, today);
            String prevMathClassName = getPrevClassName(mathKlassList.isEmpty() ? null : mathKlassList.getFirst());
            System.out.println("prevMathClassName: " + prevMathClassName);

            List<KlassStudent> englishKlassList = classStudentRepository.findKlassForEnglishByStudentAndDate(student, today);
            String prevEnglishClassName = getPrevClassName(englishKlassList.isEmpty() ? null : englishKlassList.getFirst());
            System.out.println("prevEnglishClassName: " + prevEnglishClassName);

            List<KlassStudent> scienceKlassList = classStudentRepository.findKlassForScienceByStudentAndDate(student, today);
            String prevScienceClassName = getPrevClassName(scienceKlassList.isEmpty() ? null : scienceKlassList.getFirst());
            System.out.println("prevScienceClassName: " + prevScienceClassName);

            List<KlassStudent> socialKlassList = classStudentRepository.findKlassForSocialByStudentAndDate(student, today);
            String prevSocialClassName = getPrevClassName(socialKlassList.isEmpty() ? null : socialKlassList.getFirst());
            System.out.println("prevSocialClassName: " + prevSocialClassName);



            // クラスの変更があった場合の処理
            updateClassIfChanged(student, today, "国語", japaneseClassName, prevJapaneseClassName);
            updateClassIfChanged(student, today, "数学", mathClassName, prevMathClassName);
            updateClassIfChanged(student, today, "英語", englishClassName, prevEnglishClassName);
            updateClassIfChanged(student, today, "理科", scienceClassName, prevScienceClassName);
            updateClassIfChanged(student, today, "社会", socialClassName, prevSocialClassName);
        }

        return "redirect:/studentClassEdit";
    }

    private String getPrevClassName(KlassStudent klassStudent) {
        return (klassStudent != null) ? klassStudent.getKlass().getName() : null;
    }




    private void updateClassIfChanged(Student student, Date today, String subject, String newClassName, String prevClassName) {
        if (prevClassName != null && !newClassName.equals(prevClassName)) {
            Optional<KlassStudent> optionalPrevKlassStudent = Optional.ofNullable(classStudentRepository.findKlassStudentForASubjectAndDate(student, today, subject).getFirst());

            if (optionalPrevKlassStudent.isPresent()) {
                KlassStudent prevKlassStudent = optionalPrevKlassStudent.get();
                prevKlassStudent.setChangedAt(termAndYearService.getSqlToday());
                classStudentRepository.save(prevKlassStudent); // Save the updated prevKlassStudent

                Klass klass = classRepository.findBySubjectAndName(subject, newClassName);
                if (klass != null) {
                    KlassStudent newKlassStudent = new KlassStudent();
                    newKlassStudent.setKlass(klass);
                    newKlassStudent.setStudent(student);
                    newKlassStudent.setCreatedAt(termAndYearService.getSqlToday());
                    newKlassStudent.setChangedAt(termAndYearService.maxSqlDate);
                    classStudentRepository.save(newKlassStudent); // 新たなKlassStudentを作成、編集して保存
                }else{
                    log.error("Klass not found for subject: " + subject + ", class name: " + prevClassName);
                }
            }
        }else if(prevClassName == null){
            Klass klass = classRepository.findBySubjectAndName(subject, newClassName);
            KlassStudent newKlassStudent = new KlassStudent();
            newKlassStudent.setKlass(klass);
            newKlassStudent.setChangedAt(termAndYearService.maxSqlDate);
            newKlassStudent.setCreatedAt(termAndYearService.minSqlDate);
            newKlassStudent.setStudent(student);
            classStudentRepository.save(newKlassStudent);
        }
    }



}
