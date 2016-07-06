package com.summer.jedis;

import com.summer.rest.component.JedisClient;
import org.junit.Test;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by toy on 7/4/16.
 */
public class JedisTest {
    // 单机版测试
    @Test
    public void testJedisSingle() throws Exception {
        //创建jedis obj
        Jedis jedis = new Jedis("", 6379);
        jedis.set("test", "hello jedis");
        String string = jedis.get("test");
        System.out.println(string);
        jedis.close();
    }

    @Test
    public void testJedisPool() throws Exception {
        //创建一个连接池对象
        JedisPool jedisPool = new JedisPool("172.21.14.118", 6379);
        Jedis jedis = jedisPool.getResource();
        String result = jedis.get("test");
        System.out.println(result);
        jedis.close();
        jedisPool.close();
    }

    //cluster
    @Test
    public void testJedisCluster() throws Exception {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("", 7001));
        nodes.add(new HostAndPort("", 7002));
        nodes.add(new HostAndPort("", 7003));
        nodes.add(new HostAndPort("", 7004));
        nodes.add(new HostAndPort("", 7005));
        nodes.add(new HostAndPort("", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("name", "zhandan");
        jedisCluster.set("value", "100");
        String name = jedisCluster.get("name");
        System.out.print(name);
        //系统关闭时关闭
        jedisCluster.close();
    }

    @Test
    public void testJedisClientSpring() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("client1", "1000");
        String string = jedisClient.get("client1");
        System.out.println(string);
    }
}
