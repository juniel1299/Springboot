package com.test.bootthymeleaf.mapper;

import com.test.bootthymeleaf.dto.AddressDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
    String time();

    int num();

    String txt();

    AddressDTO get();
}
