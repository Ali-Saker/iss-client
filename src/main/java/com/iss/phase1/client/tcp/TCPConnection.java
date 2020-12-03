package com.iss.phase1.client.tcp;

import java.io.*;
import java.net.Socket;
import java.security.PublicKey;

public class TCPConnection {

    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream out;
    private PublicKey serverPublicKey;
    public TCPConnection(Socket clientSocket, ObjectInputStream input, ObjectOutputStream out) {
        this.clientSocket = clientSocket;
        this.input = input;
        this.out = out;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public void send(TCPObject data) throws IOException {
        out.writeObject(data);
        out.flush();
    }

    public TCPObject receive() throws IOException, ClassNotFoundException {
        return (TCPObject) input.readObject();
    }

    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }
}
