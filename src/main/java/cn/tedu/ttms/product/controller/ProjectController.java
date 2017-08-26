package cn.tedu.ttms.product.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.ttms.common.web.JsonResult;
import cn.tedu.ttms.product.entity.Project;
import cn.tedu.ttms.product.service.ProjectService;

@RequestMapping("/project/")
@Controller
public class ProjectController {
	@Resource
	private ProjectService projectService;
	
	@RequestMapping("listUI")
	public String listUI() {
		//此字符串返回时会交给spring视图解析器 
		// WEB-INF/pages/product/project_list.jsp
		return "product/project_list";
	}
	
	@RequestMapping("editUI")
	public String editUI() {
		return "product/project_edit";
	}
	
	@RequestMapping("doFindObjects")
	@ResponseBody//用于将返回的对象转换为json
	public JsonResult doFindObjects(
			String name,
			int valid,
			int pageCurrent) {
		System.out.println("doFindObjects().pageCurrent="+pageCurrent);
		Map<String,Object> map = 
				projectService.findObjects(name,valid,pageCurrent);
		//将获得的数据封装到JsonResult对象中
		return new JsonResult(map);
	}//输出JSON string
	//封装前[{"id":11,"name":"环球游","code":"tt-20170802-CN-BJ-001","beginDate":1502812800000,"endDate":1504108800000,"
	/*封装后:{	"state":1,
				"message":OK,
				"data":{"list":[{"id":11,"name":"环球游","code":"tt-20170802-CN-BJ-001"...}]
						"pageObject":{"pageSize":2,
				*/
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(
			String checkedIds,
			Integer valid){
		projectService.validById(checkedIds, valid);
		return new JsonResult();
		//this.message="OK"
		//this.state=SUCCESS
	}
	
	/**执行添加操作
	 * js中传入
	 * var params={"name":"A","code":"tt2012931424",....}
	 * @param entity 对象会封装页面上传入的参数
	 * 页面上的参数名字和entity对象中属性的值
	 * 一致时会spring会实现自动注入操作
	 * 即将JSON对象中的键值对自动给entity对象属性赋值
	 * 
	 * 需要获得协议中的数据:
	 * 比如请求消息头中的数据时,获得session中的值时 
	 * 使用request.getParameter
	 * */
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(Project entity){
		projectService.insertObject(entity);
		return new JsonResult();
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody//spirng提供  底层jackson实现
	public JsonResult doFindObjectById(Integer id){
		Project project=projectService.findObjectById(id);
		return new JsonResult(project);
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(
			Project entity,
			HttpServletRequest request){
		//假设有用户登录,可以从session中获得用户信息
		projectService.updateObject(entity);
		return new JsonResult();
	}
	
}
