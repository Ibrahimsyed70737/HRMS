package com.hrms.hrms_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // One department has many employees.
    // mappedBy = "department" means: "the foreign key is owned by the Employee side,
    // look at the 'department' field in Employee to find the relationship."
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}