package org.example.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    public String gender;
    public LocalDate birthdate;
    public String certificate;
    public String phone;
    public LocalDate startWork;
    @ManyToOne
    public Profession profession;
}
