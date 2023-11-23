/**
 * Copyright © 2022 WXFGGZS. All Rights Reserved.
 */

package com.my.app.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.util.DigestUtils;

@Slf4j
public class MybatisPlusRedisCache implements Cache {
	/**
	 * 注意，这里无法通过@Autowired等注解的方式注入bean,只能手动获取
	 */
	private RedisUtils redisUtils;
	/**
	 * 缓存刷新间隔，单位为毫秒 flushInterval 参数(自定义cache无法使用默认的flushInterval)
	 */
	@Setter
	private long flushInterval = 60000L;

	/**
	 * 手动获取bean
	 *
	 * @return
	 */
	private void getRedisUtil() {
		redisUtils = (RedisUtils) ApplicationContextUtils.getBean("RedisUtils");
	}

	// 读写锁
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

	private final String id;

	public MybatisPlusRedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void putObject(Object key, Object value) {
		if (redisUtils == null) {
			getRedisUtil(); // 获取bean
		}
		try {
			// 将key加密后存入
			redisUtils.hset(this.id, this.MD5Encrypt(key), value);
			// 有效时间
			redisUtils.expire(this.id, flushInterval);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getObject(Object key) {
		if (redisUtils == null) {
			getRedisUtil(); // 获取bean
		}
		try {
			if (key != null) {
				return redisUtils.hget(this.id, this.MD5Encrypt(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object removeObject(Object key) {
		if (redisUtils == null) {
			getRedisUtil(); // 获取bean
		}
		try {
			if (key != null) {
				redisUtils.del(this.MD5Encrypt(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void clear() {
		if (redisUtils == null) {
			getRedisUtil(); // 获取bean
		}
		try {
			redisUtils.del(this.id.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getSize() {
		if (redisUtils == null) {
			getRedisUtil(); // 获取bean
		}
		Long size = (Long) redisUtils.execute((RedisCallback<Long>) RedisServerCommands::dbSize);
		return size.intValue();
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

	/**
	 * MD5加密存储key,以节约内存
	 */
	private String MD5Encrypt(Object key) {
		return DigestUtils.md5DigestAsHex(key.toString().getBytes());
	}
}
