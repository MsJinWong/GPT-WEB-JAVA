package com.chat.java.flow.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPT35RequestParameter {
    private String model = "GPT35";

    private List<ChatMessage> messages = new ArrayList<>();

    private boolean stream = true;

     public void addMessages(ChatMessage message) {
    	if(messages.size()<10) {
    		this.messages.add(message);
    	}else {
    		this.messages.remove(0);
    		this.messages.add(message);
    	}
    }
}
