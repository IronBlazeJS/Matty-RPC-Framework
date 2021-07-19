package com.matty.rpc.codec;

import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.enumeration.PackageType;
import com.matty.rpc.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ClassName: CommonEncoder
 * author: Matty Roslak
 * date: 2021/7/19  16:28
 * 通用编码拦截器
 */
public class CommonEncoder extends MessageToByteEncoder {

    // 定义魔数
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    // 这个构造方法可以传入不同类型的序列化器
    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //数据写到缓冲区
        // 写魔数
        out.writeInt(MAGIC_NUMBER);
        if (msg instanceof RpcRequest) {
            // 写包类型
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        // 写序列化器类型
        out.writeInt(serializer.getCode());
        // 执行序列化
        byte[] bytes = serializer.serialize(msg);
        // 写数据长度
        out.writeInt(bytes.length);
        // 写序列化之后的数据
        out.writeBytes(bytes);
    }
}
