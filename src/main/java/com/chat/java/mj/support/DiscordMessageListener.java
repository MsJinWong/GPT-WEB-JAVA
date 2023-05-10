package com.chat.java.mj.support;

import cn.hutool.core.stream.StreamUtil;
import cn.hutool.core.text.CharSequenceUtil;

import com.chat.java.mj.enums.Action;
import com.chat.java.mj.enums.TaskStatus;
import com.chat.java.mj.service.NotifyService;
import com.chat.java.mj.util.ConvertUtils;
import com.chat.java.mj.util.MessageData;
import com.chat.java.model.SysConfig;
import com.chat.java.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscordMessageListener extends ListenerAdapter {
	private final TaskHelper taskHelper;
	private final NotifyService notifyService;

	private boolean ignoreMessage(Message message) {
		String authorName = message.getAuthor().getName();
		String channelId = message.getChannel().getId();
		SysConfig sysConfig = RedisUtil.getCacheObject("sysConfig");
		boolean isOpenMessage = !sysConfig.getMjBotName().equals(authorName) || !sysConfig.getMjChannelId().equals(channelId);
//		log.error("isOpenMessage:{}",isOpenMessage);
//		log.error("MjBotName():{}",sysConfig.getMjBotName());
//		log.error("authorName:{}",authorName);
//		log.error("sysConfig.getMjChannelId():{}",sysConfig.getMjChannelId());
//		log.error("channelId:{}",channelId);
//		log.error("!sysConfig.getMjBotName().equals(authorName):{}",!sysConfig.getMjBotName().equals(authorName));
//		log.error("!sysConfig.getMjChannelId().equals(channelId):{}",!sysConfig.getMjChannelId().equals(channelId));
		return isOpenMessage;
	}

	@Override
	public void onMessageUpdate(MessageUpdateEvent event) {
		Message message = event.getMessage();
		if (ignoreMessage(event.getMessage())) {
			return;
		}
		String content = message.getContentRaw();
		log.debug("消息变更: {}", content);
		MessageData data = ConvertUtils.matchImagineContent(content);
		if (data == null) {
			data = ConvertUtils.matchUVContent(content);
		}
		if (data == null) {
			return;
		}
		String relatedTaskId = ConvertUtils.findTaskIdByFinalPrompt(data.getPrompt());
		if (CharSequenceUtil.isBlank(relatedTaskId)) {
			return;
		}
		List<Action> uvActions = Arrays.asList(Action.UPSCALE, Action.VARIATION);
		Task task = StreamUtil.of(this.taskHelper.taskIterator())
				.filter(t -> relatedTaskId.equals(t.getRelatedTaskId())
						&& TaskStatus.NOT_START.equals(t.getStatus())
						&& uvActions.contains(t.getAction()))
				.max(Comparator.comparing(Task::getSubmitTime))
				.orElse(null);
		if (task == null) {
			return;
		}
		task.setStatus(TaskStatus.IN_PROGRESS);
		this.notifyService.notifyTaskChange(task);
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Message message = event.getMessage();
		if (ignoreMessage(event.getMessage())) {
			return;
		}
		String messageId = message.getId();
		String content = message.getContentRaw();
		log.debug("消息接收: {}", content);
		if (MessageType.SLASH_COMMAND.equals(message.getType()) || MessageType.DEFAULT.equals(message.getType())) {
			MessageData messageData = ConvertUtils.matchImagineContent(content);
			if (messageData == null) {
				return;
			}
			// imagine 命令生成的消息: 启动、完成
			String taskId = ConvertUtils.findTaskIdByFinalPrompt(messageData.getPrompt());
			Task task = this.taskHelper.getTask(taskId);
			if (task == null) {
				return;
			}
			task.setMessageId(messageId);
			if ("Waiting to start".equals(messageData.getStatus())) {
				task.setStatus(TaskStatus.IN_PROGRESS);
			} else {
				finishTask(task, message);
			}
			this.notifyService.notifyTaskChange(task);
		} else if (MessageType.INLINE_REPLY.equals(message.getType()) && message.getReferencedMessage() != null) {
			MessageData messageData = ConvertUtils.matchUVContent(content);
			if (messageData == null) {
				return;
			}
			// uv 变更图片完成后的消息
			Task task = this.taskHelper.getTask(message.getReferencedMessage().getId() + "-" + messageData.getAction());
			if (task == null) {
				return;
			}
			task.setMessageId(messageId);
			finishTask(task, message);
			this.notifyService.notifyTaskChange(task);
		}
	}

	private void finishTask(Task task, Message message) {
		task.setFinishTime(System.currentTimeMillis());
		if (!message.getAttachments().isEmpty()) {
			task.setStatus(TaskStatus.SUCCESS);
			String imageUrl = message.getAttachments().get(0).getUrl();
			task.setImageUrl(imageUrl);
			int hashStartIndex = imageUrl.lastIndexOf("_");
			task.setMessageHash(imageUrl.substring(hashStartIndex + 1).replace(".png", ""));
		} else {
			task.setStatus(TaskStatus.FAILURE);
		}
	}

}
