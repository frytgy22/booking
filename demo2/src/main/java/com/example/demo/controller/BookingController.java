package com.example.demo.controller;

import com.example.demo.dto.BookingDto;
import com.example.demo.service.BookingService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookingController {

    private final BookingService bookingServiceImpl;

    @Autowired
    public BookingController(BookingService bookingServiceImpl) {
        this.bookingServiceImpl = bookingServiceImpl;
    }

    @PostMapping
    public Long book(@NonNull @RequestBody BookingDto bookingDto) {
        log.info("Called book() with data {}, {}", bookingDto.getDateFrom(), bookingDto.getDateTo());
        return bookingServiceImpl.book(bookingDto);
    }

    @PutMapping
    public Long update(@NonNull @RequestBody BookingDto bookingDto) {
        log.info("Called update() with data {}, {}", bookingDto.getDateFrom(), bookingDto.getDateTo());
        return bookingServiceImpl.book(bookingDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@NonNull @PathVariable Long id) {
        log.info("Called delete() with id {}", id);
        bookingServiceImpl.delete(id);
    }

    @GetMapping("/available")
    public List<LocalDate> getAvailableDatesToBook() {
        log.info("Called getAvailableDatesToBook()");
        return bookingServiceImpl.getAvailable();
    }
}
