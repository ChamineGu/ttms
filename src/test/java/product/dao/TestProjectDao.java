package product.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.ttms.product.dao.ProjectDao;
import cn.tedu.ttms.product.entity.Project;

public class TestProjectDao {
	private ClassPathXmlApplicationContext ctx;
	ProjectDao projectDao;
	@Before
	public void init() {
		ctx = new ClassPathXmlApplicationContext("spring-pool.xml","spring-mybatis.xml");
		projectDao = ctx.getBean("projectDao",ProjectDao.class);
	}
	@Test
	public void testFindObjects() {
		//当前页
		int pageCurrent = 1;
		//每页最多显示的记录数
		int pageSize = 4;
		String name="环球";
		int valid=1;
		int startIndex = (pageCurrent-1)*pageSize;
		//模糊查询
		List<Project> list = projectDao.findObjects(
				name,
				valid,
				startIndex,
				pageSize);
		System.out.println(list);
		Assert.assertNotEquals(null, list);
		
	}
	@Test
	public void getRowCount() {
		int rowCount = projectDao.getRowCount("环球",1);
		System.out.println("rowCount="+rowCount);
	}
	
	@Test
	public void testInsertObject() throws ParseException {
		Project entity = new Project();
		entity.setName("北京马拉松");
		entity.setCode("tt-20170802-CN-BJ-002");
		String begin = "2017/08/12";
		String end = "2017/8/19";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		entity.setBeginDate(sdf.parse(begin));
		entity.setEndDate(sdf.parse(end));
		entity.setValid(1);
		entity.setNote("北京马拉松....");
		entity.setCreatedUser("admin");
		entity.setModifiedUser("admin");
		int rows = projectDao.insertObject(entity);
		System.out.println("rows="+rows);
		Assert.assertEquals(1, rows);
	}
	
	
	@Test
	public void testValidById(){
		String[] ids={"11","12","13"};
		Integer valid=1;
		int rows=projectDao.validById(ids, valid);
		System.out.println(rows);
		Assert.assertEquals(3, rows);
	}
	
	@Test
	public void testUpdateObject(){
		Project project=projectDao.findObjectById(18);
		project.setName("雅马哈");
		int rows=projectDao.updateObject(project);
		Assert.assertEquals(1, rows);
	}
	@After
	public void destroy() {
		ctx.close();
		projectDao = null;
	}
}
