package com.iss.phase1.client.tcp;

import com.iss.phase1.client.entity.DocumentResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AsyncService {

    @Async
    public void send(TCPConnection connection, TCPObject data) throws IOException {
        connection.send(data);
    }

    public DocumentResponse receiveDocument(TCPConnection connection) throws IOException, ClassNotFoundException {
        TCPObject data = (TCPObject)connection.receive();
        return (DocumentResponse) data.getObject();
    }
}
