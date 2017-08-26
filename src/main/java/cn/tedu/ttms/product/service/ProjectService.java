package cn.tedu.ttms.product.service;

import java.util.Map;

import cn.tedu.ttms.product.entity.Project;

public interface ProjectService {
	/**
	 * 获得当前页项目信息以及分页信息
	 * 1)项目信息封装到List<Project>
	 * 2)分页信息封装到PageObject
	 * 将项目信息和分页信息再次封装到map
	 * 然后做统一返回
	 * IT(挨踢)
	 * CV(ctrl+c,ctrl+v):CV战士
	 * @param pageCurrent
	 * @return
	 */
	Map<String,Object> findObjects(
			String name,
			int valid,
			int pageCurrent);
	/**启用禁用项目信息*/
	void validById(
			String idStr,
			Integer valid);
	/**向表中写入数据*/
	void insertObject(Project entity);
	/**修改记录*/
	void updateObject(Project entity);
	/**根据id查找具体对象*/
	Project findObjectById(Integer id);
}
