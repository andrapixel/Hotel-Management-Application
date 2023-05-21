package com.sd.app.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private long bookingId;
    private long accountId;
    private int roomId;
    private Date checkinDate;
    private Date checkoutDate;
    private int noAdults;
    private int noChildren;
}
