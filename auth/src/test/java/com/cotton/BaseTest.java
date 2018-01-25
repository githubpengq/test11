package com.cotton;

import com.config.component.BeanConfig;
import com.config.component.JdbcConfig;
import com.config.component.PropertyConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JdbcConfig.class, PropertyConfig.class, BeanConfig.class})
public class BaseTest {

}
