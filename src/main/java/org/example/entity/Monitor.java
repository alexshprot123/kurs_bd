package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "monitor")
public class Monitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @ManyToOne
    public Disease disease;
    @ManyToOne
    public Profession profession;
}
