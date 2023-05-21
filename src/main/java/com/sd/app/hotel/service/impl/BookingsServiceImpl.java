package com.sd.app.hotel.service.impl;

import com.sd.app.hotel.dal.BookingsRepository;
import com.sd.app.hotel.dto.BookingDto;
import com.sd.app.hotel.model.BookingEntity;
import com.sd.app.hotel.model.RoomEntity;
import com.sd.app.hotel.service.BookingsService;
import com.sd.app.hotel.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BookingsServiceImpl implements BookingsService {
    @Autowired
    private BookingsRepository bookingsRepository;
    @Autowired
    private RoomsService roomsService;

    @Override
    public List<BookingEntity> findAllBookings() {
        return bookingsRepository.findAll();
    }

    @Override
    public List<BookingEntity> findCancelledBookings() {
        List<BookingEntity> cancelledBookings = new ArrayList<>();
        for (BookingEntity bookingEntity: findAllBookings()) {
            if (bookingEntity.isCancelled())
                cancelledBookings.add(bookingEntity);
        }

        return cancelledBookings;
    }

    @Override
    public List<BookingEntity> getBookingsByUser(long accountId) {
        List<BookingEntity> bookings = new ArrayList<>();

        for (BookingEntity booking: findAllBookings()) {
            if (booking.getAccountId() == accountId) {
                bookings.add(booking);
            }
        }

        return bookings;
    }

    @Override
    public BookingEntity findById(long id) {
        Optional<BookingEntity> foundBooking = bookingsRepository.findById(id);

        if (foundBooking.isEmpty()) {
            try {
                throw new Exception("Booking with ID " + id + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return foundBooking.get();
    }

    @Override
    public String createBooking(BookingDto bookingDto) {
        BookingEntity newBooking = new BookingEntity();
        newBooking.setBookingId(bookingDto.getBookingId());
        newBooking.setAccountId(bookingDto.getAccountId());
        newBooking.setRoomId(bookingDto.getRoomId());
        newBooking.setCheckinDate(bookingDto.getCheckinDate());
        newBooking.setCheckoutDate(bookingDto.getCheckoutDate());
        newBooking.setNoAdults(bookingDto.getNoAdults());
        newBooking.setNoChildren(bookingDto.getNoChildren());
        newBooking.setPrice(computeBookingPrice(bookingDto));
        newBooking.setCancelled(false);

        bookingsRepository.save(newBooking);
        roomsService.changeRoomAvailability(bookingDto.getRoomId(), false);
        return "Booking created successfully.";
    }

    @Override
    public String cancelBooking(long id) {
        BookingEntity booking = findById(id);
        booking.setCancelled(true);
        bookingsRepository.save(booking);
        roomsService.changeRoomAvailability(booking.getRoomId(), true);
        return "Booking cancelled successfully.";
    }

    @Override
    public String deleteBooking(long id) {
        if (id == 0 || id < 0) {
            throw new InvalidParameterException("Invalid ID.\n");
        }

        Optional<BookingEntity> optional = bookingsRepository.findById(id);
        if (optional.isEmpty()) {
            try {
                throw new Exception("Booking with ID " + id + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        roomsService.changeRoomAvailability(optional.get().getRoomId(), true);
        bookingsRepository.deleteById(id);
        return "Booking deleted successfully.";
    }

    @Override
    public String updateBooking(long id, BookingEntity givenBooking) {
        Optional<BookingEntity> optional = bookingsRepository.findById(id);

        if (optional.isEmpty()) {
            try {
                throw new Exception("Booking with ID " + id + " could not be found.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        BookingEntity updatedBooking = new BookingEntity(optional.get().getBookingId(), givenBooking.getAccountId(),
        givenBooking.getRoomId(), givenBooking.getCheckinDate(), givenBooking.getCheckoutDate(), givenBooking.getNoAdults(),
        givenBooking.getNoChildren(), givenBooking.getPrice(), givenBooking.isCancelled());

        bookingsRepository.save(updatedBooking);
        return "Booking details updated successfully.";
    }

    private float computeBookingPrice(BookingDto bookingDto) {
        float totalPrice = 0.0f;
        RoomEntity selectedRoom = roomsService.findByRoomNumber(bookingDto.getRoomId());
        int nrNights = computeNumNights(bookingDto.getCheckinDate(), bookingDto.getCheckoutDate());

        switch (selectedRoom.getType()) {
            case "SINGLE":
                totalPrice = nrNights * 100.0f;
                break;
            case "TWIN":
                totalPrice = nrNights * 150.0f;
                break;
            case "DOUBLE":
                totalPrice = nrNights * 200.0f;
                break;
            case "SUITE":
                totalPrice = nrNights * 400.0f;
                break;
            default:    // invalid room type
                return 0.0f;
        }

        totalPrice += (bookingDto.getNoAdults() * 35.0f) + (bookingDto.getNoChildren() * 15.0f);

        return totalPrice;
    }

    private int computeNumNights(Date checkinDate, Date checkoutDate) {
        long diffInMillies = Math.abs(checkoutDate.getTime() - checkinDate.getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (int) diffInDays;
    }
}
