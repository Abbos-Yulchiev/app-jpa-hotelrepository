package uz.pdp.appjpahotelrepository.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjpahotelrepository.entity.Hotel;
import uz.pdp.appjpahotelrepository.entity.Rooms;
import uz.pdp.appjpahotelrepository.payload.RoomsDTO;
import uz.pdp.appjpahotelrepository.repository.HotelRepository;
import uz.pdp.appjpahotelrepository.repository.RoomsRepository;

import java.util.Optional;

@RestController
public class RoomsController {

    final RoomsRepository roomsRepository;
    final HotelRepository hotelRepository;

    public RoomsController(RoomsRepository roomsRepository, HotelRepository hotelRepository) {
        this.roomsRepository = roomsRepository;
        this.hotelRepository = hotelRepository;
    }

    /*Get all Rooms*/
    @RequestMapping(value = "/getRooms", method = RequestMethod.GET)
    public Page<Rooms> getRooms(@RequestParam Integer page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Rooms> roomsPage = roomsRepository.findAll(pageable);
        return roomsPage;
    }

    /*Get Rooms by Hotel ID*/
    @RequestMapping(value = "/getRoomsByHotelId/{hotelId}", method = RequestMethod.GET)
    public Page<Rooms> getRoomsByHotelId(@RequestParam("page") Integer page, @PathVariable Integer hotelId) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Rooms> roomsPage = roomsRepository.findRoomByHotel_Id(pageable, hotelId);
        return roomsPage;
    }

    /*Add Room to Hotel*/
    @RequestMapping(value = "/addRooms", method = RequestMethod.POST)
    public String addRoom(@RequestBody RoomsDTO roomsDTO) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomsDTO.getHotelID());
        boolean roomNumber = roomsRepository.existsAllByHotel_IdAndRoomNumber(roomsDTO.getHotelID(), roomsDTO.getRoomNumber());
        if (optionalHotel.isPresent()) {
            if (!roomNumber) {
                Rooms room = new Rooms();
                room.setRoomNumber(roomsDTO.getRoomNumber());
                room.setFloor(roomsDTO.getFloor());
                room.setSize(roomsDTO.getSize());
                room.setHotel(optionalHotel.get());
                roomsRepository.save(room);
                return "Room saved";
            }
            return "Room number already exist!";
        }
        return "Hotel not found";
    }

    /*Edit Hotel*/
    @RequestMapping(value = "/editRoom/{roomId}", method = RequestMethod.PUT)
    public String editRooms(@PathVariable Integer roomId, @RequestBody RoomsDTO roomsDTO) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(roomsDTO.getHotelID());
        Optional<Rooms> optionalRooms = roomsRepository.findById(roomId);
        boolean roomNumber = roomsRepository.existsAllByHotel_IdAndRoomNumber(roomsDTO.getHotelID(), roomsDTO.getRoomNumber());

        if (optionalHotel.isPresent() && optionalRooms.isPresent()) {
            if (!roomNumber) {
                Rooms editedRoom = optionalRooms.get();

                editedRoom.setFloor(roomsDTO.getFloor());
                editedRoom.setRoomNumber(roomsDTO.getRoomNumber());
                editedRoom.setHotel(optionalHotel.get());
                editedRoom.setSize(roomsDTO.getSize());

                roomsRepository.save(editedRoom);
                return "Room edited";
            }
            return "Room number already exist!";
        }
        return "Hotel or Room not found";
    }

    /*Delete room by id*/
    @RequestMapping(value = "/delete/{roomId}", method = RequestMethod.DELETE)
    public String deleteRoom(@PathVariable Integer roomId) {

        Optional<Rooms> optionalRooms = roomsRepository.findById(roomId);

        if (optionalRooms.isPresent()) {
            roomsRepository.deleteById(roomId);
            return "Room deleted!";
        }
        return "Room not found";
    }
}
