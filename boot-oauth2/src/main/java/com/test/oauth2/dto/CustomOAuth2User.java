package com.test.oauth2.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    public CustomOAuth2User(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @Override
    public Map<String, Object> getAttributes() {

        //사용 안 함
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority(){
            @Override
            public String getAuthority() {
                return userDTO.getRole();
            }
        });

        return List.of();
    }

    @Override
    public String getName() {
        return userDTO.getName();
    }

    //추가 정보
    public String getUsername(){
        return userDTO.getUsername();
    }
}
