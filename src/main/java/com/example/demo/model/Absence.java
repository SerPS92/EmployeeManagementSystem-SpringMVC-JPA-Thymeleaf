package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "absences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDay;
    private String reason;
    private boolean justify;

    @ManyToOne
    private Employee employee;
}
