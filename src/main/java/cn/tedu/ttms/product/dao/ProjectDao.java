package cn.tedu.ttms.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.ttms.product.entity.Project;
/**数据持久层接口,方法必须与ProjectMapper中的id值对应*/
public interface ProjectDao {
	/**
	 * 获得表中所有记录
	 * @param name 模糊查询时输入的项目
	 * @param valid 项目启用/禁用状态值(1,0)
	 * @param startIndex 表示从那条记录开始取数据
	 * @param pageSize 表示每页显示多少条记录
	 * @return 表示查询到的当前页的所有记录
	 * 当方法中的参数个数多于1个时,需要使用
	 * @Param 注解进行声明
	 */
	List<Project> findObjects(
			@Param("name")String name,
			@Param("valid")Integer valid,
			@Param("startIndex")int startIndex,
			@Param("pageSize")int pageSize);
	/**获得总记录数
	 * @param name 模糊查询时输入的项目
	 * @param valid 项目启用/禁用状态值(1,0)
	 * @return 总记录数
	 * */
	int getRowCount(
			@Param("name")String name,
			@Param("valid")Integer valid);
	/**插入记录*/
	int insertObject(Project entity);
	/**修改表中记录信息*/
	int updateObject(Project entity);
	/**
	 * 禁用或启用项目信息
	 * String idStr=1,2,3;
	 * String ids[]=ids.split(",");[1,2,3]
	 * @param 要修改的id的值
	 * @param 将vlid具体修改为什么值?(1,0)
	 * @return 被修改的记录的函数
	 * */
	int validById(
			@Param("ids")String[] ids,
			@Param("valid")int valid);
	
	Project findObjectById(Integer id);
	


}
