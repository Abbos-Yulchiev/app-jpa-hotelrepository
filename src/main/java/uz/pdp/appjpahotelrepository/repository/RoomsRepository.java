package uz.pdp.appjpahotelrepository.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjpahotelrepository.entity.Rooms;

public interface RoomsRepository extends JpaRepository<Rooms, Integer> {

    Page<Rooms> findRoomByHotel_Id(Pageable pageable, Integer hotel_id);
    boolean existsAllByHotel_IdAndRoomNumber(Integer hotel_id, String roomNumber);
}
