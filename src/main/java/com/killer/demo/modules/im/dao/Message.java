package com.killer.demo.modules.im.dao;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-18 15:31
 */
public class Message {
    private String type;

    private String from;

    private String target;

    private String content;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", from='" + from + '\'' +
                ", target='" + target + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
