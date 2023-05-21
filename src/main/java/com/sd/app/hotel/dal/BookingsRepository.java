package com.sd.app.hotel.dal;

import com.sd.app.hotel.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<BookingEntity, Long> {

}
