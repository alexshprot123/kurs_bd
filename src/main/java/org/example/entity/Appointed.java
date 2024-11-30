package org.example.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointed")
public class Appointed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @ManyToOne
    public Doctor doctor;
    @ManyToOne(cascade = CascadeType.ALL)
    public Patient patient;
    @Column(nullable = false)
    public LocalDate date;
}
