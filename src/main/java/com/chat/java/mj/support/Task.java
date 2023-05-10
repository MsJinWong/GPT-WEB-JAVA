package com.chat.java.mj.support;


import com.chat.java.mj.enums.Action;
import com.chat.java.mj.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Task {

	private Action action;
	private String id;
	private String prompt;

	private String description;
	private String state;
	private Long submitTime;
	private Long finishTime;
	private String imageUrl;
	private TaskStatus status = TaskStatus.NOT_START;
	private String notifyHook;

	@JsonIgnore
	private String finalPrompt;
	@JsonIgnore
	private String relatedTaskId;
	@JsonIgnore
	private String messageId;
	@JsonIgnore
	private String messageHash;
}
