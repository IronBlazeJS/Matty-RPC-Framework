package com.matty.rpc.transport.socket.server;

import com.matty.rpc.handler.RequestHandler;
import com.matty.rpc.entity.RpcRequest;
import com.matty.rpc.serializer.CommonSerializer;
import com.matty.rpc.transport.socket.util.ObjectReader;
import com.matty.rpc.transport.socket.util.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * ClassName: RequestHandlerThread
 * author: Matty Roslak
 * date: 2021/7/18  21:49
 * IO传输模式|处理客户端RpcRequest的工作线程
 */
public class SocketRequestHandlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SocketRequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private CommonSerializer serializer;

    public SocketRequestHandlerThread(Socket socket, RequestHandler requestHandler, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            Object response = requestHandler.handle(rpcRequest);
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e) {
            logger.info("调用或发送时发生错误：" + e);
        }
    }
}
