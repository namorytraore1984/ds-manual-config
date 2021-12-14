package com.example.dsmanualconfig.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private String name;
}
