package cn.tedu.ttms.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	@RequestMapping("/indexUI.do")
	public String indexUI(){
		System.out.println("indexUI");
		return "index";
	}
}
