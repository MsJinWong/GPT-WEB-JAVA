/**
 * @author 明明不是下雨天
 */
package com.chat.java.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;



@Data
@Accessors(chain = true)
public class Gpt35Model {


    private String model = "GPT35";

    private Double top_p = 0.9;

    private List<Messages> messages;

    private Integer max_tokens = 2048;


    @Data
    public static class Messages {
        private String role;

        private String content;
    }

}
