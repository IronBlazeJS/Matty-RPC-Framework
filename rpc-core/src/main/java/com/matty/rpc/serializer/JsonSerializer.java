package com.matty.rpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.enumeration.SerializerCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * ClassName: JsonSerializer
 * author: Matty Roslak
 * date: 2021/7/19  15:51
 */
public class JsonSerializer implements CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try{
            return objectMapper.writeValueAsBytes(obj);
        }catch (JsonProcessingException e){
            logger.error("序列化时有错误发生：{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try{
            Object obj = objectMapper.readValue(bytes, clazz);
            // 只有request类型的包需要辅助判断
            if(obj instanceof RpcRequest){
                // 辅助判断一下参数属性
                obj = handleRequest(obj);
            }
            return obj;
        }catch (IOException e){
            logger.error("反序列化时有错误发生：{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @description 由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类，需要重新判断处理
     * 因为都是Object类型，分不清
     * @param obj
     * @return [java.lang.Object]
     */
    private Object handleRequest(Object obj) throws IOException{
        RpcRequest rpcRequest = (RpcRequest) obj;
        // 循环读取所有的参数类型
        for(int i = 0; i < rpcRequest.getParamTypes().length; i++){
            Class<?> clazz = rpcRequest.getParamTypes()[i];
            // isAssignableFrom方法判断当前参数对应类型是不是与实际提取的参数类型相同，或者是其父类
            // 如果不是的话，重新序列化这个参数对象，然后根据正确的类型把它反序列化
            if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())){
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}
