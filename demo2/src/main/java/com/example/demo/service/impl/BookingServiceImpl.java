package com.example.demo.service.impl;

import com.example.demo.dto.BookingDto;
import com.example.demo.model.Booking;
import com.example.demo.repository.BookingRepository;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Long book(BookingDto dto) {

        if (dto.getDateTo().isBefore(dto.getDateFrom())
                || dto.getDateTo().isAfter(dto.getDateFrom().plusDays(2))) {
            throw new IllegalStateException("You can book only for 3 days");
        }

        List<LocalDate> availableDates = getAvailable();
        boolean canBook = !availableDates.isEmpty()
                && availableDates.contains(dto.getDateFrom())
                && availableDates.contains(dto.getDateTo());

        if (canBook) {
            return bookingRepository.save(mapToBooking(dto)).getId();
        }
        throw new IllegalStateException("Not available date from " + dto.getDateFrom() + " to " + dto.getDateTo());
    }

    @Override
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<LocalDate> getAvailable() {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusMonths(1);
        List<LocalDate> localDates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .toList();

        List<Booking> bookings = bookingRepository.findAllByDateFromGreaterThanEqualAndDateToLessThanEqual(start, end);
        List<LocalDate> booked = new ArrayList<>();

        bookings.forEach(booking -> {
            LocalDate from = booking.getDateFrom();
            LocalDate to = booking.getDateTo();
            booked.addAll(Stream.iterate(from, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(from, to.plusDays(1)))
                    .toList());
        });

        List<LocalDate> availableDates = new ArrayList<>(localDates);
        availableDates.removeAll(booked);

        return availableDates;
    }

    public Booking mapToBooking(BookingDto dto) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setName(dto.getName());
        booking.setSurname(dto.getSurname());
        booking.setEmail(dto.getEmail());
        booking.setDateFrom(dto.getDateFrom());
        booking.setDateTo(dto.getDateTo());
        return booking;
    }
}
