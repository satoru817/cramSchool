package com.example.demo.repository;

import com.example.demo.entity.RegularTest;
import com.example.demo.entity.RegularTestResult;
import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegularTestResultRepository extends JpaRepository<RegularTestResult,Integer> {
    public Optional<RegularTestResult> findByRegularTestAndStudent(RegularTest regularTest, Student student);

    @Query(value="select * from regular_test_result rtr " +
            "where rtr.regular_test_id = :regularTestId",
    nativeQuery = true)
    public List<RegularTestResult> findByRegularTestId(@Param("regularTestId")Integer regularTestId);
    //TODO:このクエリがあってるかテストが必要
    @Query(value="SELECT rtr.regular_test_result_id, rtr.regular_test_id , rtr.student_id, " +
            "rtr.japanese, rtr.math, rtr.english, " +
            "rtr.science, rtr.social, rtr.music, " +
            "rtr.art, rtr.tech, rtr.pe " +
            "FROM regular_test_result rtr " +
            "JOIN regular_test rt ON rt.regular_test_id = rtr.regular_test_id " +
            "JOIN regular_test_set rts ON rts.regular_test_set_id = rt.regular_test_set_id " +
            "WHERE rts.regular_test_set_id = :regularTestSetId",
            nativeQuery = true)
    List<RegularTestResult> findByRegularTestSetId(@Param("regularTestSetId") Integer regularTestSetId);

}



