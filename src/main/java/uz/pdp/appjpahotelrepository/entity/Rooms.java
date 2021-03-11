package uz.pdp.appjpahotelrepository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private String floor;

    @Column(nullable = false)
    private String size;

    @ManyToOne
    private Hotel hotel;
}
