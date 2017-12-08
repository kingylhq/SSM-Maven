package com.lq.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * junit4测试工具类
 * @author liqian
 * 2017-12-5 14:48:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring/spring-beans.xml"})
//继承了这个类（AbstractTransactionalJUnit4SpringContextTests），数据就会回滚，我们编辑或者现在的数据就不会改变数据库的数据，反之
public class BaseDaoTestUtils /*extends AbstractTransactionalJUnit4SpringContextTests*/{

	/**
	 * 日志记录处理
	 */
	protected final Log log = LogFactory.getLog(getClass());
}
