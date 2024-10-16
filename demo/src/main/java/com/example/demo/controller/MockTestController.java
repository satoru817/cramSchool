package com.example.demo.controller;
import com.example.demo.entity.*;
import com.example.demo.repository.MockTestRepository;
import com.example.demo.repository.MockTestResultRepository;
import com.example.demo.repository.StatusRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import com.example.demo.dto.StudentShow;
import com.example.demo.dto.StudentForm;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MockTestController {
    private final MockTestRepository mockTestRepository;
    private final MockTestResultRepository mockTestResultRepository;
    private final StudentRepository studentRepository;
    private final TermAndYearService termAndYearService;

    @PostMapping("/registerMockTest")
    public String uploadCsv(@RequestParam("file") MultipartFile file,
                            @RequestParam("date") LocalDate date,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            model.addAttribute("error", "CSVファイルを選択してください。");
            return "error"; // エラービューに戻る
        }

        String fileName = file.getOriginalFilename(); // ファイル名を取得
        //todo:mockTestを作成して保存
        MockTest mockTest = new MockTest();
        mockTest.setDate(date);
        mockTest.setName(fileName);
        mockTestRepository.save(mockTest);//unique制約がnameにあるので同じファイル名のをアップロードするとupsertされる

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {


            // Read the CSV records starting from the header
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(',')
                    .withQuote('"')
                    .withFirstRecordAsHeader()  // Use the first line after the skipped lines as header
                    .parse(reader);

            for (CSVRecord record : records) {

                System.out.println("Record: " + record);
                // Access the specific columns by header names
                String registrationNumber = record.get("登録番号");
                String jukuCode = record.get("塾コード");
                String jukuName = record.get("塾名");
                String classroomName = record.get("教室名");
                String grade = record.get("学年");
                String jukuNumber = record.get("塾内番号");
                String gender = record.get("性別");
                String studentName = record.get("氏名");
                String term = record.get("年度");
                String product = record.get("商品");
                String testGrade = record.get("学年"); // 重複しているので注意
                String roundNumber = record.get("回号");
                String japanese = record.get("国語\n得点");
                String japanseseSs = record.get("国語\n偏差値");
                String math = record.get("数学\n得点");
                String mathSs = record.get("数学\n偏差値");
                String english = record.get("英語\n得点");
                String englishSs = record.get("英語\n偏差値");
                String social = record.get("社会\n得点");
                String socialSs = record.get("社会\n偏差値");
                String science = record.get("理科\n得点");
                String scienceSs = record.get("理科\n偏差値");
                String jme = record.get("二科三科\n得点");
                String jmeSs = record.get("二科三科\n偏差値");
                String jmess = record.get("四科五科\n得点");
                String jmessSs = record.get("四科五科\n偏差値");
                String dreamSchool1 = record.get("志望校名１");
                String probability1 = record.get("合格\n可能性１");
                String dreamSchool2 = record.get("志望校名２");
                String probability2 = record.get("合格\n可能性２");
                String dreamSchool3 = record.get("志望校名３");
                String probability3 = record.get("合格\n可能性３");
                String dreamSchool4 = record.get("志望校名４");
                String probability4 = record.get("合格\n可能性４");
                String dreamSchool5 = record.get("志望校名５");
                String probability5 = record.get("合格\n可能性５");
                String dreamSchool6 = record.get("志望校名６");
                String probability6 = record.get("合格\n可能性６");
                //TODO:studentNameと学年が一致するstudentを取ってくる必要がある。実施日と学年からel1を計算して、el1とstudentNameでsql発行すればいい。
                Integer el1 = termAndYearService.getWhenEnteredElementarySchoolForCsvReader(testGrade,date);
                Optional<Student> student = studentRepository.findByEl1AndName(el1,studentName);

                // MockTestResult を設定
                MockTestResult mockTestResult = new MockTestResult();

                // MockTest と Student の設定
                mockTestResult.setMockTest(mockTest); // mockTest は事前に取得または作成しておく
                if (student.isPresent()) {
                    mockTestResult.setStudent(student.get());
                } else {
                    // Student が見つからなかった場合の処理
                    throw new IllegalArgumentException("Student not found: " + studentName);
                }

                // 点数の設定 (null チェックを追加)
                mockTestResult.setJapanese(parseInteger(japanese));
                mockTestResult.setJapaneseSs(parseInteger(japanseseSs));
                mockTestResult.setMath(parseInteger(math));
                mockTestResult.setMathSs(parseInteger(mathSs));
                mockTestResult.setEnglish(parseInteger(english));
                mockTestResult.setEnglishSs(parseInteger(englishSs));
                mockTestResult.setScience(parseInteger(science));
                mockTestResult.setScienceSs(parseInteger(scienceSs));
                mockTestResult.setSocial(parseInteger(social));
                mockTestResult.setSocialSs(parseInteger(socialSs));
                mockTestResult.setJmeSs(parseInteger(jmeSs));
                mockTestResult.setJmessSs(parseInteger(jmessSs));

                // 志望校名と合格可能性の設定 (null チェックを追加)
                mockTestResult.setDreamSchool1(dreamSchool1);
                mockTestResult.setDreamSchool1Probability(parseProbability(probability1));
                mockTestResult.setDreamSchool2(dreamSchool2);
                mockTestResult.setDreamSchool2Probability(parseProbability(probability2));
                mockTestResult.setDreamSchool3(dreamSchool3);
                mockTestResult.setDreamSchool3Probability(parseProbability(probability3));
                mockTestResult.setDreamSchool4(dreamSchool4);
                mockTestResult.setDreamSchool4Probability(parseProbability(probability4));
                mockTestResult.setDreamSchool5(dreamSchool5);
                mockTestResult.setDreamSchool5Probability(parseProbability(probability5));
                mockTestResult.setDreamSchool6(dreamSchool6);
                mockTestResult.setDreamSchool6Probability(parseProbability(probability6));

                mockTestResultRepository.save(mockTestResult);
            }


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        return "mockTest結果一覧画面";//TODO:tuku
    }
    // 整数に変換するメソッド (null チェックを追加)
    private Integer parseInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // または適切なデフォルト値を返す
        }
        return Integer.valueOf(value.trim());
    }
    // 合格可能性を変換するメソッド
    private Integer parseProbability(String probability) {
        if (probability == null || probability.trim().isEmpty() || probability.equals("**")) {
            return null; // または適切なデフォルト値を返す
        }
        return Integer.valueOf(probability.trim());
    }

    @GetMapping("/mockTestRegisterForm")
    public String mockTestRegisterForm(){
        return "/mockTest/mockTestRegister";
    }
}