package uz.pdp.appjpahotelrepository.controller;

import org.springframework.web.bind.annotation.*;
import uz.pdp.appjpahotelrepository.entity.Hotel;
import uz.pdp.appjpahotelrepository.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    final HotelRepository hotelRepository;

    public HotelController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /* Get to all hotels */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Hotel> getHotels() {

        return hotelRepository.findAll();
    }

    /* Get a hotel by id  */
    @RequestMapping(value = "/get/{hotelId}", method = RequestMethod.GET)
    public Hotel getHotel(@PathVariable Integer hotelId) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            return optionalHotel.get();
        }
        return new Hotel();
    }

    /*Post a Hotel*/
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String addHotel(@RequestBody Hotel hotel) {

        boolean existsByHotelName = hotelRepository.existsByHotelName(hotel.getHotelName());

        if (!existsByHotelName) {
            Hotel newHotel = new Hotel();
            newHotel.setHotelName(hotel.getHotelName());
            hotelRepository.save(newHotel);
            return "Hotel saved!";
        }
        return hotel.getHotelName() + " Hotel name already exist!";
    }

    /* Edit Hotels*/
    @RequestMapping(value = "/edit/{hotelId}", method = RequestMethod.PUT)
    public String editHotel(@PathVariable Integer hotelId, @RequestBody Hotel hotel) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        boolean existsByHotelName = hotelRepository.existsByHotelName(hotel.getHotelName());

        if (!existsByHotelName) {
            if (optionalHotel.isPresent()) {
                Hotel editedHotel = optionalHotel.get();

                editedHotel.setHotelName(hotel.getHotelName());
                hotelRepository.save(editedHotel);
                return "Hotel Edited!";
            }
            return hotel.getHotelName() + " Hotel name already exist!";
        }
        return "Hotel not found!";
    }

    /*Delete hotels*/
    @RequestMapping(value = "/delete/{hotelId}", method = RequestMethod.DELETE)
    public String deleteHotel(@PathVariable Integer hotelId) {

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            hotelRepository.deleteById(hotelId);
            return "Hotel deleted!";
        }
        return "Hotel not found!";
    }
}
