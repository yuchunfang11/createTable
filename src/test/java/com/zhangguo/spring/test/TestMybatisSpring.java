package com.zhangguo.spring.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhangguo.spring.entities.BookType;
import com.zhangguo.spring.mapping.BookTypeDAO;

public class TestMybatisSpring {
	
	@Test
	public void test(){
		  //初始化容器
		 ApplicationContext ctx=new ClassPathXmlApplicationContext("ApplicationContext.xml");
		 
		  //获得bean
	        BookTypeDAO bookTypeDao=ctx.getBean("bookTypeDAO",BookTypeDAO.class);

	        //访问数据库
	        List<BookType> booktypes=bookTypeDao.getAllBookTypes();
	        for (BookType bookType : booktypes) {
	            System.out.println(bookType);
	        }


	}

}
