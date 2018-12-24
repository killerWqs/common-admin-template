package com.killer.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killer.demo.modules.im.dao.Message;
import org.junit.Test;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.readValue("{\"type\":\"identity\",\"from\":\"client\",\"target\":\"se" +
                "rver\",\"content\":\"identity\"}", Message.class));
    }

}
