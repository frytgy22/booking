package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    @NonNull
    private LocalDate dateFrom;
    @NonNull
    private LocalDate dateTo;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String email;
}
