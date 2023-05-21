package com.sd.app.hotel.service;

import com.sd.app.hotel.dto.BookingDto;
import com.sd.app.hotel.model.BookingEntity;

import java.util.List;

public interface BookingsService {
    List<BookingEntity> findAllBookings();
    List<BookingEntity> findCancelledBookings();
    List<BookingEntity> getBookingsByUser(long accountId);
    BookingEntity findById(long id);
    String createBooking(BookingDto bookingDto);
    String cancelBooking(long id);
    String deleteBooking(long id);
    String updateBooking(long id, BookingEntity givenBooking);
}
