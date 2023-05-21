package com.sd.app.hotel.presentation;

import com.sd.app.hotel.dto.BookingDto;
import com.sd.app.hotel.model.BookingEntity;
import com.sd.app.hotel.service.BookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/hotel")
public class BookingsController {
    @Autowired
    private BookingsService bookingsService;

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingEntity>> getBookings() {
        List<BookingEntity> bookings = bookingsService.findAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/cancelled-bookings")
    public ResponseEntity<List<BookingEntity>> getCancelledBookings() {
        List<BookingEntity> bookings = bookingsService.findCancelledBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/account-bookings/{accountId}")
    public ResponseEntity<List<BookingEntity>> getAccountBookings(@PathVariable long accountId) {
        List<BookingEntity> bookings = bookingsService.getBookingsByUser(accountId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/create-booking")
    public String createBooking(@RequestBody BookingDto booking) {
        return bookingsService.createBooking(booking);
    }

    @DeleteMapping("/delete-booking/{id}")
    public String deleteBooking(@PathVariable int id) {
        return bookingsService.deleteBooking(id);
    }

    @PutMapping("/update-booking/{id}")
    public String updateBooking(@PathVariable int id, @RequestBody BookingEntity bookingEntity) {
        return bookingsService.updateBooking(id, bookingEntity);
    }

    @PatchMapping("/cancel-booking/{id}")
    public String updateRoom(@PathVariable int id) {
        return bookingsService.cancelBooking(id);
    }
}
