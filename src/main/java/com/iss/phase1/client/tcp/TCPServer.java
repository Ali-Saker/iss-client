package com.iss.phase1.client.tcp;

import com.iss.phase1.client.extra.CertificateAuthority;
import com.iss.phase1.client.extra.DigitalSignature;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.UUID;

@Component
public class TCPServer {

    private TCPConnection tcpConnection;

    public void run(String url, int port) throws IOException, ClassNotFoundException, SignatureException, InvalidKeyException {

        String clientID = UUID.randomUUID().toString();

        X509Certificate clientCertificate = CertificateAuthority.generateV1Certificate(DigitalSignature.getPublicKey(), clientID + ";Document 1;");

        Socket socket = new Socket(url, port);
        this.tcpConnection = new TCPConnection(socket, new ObjectInputStream(socket.getInputStream()), new ObjectOutputStream(socket.getOutputStream()),
                clientCertificate);

        this.tcpConnection.send(new TCPObject(TCPObjectType.CLIENT_CERTIFICATE, clientCertificate));
        TCPObject tcpObject = this.tcpConnection.receive();

        if(tcpObject.getType() == TCPObjectType.SERVER_CERTIFICATE) {
            X509Certificate serverCertificate = (X509Certificate) tcpObject.getObject();
            CertificateAuthority.verifyCertificate(serverCertificate);
            this.tcpConnection.setServerCertificate(serverCertificate);
        }

    }

    public TCPConnection getTcpConnection() {
        return tcpConnection;
    }
}
