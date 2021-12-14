package com.example.dsmanualconfig;

import com.example.dsmanualconfig.model.product.Product;
import com.example.dsmanualconfig.model.user.Person;
import com.example.dsmanualconfig.repository.product.ProductRepository;
import com.example.dsmanualconfig.repository.user.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@SpringBootApplication
public class DsManualConfigApplication implements CommandLineRunner {

    private final ProductRepository repository;
    private final PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(DsManualConfigApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("*************************** start ***************************");
        System.out.println("********* start add product *******");
        repository.save(Product.builder().name("Phone " + LocalDateTime.now()).build());
        System.out.println("********* end add product *******");
        System.out.println("********* start add person *******");
        personRepository.save(Person.builder().name("Namory " + LocalDateTime.now()).build());
        System.out.println("********* end add person *******");

        personRepository.findAll().forEach(record -> {
            System.out.println("ID : " + record.getId());
            System.out.println("NOM : " + record.getName());
        });
        System.out.println("*************************** end ***************************");
    }
}
