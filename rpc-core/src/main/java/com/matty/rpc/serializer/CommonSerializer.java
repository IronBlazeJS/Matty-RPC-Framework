package com.matty.rpc.serializer;


/**
 * ClassName: CommonSerializer
 * author: Matty Roslak
 * date: 2021/7/19  15:46
 * 通用的序列化、反序列化接口
 */
public interface CommonSerializer {

    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;
    Integer HESSIAN_SERIALIZER = 2;
    Integer PROTOBUF_SERIALIZER = 3;

    Integer DEFAULT_SERIALIZER = KRYO_SERIALIZER;

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            case 3:
                return new ProtostuffSerializer();
            default:
                return null;
        }
    }

}
