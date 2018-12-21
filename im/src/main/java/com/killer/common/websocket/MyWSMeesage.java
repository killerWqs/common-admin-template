package com.killer.common.websocket;

/**
 * TODO...
 *
 * @author wqs
 * @date 2018-12-20 13:39
 */


//public class MyWSMeesage extends AbstractWebSocketMessage<String> {
public class MyWSMeesage{
        private String source;

    private String target;

//    @Override
    protected String toStringPayload() {
        return null;
    }

//    @Override
    public int getPayloadLength() {
        return 0;
    }
}
