package com.zhangguo.spring.dao;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public abstract class MybatisUtil {
	
	private static SqlSessionFactory factory=null;

	public static SqlSessionFactory getSqlSessionFactory(){
        if(factory==null){
        	InputStream config = MybatisUtil.class.getClassLoader().getResourceAsStream("MyBatisCfg.xml");
        factory = new SqlSessionFactoryBuilder().build(config);
        }
        return factory;
    }
	  public static SqlSession getSession(){
	        return getSqlSessionFactory().openSession(true);
	    }
	  
	  public static SqlSession getSession(boolean isAutoCommit){
	        return getSqlSessionFactory().openSession(isAutoCommit);
	    }




}
