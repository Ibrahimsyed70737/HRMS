package com.hrms.hrms_backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String designation;

    private LocalDate dateOfJoining;

    // Many employees belong to ONE department.
    // This side OWNS the foreign key — the 'departments' table will get an employees list,
    // but the actual "department_id" column lives here, in the employees table.
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Self-referencing relationship: an employee's manager is also an Employee.
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
}