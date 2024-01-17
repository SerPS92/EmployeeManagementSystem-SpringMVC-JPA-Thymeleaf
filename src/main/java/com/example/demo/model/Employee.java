package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String gender;
    private String maritalStatus;
    private String phone;
    private String email;
    private String idDocument;
    private String state;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDateContract;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDateContract;
    private String department;
    private String position;
    private Double salary;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Absence> absences;

}
