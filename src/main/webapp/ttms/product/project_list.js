$(document).ready(function() {//启动时加载
	//在queryFormId对应对象的btn-search元素上注册click事件
	$("#queryFormId").on("click",".btn-search",doQueryObjects);
	//在禁用和启用按钮上注册点击事件
	$("#queryFormId").on("click",".btn-valid,.btn-invalid",doValidById);
	//在添加按钮上注册点击事件
	$("#queryFormId").on("click",".btn-add,.btn-update",doShowEditDialog);
	doGetObjects();
});
/**
 * 点击添加按钮时执行一个动作
 * 1)初始化index页面模态框(bootstrap 框架提供 ,内部定义了一些属性,来构建)
 * 比如:ctrl+n eclipse新建窗口就是一个类似的模态框
 * 2)在模态框内部显示project_edit.jsp 
 * */
function doShowEditDialog(){
	var title;
	//1.定义模态框的标题
	if($(this).hasClass("btn-add")){
		title="添加项目";
		
	}
	if($(this).hasClass("btn-update")){
		//模态框上绑定id值(
		//在修改页面中要根据此值获取查找对象)
		//data(key,value)用于绑定数据
		//data(key)函数用于获取数据
		$("#modal-dialog").data("id",
				$(this).parent().parent().data("id"));
		title="修改项目,id为"
			+$("#modal-dialog").data("id");
	}
	
	//2.启动模态框,并加载页面
	//在模态框对应位置异步加载url
	var url="project/editUI.do";
	$("#modal-dialog .modal-body").load(url,
			function(){//异步加载完成回调此函数
		//class选择器,设置标题内容
		$(".modal-title").html(title);
		//显示模态框,modal方法,隐藏hide
		$("#modal-dialog").modal("show");
	});
}

/**执行禁用启用操作
 * 1.获得数据(禁用或启用那些项目信息)
 * a)id(选中的那个checkbox值
 * b)valid(由点击按钮来绝对)
 * 2.发生异步请求,修改记录信息
 * a)url
 * b)params
 * c)post(url,params,functions(result){})
 * 
 * */
function doValidById(){
	//debugger
	//1.获得页面数据(valid,checkedId)
	var valid;//定义一个变量(默认值undefined)
	//判定点击的按钮时启用还是禁用
	//hasClass用于判定对象上是否有某个选择器
	//$(this).attr("class") 另一种方法获得点击哪一个按钮
	if($(this).hasClass("btn-valid")){
		valid=1;//表示启用
	}
	if($(this).hasClass("btn-invalid")){
		valid=0;//表示禁用
	}
	//1.2获得选中的id值(可能是多个)
	var checkedIds=getCheckedIds();
	console.log("checkedIds="+checkedIds);
	if(checkedIds.length==0){
		alert("请至少选择一项");
		return;
	}
	//2.提交异步请求,更新对应记录的状态信息
	var url="project/doValidById.do";
	var params={"checkedIds":checkedIds,"valid":valid};
	console.log("params="+JSON.stringify(params));
	//post是一个特殊的ajax请求(类型为post)
	$.post(url,params,function(result){//回调函数
		//alert("result="+JSON.stringify(result));
		if(result.state==1){
			alert(result.message);//ok
			doGetObjects();//重新查询
		}else{//请求过程发生异常
			alert(result.message);
		}
	});
}
function getCheckedIds(){//结果数据应是1,2,3
	var checkedIds="";
	//1.遍历所有的checkbox,获得选中的值
	//each函数用于迭代对象
	
	/* 另一个写法
	 * 根据标签取节点
	 * $('tbody input[name="checkedItem"]').
	 * each(function(){
	 * 	if($(this).is(":checked")){}
	 * });
	 */
	$('#tbodyId input[name="checkedItem"]').each(function(){
		//判定当前对象是否是选中的
		if($(this).prop("checked")){
			if(checkedIds==""){
				checkedIds+=$(this).val();
			}else{
				checkedIds+=","+$(this).val();
			}
		}
	});
	//2.返回获得的数据
	return checkedIds;
}
function doQueryObjects(){
	//1.修改当前页的值为1
	$("#pageId").data("pageCurrent",1);
	//2.执行查询动作(重用doGetObjects方法)
	doGetObjects();
}
/**获得查询表单中的数据*/
function getQueryFormData(){
	//根据id获得具体对象的值,然后封装到JSON对象中
	var params={
			"name":$("#searchNameId").val(),
			"valid":$("#searchValidId").val()
	};
	console.log(JSON.stringify(params));
	//一定要记得返回值
	return params;
}
function doGetObjects(){
	//debugger
	//定义一个url
	var url="project/doFindObjects.do";
	//定义一个params对象
	//获取当前页的页码值,假如没有值,默认值设置为1
	var pageCurrent=$("#pageId").data("pageCurrent");
//	console.log("pageCurrent="+pageCurrent);
	if(!pageCurrent){
		pageCurrent=1;
	}
	//获取查询表单中数据
	var params=getQueryFormData();
	//动态的向params对象中添加JSON键值对key/value
	console.log(JSON.stringify(params));
	params.pageCurrent=pageCurrent;
	//底层发起ajax异步请求
	//getJSON方法是一个增强版的ajax方法
	//$.getJSON(url,params,function(result){
	$.post(url,params,function(result){
		//result为一个json对象,由服务端返回
		//@ResponseBody()使用时需引用第三方API
		//console.log("result="+result);数据跟踪
		//将json对象转换成json字符串输出
		//console.log(JSON.stringify(result));
		if(result.state=1){//成功
//			alert(result.message);//假如有需要弹出alert框
			//显示记录信息
			setTableBodyRows(result.data.list);
			//设置及显示分页信息
			setPagination(result.data.pageObject);
		}else{
			alert(result.message);
		}
	});
}
//将json对象中的数据填充到table的tbody中
function setTableBodyRows(result) {
	//获得tbody对象(根据id获得)
	var tBody = $("#tbodyId");
	//清空body中内容
	tBody.empty();
	//迭代json对象List<Project>
	for(var i in result) {
		//构建tr对象
		var tr = $("<tr></tr>");
		//在tr对象上绑定id值
		tr.data("id",result[i].id);
		var firstTd='<td><input type="checkbox" name="checkedItem" value="[id]"></td>';
		//将firstTd字符串中的[id]替换为一个具体值
		firstTd=firstTd.replace("[id]",result[i].id);
		//tr对象中追加td字符串对象
		tr.append(firstTd);
//		tr.append('<td><input type="checkbox" name="checkedId" value="[id]"></td>');
		tr.append("<td>"+result[i].code+"</td>");
		tr.append("<td>"+result[i].name+"</td>");
		tr.append("<td>"+result[i].beginDate+"</td>");
		tr.append("<td>"+result[i].endDate+"</td>");
//		tr.append("<td>"+new Date(result[i].beginDate).toLocaleDateString()+"</td>");
//		tr.append("<td>"+new Date(result[i].endDate).toLocaleDateString()+"</td>");
		tr.append("<td>"+(result[i].valid?"启用":"禁用")+"</td>");
		tr.append("<td><button  type='button' class='btn btn-defult btn-update'>修改</td>");
		tBody.append(tr);   
	}
}