package com.aries.aries_boot;

import okhttp3.*;

import java.io.IOException;

public class test {
    public static void main(String[] args) {

        String json = "{\"age\":2,\"name\":\"aries\"}";

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        Request request = new Request.Builder().url("http://localhost:8080/requestParamTest").post(body).build();

        Call call = new OkHttpClient().newCall(request);
        try {
            Response response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
