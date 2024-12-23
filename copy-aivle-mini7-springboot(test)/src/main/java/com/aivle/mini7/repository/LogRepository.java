package com.aivle.mini7.repository;

import com.aivle.mini7.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {

    Page<Log> findByDatetimeBetween(String startDateTime, String endDateTime, Pageable pageable);
}
