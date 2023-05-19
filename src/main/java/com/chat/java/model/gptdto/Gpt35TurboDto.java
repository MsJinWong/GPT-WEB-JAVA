
package com.chat.java.model.gptdto;

import com.chat.java.model.Gpt35Model;
import com.chat.java.model.GptTurboModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@ToString
@Accessors(chain = true)
public final class Gpt35TurboDto {

    /**
     * OpenId (WeChat)
     */
//    @NotBlank(message = "code错误")
//    private String openId;

    /**
     * whether it is streaming data
     */
    private boolean isStream;

    /**
     * messageData
     */
    @NotEmpty(message = "消息数据不能为空")
    private List<Gpt35Model.Messages> messages;

    @NotNull(message = "登录人类型不能为空")
    private Integer type;

    private String logId;


    /**
     * Convert to gpt turbo model gpt turbo model.
     *
     * @param item the item
     * @return the gpt turbo model
     */
    public static Gpt35Model convertToGptTurboModel(Gpt35TurboDto item) {
        if (item == null) {
            return null;
        }
        return new Gpt35Model().setMessages(item.getMessages());
    }
}
