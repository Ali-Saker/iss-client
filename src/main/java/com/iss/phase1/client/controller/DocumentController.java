package com.iss.phase1.client.controller;

import com.iss.phase1.client.entity.Document;
import com.iss.phase1.client.entity.DocumentRequest;
import com.iss.phase1.client.entity.DocumentResponse;
import com.iss.phase1.client.extra.ActionType;
import com.iss.phase1.client.tcp.AsyncService;
import com.iss.phase1.client.tcp.TCPObject;
import com.iss.phase1.client.tcp.TCPObjectType;
import com.iss.phase1.client.tcp.TCPServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.security.PublicKey;

@Controller
@RequestMapping("/")
public class DocumentController {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private TCPServer tcpServer;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("document", new DocumentRequest());
        return "index";
    }

    @RequestMapping("/fetch")
    public String fetch(@ModelAttribute DocumentRequest request, Model model) throws IOException, ClassNotFoundException {
        request.setActionType(ActionType.FETCH);
        request.signName();
        asyncService.send(tcpServer.getTcpConnection(), new TCPObject(TCPObjectType.DOCUMENT, request));
        PublicKey serverPublicKey = tcpServer.getTcpConnection().getServerPublicKey();

        TCPObject response = tcpServer.getTcpConnection().receive();

        DocumentResponse documentResponse = ((DocumentResponse) response.getObject())
                .verifyName(serverPublicKey).verifyContent(serverPublicKey);

        if(response.getType() == TCPObjectType.UNAUTHORIZED_EDIT) {
            model.addAttribute("enableSave", false);
        }

        model.addAttribute("enableSave", !(response.getType() == TCPObjectType.UNAUTHORIZED_EDIT ||
                response.getType() == TCPObjectType.UNAUTHORIZED_READ));

        model.addAttribute("document", documentResponse);

        return "fetchResult";
    }

    @RequestMapping("/save")
    public String fetch(@ModelAttribute DocumentResponse document, Model model) throws IOException, ClassNotFoundException {
        DocumentRequest request = new DocumentRequest(document.getName(), ActionType.EDIT, document.getContent(), tcpServer.getTcpConnection().getClientCertificate())
                .signName().signContent();
        asyncService.send(tcpServer.getTcpConnection(), new TCPObject(TCPObjectType.DOCUMENT, request));
        PublicKey serverPublicKey = tcpServer.getTcpConnection().getServerPublicKey();
        DocumentResponse response = asyncService.receiveDocument(tcpServer.getTcpConnection())
                .verifyName(serverPublicKey).verifyContent(serverPublicKey);
        model.addAttribute("document", response);
        return "fetchResult";
    }
}
