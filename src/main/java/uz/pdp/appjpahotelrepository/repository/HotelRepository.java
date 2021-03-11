package uz.pdp.appjpahotelrepository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjpahotelrepository.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {

    boolean existsByHotelName(String hotelName);
}
