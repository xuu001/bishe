package xyz.hhang.boot.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseDataBuilder {
    private Map<String,Object> result;

    public ResponseDataBuilder(){
        result = new HashMap<>();
    }
    public ResponseDataBuilder success(){
        result.put("status",1);
        return this;
    }

    public ResponseDataBuilder error(){
        result.put("status",0);
        return this;
    }

    public  ResponseDataBuilder message(String message){
        result.put("message",message);
        return this;
    }

    public ResponseDataBuilder data(Object data){
        result.put("data",data);
        return this;
    }
    public Map<String,Object> build(){
        return result;
    }
}
