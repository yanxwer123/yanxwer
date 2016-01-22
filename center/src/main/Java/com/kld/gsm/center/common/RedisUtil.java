package com.kld.gsm.center.common;

import org.apache.commons.collections.KeyValue;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;
import java.util.Set;

public class RedisUtil {

	private static JedisPool pool;
	private static Properties pro;

	static {
		pro = new Properties();
		try {
			pro.load(RedisUtil.class.getResourceAsStream("/redisConfig.properties"));
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(50);
			config.setMaxIdle(20);
			config.setMaxWaitMillis(50l);
			pool = new JedisPool(config, pro.getProperty("REDIS_IP"),6379,100000);
		} catch (Exception e) {
			e.getCause();
		}
	}
	
	public RedisUtil(){
		
	}
	
	public void save(String key, String value, Integer seconds) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) pool.getResource();
			jedis.set(key, value);
			if(seconds!=null){
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	public void save(String key, String value) {
		save(key,value,null);
	}

	public void delete(String... keys) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) pool.getResource();
				jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
	}
	
	public boolean update(String key, String value, Integer seconds) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) pool.getResource();
			if (jedis.exists(key)) {
				jedis.set(key, value);
				jedis.expire(key, seconds);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		return false;
	}

	public String getValue(String key) {
		Jedis jedis = null;
		try {
			jedis = (Jedis) pool.getResource();
			if (jedis.exists(key)) {
				return jedis.get(key);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	public Set getValues(String key){
		Jedis jedis = null;
		try {
			jedis = (Jedis) pool.getResource();
			/*if (jedis.exists(key)) {*/
				return jedis.keys(key);
		/*	} else {
				return null;
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 判断KEY是否存在
	 * @param key
	 * @return 存在返回true 不存在返回false
	 */
	public boolean isExists(String key) {
		boolean rebool = false;
		Jedis jedis = null;
		try {
			jedis = (Jedis) pool.getResource();
			rebool = jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.returnResource(jedis);
		}
		return rebool;
	}

	public Jedis getJedis() {
		Jedis jedis = null;
		// 捕捉异常
		try {
			jedis = (Jedis) pool.getResource();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("获取Jedis连接失败!:");

		}
		return jedis;
	}
}
