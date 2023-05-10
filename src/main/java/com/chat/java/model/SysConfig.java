package com.chat.java.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chat.java.model.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_config")
public class SysConfig extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 326308725675949330L;

    @ApiModelProperty(value = "注册模式 1账号密码  2 短信注册 3 关闭注册")
    private Integer registrationMethod;

    @ApiModelProperty(value = "是否禁用自动禁用key 0关闭 1开启")
    private Integer keySwitch;

    @ApiModelProperty(value = "默认注册次数")
    private Integer defaultTimes;

    @ApiModelProperty(value = "阿里云accessKeyId")
    private String aliAccessKeyId;

    @ApiModelProperty(value = "阿里云secret")
    private String aliSecret;

    @ApiModelProperty(value = "阿里云短信签名")
    private String aliSignName;

    @ApiModelProperty(value = "阿里云短信模版id")
    private String aliTemplateCode;

    @ApiModelProperty(value = "图片上传路径")
    private String imgUploadUrl;

    @ApiModelProperty(value = "图片返回前缀地址")
    private String imgReturnUrl;

    @ApiModelProperty(value = "sd接口地址")
    private String sdUrl;

    @ApiModelProperty(value = "是否开启sd 0未开启 1开启")
    private Integer isOpenSd;

    @ApiModelProperty(value = "是否开启代理 0未开启 1开启")
    private Integer isOpenProxy;

    @ApiModelProperty(value = "代理ip")
    private String proxyIp;

    @ApiModelProperty(value = "代理端口")
    private Integer proxyPort;

    @ApiModelProperty(value = "微软bing cookie")
    private String bingCookie;

    @ApiModelProperty(value = "是否开启bing 0-未开启 1开启")
    private Integer isOpenBing;


    @ApiModelProperty(value = "是否开启FlagStudio 0-未开启 1开启")
    private Integer isOpenFlagStudio;

    @ApiModelProperty(value = "FlagStudio key")
    private String flagStudioKey;

    @ApiModelProperty(value = "FlagStudio接口地址")
    private String flagStudioUrl;


    @ApiModelProperty(value = "百度appid")
    private String baiduAppid;

    @ApiModelProperty(value = "百度Secret")
    private String baiduSecret;

    @ApiModelProperty(value = "discord服务器id")
    private String mjGuildId;

    @ApiModelProperty(value = "discord频道id")
    private String mjChannelId;

    @ApiModelProperty(value = "discord用户Token")
    private String mjUserToken;

    @ApiModelProperty(value = "机器人token")
    private String mjBotToken;

    @ApiModelProperty(value = "机器人名称")
    private String mjBotName;

    @ApiModelProperty(value = "任务状态变更回调地址")
    private String mjNotifyHook;

    @ApiModelProperty(value = "百度应用key")
    private String baiduKey;

    @ApiModelProperty(value = "百度应用Secret")
    private String baiduSecretKey;

    @ApiModelProperty(value = "是否开启Mj 0-未开启 1开启")
    private Integer isOpenMj;


}
