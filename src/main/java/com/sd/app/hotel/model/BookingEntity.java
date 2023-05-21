package com.sd.app.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long bookingId;

    @Column
    private long accountId;

    @Column
    private int roomId;

    @Column
    private Date checkinDate;

    @Column
    private Date checkoutDate;

    @Column
    private int noAdults;

    @Column
    private int noChildren;

    @Column
    private float price;

    @Column
    private boolean cancelled;
}
