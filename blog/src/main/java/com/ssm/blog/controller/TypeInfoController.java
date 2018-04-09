package com.ssm.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.blog.exception.CustomException;
import com.ssm.blog.service.ArticleInfoService;
import com.ssm.blog.service.TypeInfoService;
import com.ssm.blog.service.UserInfoService;
import com.ssm.blog.view.Result;
import com.ssm.blog.view.TypeInfo;
import com.ssm.blog.view.UserInfo;

/**
 * 
 * @author 丁杰
 *
 */
@Controller
@RequestMapping("/type_info")
public class TypeInfoController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	TypeInfoService typeInfoService;
	
	@Autowired
	ArticleInfoService articleInfoService;
	
	/**
	 * 根据博主id获取文章类型列表并返回至页面list.jsp
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping("/list.action")
	public String list(ModelMap map,HttpSession session) {
		List<TypeInfo> list = typeInfoService.getTypeList(((UserInfo)session.getAttribute("userInfo")).getId());	
		map.put("typeList", list);
		return "admin/type_info/list";
	}
	
	/**
	 * 删除文章类型并返回json数据
	 * @param idArr
	 * @return
	 * @throws CustomException 
	 */
	@RequestMapping("/delete.json")
	@ResponseBody
	public Result deleteTypeList(@RequestParam(value = "idArr") String[] idArr,HttpSession session) throws CustomException {
		
		//根据文章类型id获取状态为1的文章数量
		int count = articleInfoService.getArticlesByTypeId(idArr,"1");
		
		if(count > 0) {
			//文章类型被使用
			throw new CustomException("选中类型中含有正常状态的文章，无法将其删除!");
		}
		//如果未被使用，则将文章类型下的所有状态为"0"的文章的状态改为"-1"
		articleInfoService.changeStatusByTypeId(idArr,((UserInfo)session.getAttribute("userInfo")).getId());
		typeInfoService.delete(idArr);
		
		return new Result("1","删除成功");
	}
	
	/**
	 * 文章类型的保存并返回json数据
	 * @param idArr
	 * @param sortArr
	 * @param nameArr
	 * @param session
	 * @return
	 */
	@RequestMapping("/save.json")
	@ResponseBody
	public Result saveTypeList(@RequestParam(value = "idArr") String[] idArr,
			@RequestParam(value = "sortArr") String[] sortArr,
			@RequestParam(value = "nameArr") String[] nameArr,HttpSession session) {
		
		//根据博主id将文章类型保存
		typeInfoService.save(idArr,sortArr,nameArr,((UserInfo)session.getAttribute("userInfo")).getId());
		
		return  new Result("1","保存成功");
	}
	
}
