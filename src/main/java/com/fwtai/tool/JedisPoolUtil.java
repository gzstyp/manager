package com.fwtai.tool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis操作类
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2018-03-17 21:54
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class JedisPoolUtil {

    private static volatile JedisPool jedisPool = null;

    private JedisPoolUtil(){}

    public static JedisPool getJedisPoolInstance(){
        if(jedisPool == null){
            synchronized (JedisPoolUtil.class){
                if(jedisPool == null){
                    final JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(1000);
                    config.setMaxIdle(32);
                    config.setMaxWaitMillis(100*1000L);
                    config.setTestOnBorrow(true);
                    jedisPool = new JedisPool(config,"192.168.3.140",6379);
                }
            }
        }
        return jedisPool;
    }

    /**关闭*/
    public static void close(final Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }

    public static void main(String[] args){
        final JedisPool jedisPool = getJedisPoolInstance();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set("age","35");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(jedis);
        }
    }
}