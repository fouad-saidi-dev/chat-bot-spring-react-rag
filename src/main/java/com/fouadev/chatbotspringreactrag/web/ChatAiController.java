package com.fouadev.chatbotspringreactrag.web;

import com.fouadev.chatbotspringreactrag.services.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

/*
 Created by : Fouad SAIDI on 13/07/2024
 @author : Fouad SAIDI
 @date : 13/07/2024
 @project : Chat-Bot-Spring-React -RAG
*/
@RestController
@RequestMapping("/chat-ai")
public class ChatAiController{
    private ChatService chatService;

    public ChatAiController(ChatService chatService) {
        this.chatService = chatService;
    }
    @GetMapping(value = "/ask",produces = MediaType.TEXT_PLAIN_VALUE)
    public String regChat(String question) {
        return chatService.regChat(question);
    }
}