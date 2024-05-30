package com.test.bootjpa.repository;

import com.test.bootjpa.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;


//JpaRepository<엔티티,인티티 @Id 자료형>
public interface AddressRespository extends JpaRepository<Address, Long> {


}
