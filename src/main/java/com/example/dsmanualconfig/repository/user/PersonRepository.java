package com.example.dsmanualconfig.repository.user;

import com.example.dsmanualconfig.model.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
