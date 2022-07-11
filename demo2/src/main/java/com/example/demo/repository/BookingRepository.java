package com.example.demo.repository;

import com.example.demo.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findAllByDateFromGreaterThanEqualAndDateToLessThanEqual(LocalDate dateFrom, LocalDate dateTo);
}
