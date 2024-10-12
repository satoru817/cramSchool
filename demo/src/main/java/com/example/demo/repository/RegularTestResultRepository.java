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
}



