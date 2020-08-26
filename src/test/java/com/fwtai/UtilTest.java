package com.fwtai;

import com.fwtai.dao.DaoBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;

/**
 * 测试单元,注意，web项目运行时使用单元测试会报错
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年12月2日 17:21:45
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"file:src/main/resources/spring/spring-config.xml","file:src/main/resources/config/mybatis-config.xml", "file:src/main/resources/spring/spring-mvc.xml" })
public class UtilTest {

    @Autowired
    private DaoBase dao;

    @Before
    public void before(){
        System.out.println("准备测试!");
    }

    @After
    public void After(){
        System.out.println("测试结束!");
    }

    @Test
    public void queryTotal(){
        try {
            Integer total = dao.queryForInteger("sys_role.listTotal",new HashMap<String,Object>());
            System.out.println(total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}