package com.matty.rpc.codec;

import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.entity.RpcResponse;
import com.matty.rpc.enumeration.PackageType;
import com.matty.rpc.enumeration.RpcError;
import com.matty.rpc.exception.RpcException;
import com.matty.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: CommonDecoder
 * author: Matty Roslak
 * date: 2021/7/19  16:34
 * 通用解码器
 */
public class CommonDecoder extends ReplayingDecoder {

    private static final Logger logger = LoggerFactory.getLogger(CommonDecoder.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //从缓冲区中读取数据
        // 最开始的一段数据是魔数
        int magic = in.readInt();
        if (magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包：{}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }

        // 第二段是包类型，根据包类型执行是request还是response，存到packageClass中
        int packageCode = in.readInt();
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            logger.error("不识别的数据包：{}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }

        // 第三段是序列化器类型 就是枚举类里面的那个code
        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if (serializer == null) {
            logger.error("不识别的反序列化器：{}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }

        // 第四段是数据长度
        int length = in.readInt();
        byte[] bytes = new byte[length];

        // 最后一段是具体数据
        in.readBytes(bytes);
        // 读完之后根据第二段数据即包裹类型进行反序列化
        Object obj = serializer.deserialize(bytes, packageClass);
        //添加到对象列表
        out.add(obj);
    }
}
