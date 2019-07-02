package com.ace.pool;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class TestJedisPool {
	
	private JedisPool pool;
	
	@Before
	public void init() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(10);
		pool = new JedisPool(jedisPoolConfig,"120.25.193.143");
		System.out.println("连接池初始化成功");
		
	}
	
	@Test
	public void testPing() {
		
		try(Jedis jedis = pool.getResource()){
			 //查看服务是否运行 PING
            System.out.println("服务正在运行: "+jedis.ping());
		}
		
	}
	
	@Test
	public void testList() {
		
		try(Jedis jedis = pool.getResource()){
			
			jedis.select(3);
			jedis.lpush("people", "Ace");
			jedis.lpush("people", "Arvin");
			jedis.lpush("people", "Lily");
			
			List<String> list = jedis.lrange("people", 0, 2);
            for (int i = 0; i < list.size(); i++) {
                System.out.println("people 列表项为: " + list.get(i));
            }
			
			
		}
	}
	
	  /**
     * 程序关闭时，需要调用关闭方法
     */
    @After
    public void end(){      
        if(null != pool){
            pool.destroy();
            System.out.println("连接池关闭");
        }

    }
	

}
