package org.example.entity;



import jakarta.validation.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "disease")
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String name;
    public String type;
}
