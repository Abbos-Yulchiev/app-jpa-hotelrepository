package uz.pdp.appjpahotelrepository.payload;

import lombok.Data;

@Data
public class RoomsDTO {

    private String roomNumber;
    private String floor;
    private String size;
    private Integer hotelID;
}
