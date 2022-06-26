package dev.proqa.studentmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String firstName;

    @Column(nullable = false, length = 15)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 120)
    private String password;

    @Column(nullable = false, length = 250)
    private String address;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String state;

    @Column(nullable = false, length = 5)
    private String zipCode;

    @Column(nullable = false, length = 20)
    private String country;

    @Column(nullable = false, length = 14)
    private String phoneNumber;

    @Column(nullable = false, length = 6)
    private String gender;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;


    public User(String firstName, String lastName, String email, String username, String password,
                String address, String city, String state, String zipCode, String country,
                String phoneNumber, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.gender = gender;

    }

}
