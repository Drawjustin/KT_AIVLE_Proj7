package com.aivle.mini7.controller;

import com.aivle.mini7.dto.LogDto;
import com.aivle.mini7.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {
    private final LogService logService;

    @GetMapping("")
    public ModelAndView index(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,    // 페이지 기본값 0
            @RequestParam(defaultValue = "10") int size,   // 페이지 사이즈 기본값 10
            @RequestParam(required = false, defaultValue = "false") Boolean showAll) {  // 전체 조회 여부

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/index");

        Page<LogDto.ResponseList> logList;
        PageRequest pageRequest = PageRequest.of(page, size);

        if (Boolean.TRUE.equals(showAll)) {
            // 전체 조회
//            logList = logService.getLogList(pageRequest);
            // 날짜 필터링 조회
            startDate = LocalDate.parse("1900-01-01");
            endDate = LocalDate.now();
            logList = logService.getLogList(startDate, endDate, pageRequest);
        } else {
            // 날짜 필터링 조회
            if (startDate == null) startDate = LocalDate.now().minusMonths(1);
            if (endDate == null) endDate = LocalDate.now();
            logList = logService.getLogList(startDate, endDate, pageRequest);
        }

        mv.addObject("logList", logList);
        mv.addObject("startDate", startDate);
        mv.addObject("endDate", endDate);
        mv.addObject("showAll", showAll);

        return mv;
    }
}