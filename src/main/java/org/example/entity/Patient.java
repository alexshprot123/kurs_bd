package org.example.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    public String gender;
    public LocalDate birthdate;
    public String phone;
    public String snils;
    @ManyToOne
    public Doctor doctor;
    @ManyToOne
    public Disease disease;
}
