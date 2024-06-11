package com.test.oauth2.dto;

import java.util.Map;

public class GoogleResponse implements Oauth2Response{


    private final Map<String,Object> attribute;

    public GoogleResponse(Map<String,Object> attribute) {

        //밖에 다 나와있는 형식이라 그대로 가져오면 됨
        this.attribute = attribute;

    }


    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }
}
