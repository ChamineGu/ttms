package product;
import javax.sql.DataSource;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BaseTest {
	@Test
	public void testDruid(){
		
		ApplicationContext  ctx=
		new ClassPathXmlApplicationContext("spring-pool.xml");
		
		DataSource dataSource=
		(DataSource)ctx.getBean("dataSource");
	    System.out.println("dataSource="+dataSource);
		//验证DataSource是否不等于null
	    Assert.assertNotEquals(null, dataSource);
	}
	@Test
	public void testSessionFty(){
		ApplicationContext ctx=
		new ClassPathXmlApplicationContext(
				"spring-pool.xml","spring-mybatis.xml");
		Object bean=
		ctx.getBean("sqlSessionFactory");
		System.out.println(bean);
		Assert.assertNotEquals(null, bean);
	}
}
