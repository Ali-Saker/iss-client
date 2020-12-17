package com.iss.phase1.client;

import com.iss.phase1.client.tcp.TCPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

@Component
public class ApplicationInitializer implements CommandLineRunner {

    @Autowired
    private TCPServer tcpServer;


    public ApplicationInitializer() {

    }

    @Override
    public void run(String... args) throws IOException, ClassNotFoundException, SignatureException, InvalidKeyException {
        this.tcpServer.run("localhost", 9999);
    }

}
