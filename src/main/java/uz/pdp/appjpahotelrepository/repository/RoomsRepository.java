package uz.pdp.appjpahotelrepository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjpahotelrepository.entity.Rooms;


import java.util.List;

public interface RoomsRepository extends JpaRepository<Rooms, Integer> {

    List<Rooms> findRoomByHotel_Id(Integer hotel_id);
    boolean existsAllByHotel_IdAndRoomNumber(Integer hotel_id, String roomNumber);
}
