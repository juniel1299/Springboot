package com.test.bootjpa;

import com.test.bootjpa.entity.Address;
import com.test.bootjpa.repository.AddressRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AddressRepositoryTests {


    @Autowired
    private AddressRespository addressRespository;

    @Test
    public void test(){

        //Ctrl + Alt + v
     Optional<Address> address = addressRespository.findById(1L);


        System.out.println();

    }
}
