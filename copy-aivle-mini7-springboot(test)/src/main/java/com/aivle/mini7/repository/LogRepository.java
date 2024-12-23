package com.aivle.mini7.repository;

import com.aivle.mini7.model.Log;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Repository
public interface LogRepository extends JpaRepository<Log, String> {
//    Page<Log> findByDatetimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
//
//    @Query("SELECT l FROM Log l JOIN FETCH l.hospitals WHERE l.id = :id")
//    Optional<Log> findByIdWithHospitals(@Param("id") String id);

    @Query(value = """
            SELECT DISTINCT l 
            FROM Log l 
            LEFT JOIN FETCH l.hospitals 
            WHERE l.datetime BETWEEN :startDateTime AND :endDateTime
            """,
            countQuery = "SELECT COUNT(l) FROM Log l WHERE l.datetime BETWEEN :startDateTime AND :endDateTime")
    Page<Log> findByDatetimeBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            Pageable pageable);

    // 디버깅을 위한 추가 메서드
    @Query("SELECT l FROM Log l LEFT JOIN FETCH l.hospitals WHERE l.id = :id")
    Optional<Log> findByIdWithHospitals(@Param("id") Integer id);
}