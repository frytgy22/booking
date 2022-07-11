package com.example.demo.service.impl;

import com.example.demo.dto.BookingDto;
import com.example.demo.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.concurrent.Callable;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BookingServiceImplTest {

    @Autowired
    private BookingService bookingServiceImpl;

    @Test()
    void shouldBookForFirstDto() throws Exception {
        BookingDto firstBook = BookingDto.builder()
                .name("Jack")
                .surname("Jenkins")
                .email("jj@gmail.com")
                .dateFrom(LocalDate.now().plusDays(1))
                .dateTo(LocalDate.now().plusDays(3))
                .build();

        BookingDto secondBook = BookingDto.builder()
                .name("Jon")
                .surname("Collins")
                .email("jc@gmail.com")
                .dateFrom(LocalDate.now().plusDays(2))
                .dateTo(LocalDate.now().plusDays(4))
                .build();

        Callable<Long> firstCall = () -> bookingServiceImpl.book(firstBook);
        Callable<Long> secondCall = () -> bookingServiceImpl.book(secondBook);
        Long firstAnswer = firstCall.call();

        int firstBookedId = 1;
        Assertions.assertEquals(firstBookedId, firstAnswer);

        IllegalStateException thrown = assertThrows(
                IllegalStateException.class, secondCall::call, "");

        assertEquals(thrown.getMessage(), "Not available date from " + secondBook.getDateFrom() + " to " + secondBook.getDateTo());
    }
}