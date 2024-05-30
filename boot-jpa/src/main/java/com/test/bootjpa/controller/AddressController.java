package com.test.bootjpa.controller;

import com.test.bootjpa.entity.Address;
import com.test.bootjpa.repository.AddressRespository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AddressController {

    private final AddressRespository addressRespository;

    @GetMapping("/m01.do")
    public String m01(Model model){

        //[C]
        //레코드 추가하기 (insert)
        // 추가 할 레코드 정보 > 엔티티 객체를 생성하기
        Address address = Address.builder()
                .name("꿀꿀이")
                .age(5)
                .address("서울특별시 강남구 역삼동 한돈 빌딩")
                .gender("m")
                .build();

        Address result = addressRespository.save(address);

        model.addAttribute("address", Address.toDTO(result));

        return "result_dto";
    }
    @GetMapping("/m02.do")
    public String m02(Model model){

        //[R]

        //1개의 레코드만 가져오기
        Optional<Address> address = addressRespository.findById(51L);
//        addressRespository.getById(51L); 안 씀
//        addressRespository.getOne(51L); 안 씀

        if(address.isPresent()){
            model.addAttribute("address", Address.toDTO(address.get()));
        }


//        address.ifPresent(value -> model.addAttribute("address", Address.toDTO(value)));
        return "result_dto";
    }


    @GetMapping("/m03.do")
    public String m03(Model model){

        //[U]
        // 레코드 수정하기
        //1. 생성 후 수정
        //2. 검색 후 수정
//        Address address = Address.builder()
//                .seq(1L)
//                .name("꿀꿀이")
//                .age(5)
//                .address("서울특별시 강남구 역삼동 한돈 빌딩 8층")
//                .gender("m")
//                .build();


        //2번 케이스
        Address address = addressRespository.findById(1L).get();
        address.updateAddress("서울특별시 강동구",6);


        Address reultAddress = addressRespository.save(address);
        model.addAttribute("address", Address.toDTO(reultAddress));

        return "result_dto";
    }

    @GetMapping("/m04.do")
    public String m04(Model model){

    //[D] 레코드 삭제하기

//        Address address = addressRepository.findById(1L).get();

        Address address = Address.builder()
                        .seq(52L)
                        .build();


        addressRespository.delete(address);

        return "result_dto";

    }

}
