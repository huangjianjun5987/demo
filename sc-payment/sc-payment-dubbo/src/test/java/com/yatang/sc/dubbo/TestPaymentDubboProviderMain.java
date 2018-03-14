package com.yatang.sc.dubbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * 服务提供方启动类,也即入口类
 * 
 * @author : Ellison
 * @date : 2015-12-24 上午10:53:54
 * @version : 2015-12-24 Ellison 新建服务提供方启动类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestPaymentDubboProviderMain {

    /**
     * @Description：启动服务提供方
     * @return void: 返回值类型
     * @throws
     */
    @Test
    public void start() {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
