package io.geekidea.springbootplus.test;

import io.geekidea.springbootplus.SpringBootPlusApplication;
import io.geekidea.springbootplus.system.service.MqSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: parent
 * @description: mq发送测试
 * @author: hhx
 * @create: 2020-06-24 10:38
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootPlusApplication.class)
public class SendServiceTest {

    @Autowired
    private MqSendService sendService;

    @Test
    public void testCache(){
    for (int i = 0; i < 10; i++) {
        sendService.direct("21");
    }

    }
}
