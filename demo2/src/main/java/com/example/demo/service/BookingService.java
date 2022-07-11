package com.example.demo.service;

import com.example.demo.dto.BookingDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Long book(BookingDto dto);

    void delete(Long id);

    List<LocalDate> getAvailable();

}
