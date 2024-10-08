package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "profession")
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
}
