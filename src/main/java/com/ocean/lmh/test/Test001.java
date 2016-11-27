package com.ocean.lmh.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ocean.lmh.base.util.HttpClientUtils;

//告诉framework怎么运行这个类
@RunWith(SpringJUnit4ClassRunner.class)
// bean的配置文件路径，这个是Test类的classpath路径，如果是Spring推荐的目录结构，应该在：项目目录/src/test/resources/里
@ContextConfiguration(locations = { "classpath:servlet-context.xml",
        "classpath:root-context.xml" })
public class Test001 extends AbstractTransactionalJUnit4SpringContextTests
{
    //private MockHttpServletRequest request;

    @Before
    public void setUp() throws Exception
    {
        //request = new MockHttpServletRequest();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * 测试方法
     */
    @Test
    public void testMonthTable()
    {

    }
    
    public static void main(String[] args) {
    	
		for (int i=0; i<10; i++) {
			new Thread(new MyRunnable("test"+1)).start();
		}
    }
}
class MyRunnable implements Runnable{
	String name;
	public MyRunnable(String name) {
		this.name = name;
	}
	public void run() {
		HttpClientUtils.post2("http://61.191.45.194:8282/mobile_anhui/index.do", name);
	}
}
