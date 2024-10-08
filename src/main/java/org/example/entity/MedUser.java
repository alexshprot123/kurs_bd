package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "med_user")
public class MedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    public String login;
    public String password;
    public String role;
    public long targetId;
}
