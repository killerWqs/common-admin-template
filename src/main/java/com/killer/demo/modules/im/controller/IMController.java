package com.killer.demo.modules.im.controller;

import com.killer.demo.modules.im.dao.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description 用于前台发送信息推送到消息队列，减轻系统压力
 *
 * @author wqs
 * @date 2018-12-18 15:29
 */
@Controller
@RequestMapping("/im")
public class IMController {
    private AmqpTemplate amqpTemplate;

    @Autowired
    public IMController(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @PostMapping("/message")
    public void sendMessage(@RequestBody Message message) {
        amqpTemplate.convertAndSend("amq.direct", "message routing a", message);
    }
}
