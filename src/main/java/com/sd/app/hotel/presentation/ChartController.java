package com.sd.app.hotel.presentation;

import com.sd.app.hotel.model.BookingEntity;
import com.sd.app.hotel.model.RoomEntity;
import com.sd.app.hotel.service.BookingsService;
import com.sd.app.hotel.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChartController {
    @Autowired
    private BookingsService bookingsService;
    @Autowired
    private RoomsService roomsService;

    @GetMapping("/hotel-pie-chart")
    public String displayBookingsPieChart(Model model){
        List<BookingEntity> bookings = bookingsService.findAllBookings();
        int cancelledCount = 0;
        int notCancelledCount = 0;

        for (BookingEntity booking : bookings) {
            if (booking.isCancelled()) {
                cancelledCount++;
            } else {
                notCancelledCount++;
            }
        }

        model.addAttribute("cancelledCount", cancelledCount);
        model.addAttribute("notCancelledCount", notCancelledCount);

        return "bookings-pie-chart";
    }

    @GetMapping("/hotel-bar-chart")
    public String displayBookingsBarChart(Model model) {
        List<BookingEntity> bookings = bookingsService.findAllBookings();
        List<RoomEntity> bookedRooms = new ArrayList<>();
        for (BookingEntity booking: bookings) {
            bookedRooms.add(roomsService.findByRoomNumber(booking.getRoomId()));
        }

        int singleRoomsCnt = 0;
        int twinRoomsCnt = 0;
        int doubleRoomsCnt = 0;
        int suiteRoomsCnt = 0;
        for (RoomEntity room: bookedRooms) {
            if (room.getType().equals("SINGLE"))
                singleRoomsCnt++;
            else if (room.getType().equals("TWIN"))
                twinRoomsCnt++;
            else if (room.getType().equals("DOUBLE"))
                doubleRoomsCnt++;
            else
                suiteRoomsCnt++;
        }

        model.addAttribute("singleRoomsCnt", singleRoomsCnt);
        model.addAttribute("twinRoomsCnt", twinRoomsCnt);
        model.addAttribute("doubleRoomsCnt", doubleRoomsCnt);
        model.addAttribute("suiteRoomsCnt", suiteRoomsCnt);

        return "rooms-bar-chart";
    }
}