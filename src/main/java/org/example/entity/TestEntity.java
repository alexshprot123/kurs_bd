package org.example.entity;

import javax.persistence.*;

@Entity
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @ManyToOne(cascade = CascadeType.ALL)
    public Patient patient;
}
