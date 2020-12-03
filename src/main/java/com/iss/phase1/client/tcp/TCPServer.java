package com.iss.phase1.client.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@Component
public class TCPServer {

    @Autowired
    private AsyncService asyncService;

    private TCPConnection tcpConnection;

    public void run(String url, int port) throws IOException {
        Socket socket = new Socket(url, port);
        this.tcpConnection = new TCPConnection(socket, new ObjectInputStream(socket.getInputStream()), new ObjectOutputStream(socket.getOutputStream()));
    }

    public TCPConnection getTcpConnection() {
        return tcpConnection;
    }
}
