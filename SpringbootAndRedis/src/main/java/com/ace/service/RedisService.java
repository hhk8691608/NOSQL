package com.ace.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void setObject(String key, Object value) {
		this.setObject(key, value, null);
	}
	
	public void setObject(String key, Object value, Long time) {
		if (StringUtils.isEmpty(key) || value == null) {
			return;
		}
		if (value instanceof String) {
			// 存放string类型
			String stringValue = (String) value;
			if (time == null) {
				stringRedisTemplate.opsForValue().set(key, stringValue);
			} else {
				stringRedisTemplate.opsForValue().set(key, stringValue, time, TimeUnit.SECONDS);
			}

			return;
		}
		if (value instanceof List) {
			// 存放list類型
			List<String> listValue = (List<String>) value;
			for (String string : listValue) {
				stringRedisTemplate.opsForList().leftPush(key, string);
			}

		}

	}
	
	
	public void delKey(String key) {
		stringRedisTemplate.delete(key);
	}

	public String getString(String key) {
		return stringRedisTemplate.opsForValue().get(key);

	}

	
	
	
}
