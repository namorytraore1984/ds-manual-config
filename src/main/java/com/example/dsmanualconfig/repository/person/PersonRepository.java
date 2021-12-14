package com.example.dsmanualconfig.repository.person;

import com.example.dsmanualconfig.model.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
