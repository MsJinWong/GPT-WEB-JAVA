
package com.chat.java.model.gptdto;

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
public final class GptTurboDto {

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
    private List<GptTurboModel.Messages> messages;

    @NotNull(message = "登录人类型不能为空")
    private Integer type;

    private String logId;


    /**
     * Convert to gpt turbo model gpt turbo model.
     *
     * @param item the item
     * @return the gpt turbo model
     */
    public static GptTurboModel convertToGptTurboModel(GptTurboDto item) {
        if (item == null) {
            return null;
        }
        return new GptTurboModel().setMessages(item.getMessages());
    }
}
