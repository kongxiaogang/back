package com.kongxiaogang.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kongxiaogang.model.UserModel;
import com.kongxiaogang.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath*:spring.xml","classpath*:spring-mybatis.xml"})
public class TestMybatis {
	//private ApplicationContext ac;
	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		//ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring.xml","classpath*:spring-mybatis.xml");
		//UserService userService = (UserService) ac.getBean("userService");
		UserModel user = userService.getUserByID("1");
		System.out.println(user);
	}
}
