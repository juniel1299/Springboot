package com.test.bootjpa.dto;

import com.test.bootjpa.entity.Address;
import jakarta.persistence.Transient;
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

    //JPQL 멤버
    private String city;
    private int birth;
//
//    @Transient
//    private String phone; // 컬럼에 존재하지 않기 때문에 무시하도록 transient 사용

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
