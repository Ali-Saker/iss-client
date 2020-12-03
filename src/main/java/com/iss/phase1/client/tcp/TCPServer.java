package com.iss.phase1.client.tcp;

import com.iss.phase1.client.extra.AES;
import com.iss.phase1.client.extra.RSA;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.security.PublicKey;

@Component
public class TCPServer {

    private TCPConnection tcpConnection;

    public void run(String url, int port) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(url, port);
        this.tcpConnection = new TCPConnection(socket, new ObjectInputStream(socket.getInputStream()), new ObjectOutputStream(socket.getOutputStream()));
        RSA.init();
        this.tcpConnection.send(new TCPObject(TCPObjectType.PUBLIC_KEY, RSA.getPublicKey()));
        TCPObject tcpObject = this.tcpConnection.receive();

        if(tcpObject.getType() == TCPObjectType.PUBLIC_KEY) {
            this.tcpConnection.setServerPublicKey((PublicKey) tcpObject.getObject());
            this.tcpConnection.send(new TCPObject(TCPObjectType.SESSION_KEY, RSA.encrypt(AES.issSecretKey.getBytes(), this.tcpConnection.getServerPublicKey())));
        }

    }

    public TCPConnection getTcpConnection() {
        return tcpConnection;
    }
}
