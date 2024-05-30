package com.test.bootjpa.dto;

import com.test.bootjpa.entity.Address;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long seq;
    private String name;
    private Integer age;
    private String address;
    private String gender;

    public static Address toEntity(AddressDTO dto) {
        //DTO > Entity

        return Address.builder()
                .seq(dto.getSeq())
                .name(dto.getName())
                .age(dto.getAge())
                .address(dto.getAddress())
                .gender(dto.getGender())
                .build();
    }
}
