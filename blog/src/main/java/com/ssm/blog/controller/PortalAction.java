package com.ssm.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.blog.exception.CustomException;
import com.ssm.blog.service.ArticleInfoService;
import com.ssm.blog.service.TypeInfoService;
import com.ssm.blog.view.ArticleInfo;
import com.ssm.blog.view.Result;
import com.ssm.blog.view.TypeInfo;
import com.ssm.blog.view.UserInfo;

@Controller
@RequestMapping("/portal")
public class PortalAction {
	
	@Autowired
	ArticleInfoService articleInfoService;
	
	@Autowired
	TypeInfoService typeInfoService;
	
	private int user_id = 1 ; // 博主id
	
	/**
	 * 获取文章状态为1的文章列表并且返回至页面index.jsp
	 * @param map
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/index.action")
	public String articleList(ModelMap map,HttpSession session,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws CustomException {	
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", "1");
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		
		List<ArticleInfo> articleList = articleInfoService.list(param,user_id);	
		PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo> (articleList);
		
		map.put("articleList", articleList);
		map.put("pageInfo", pageInfo);
		return "portal/index";
	}
	
	/**
	 * 页面一加载，就向后台请求文章分类的数据
	 * @return
	 */
	@RequestMapping("get_type.json")
	@ResponseBody
	public Result getType(ArticleInfo articleInfo,HttpSession session) {
		
		List<TypeInfo> typeList = typeInfoService.getTypeList(user_id);
		Result rs = new Result ();
		rs.getDate().put("typeList", typeList);
		return rs;
	}
	
	/**
	 * 根据文章分类id查询所有状态为1的文章
	 * @return
	 */
	@RequestMapping("type.action")
	public String type(ModelMap map,
			@RequestParam(value="typeId") int typeId,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", typeId);
		param.put("status", "1");
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleInfo> list = articleInfoService.list(param,1);
		if (list.size()==0) {
			return "404";
		}
		PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
		map.put("pageInfo", pageInfo);
		map.put("typeInfo", typeInfoService.selectByPrimaryKey(typeId));
		
		return "portal/type";
	}
	
	/**
	 * 根据主键查询文章
	 * @return
	 */
	@RequestMapping("article.action")
	public String article(ModelMap map,
			@RequestParam(value="id") int id) {
		
		ArticleInfo articleInfo = articleInfoService.getArticleInfoById(id);
		if (articleInfo==null) {
			return "404";
		}else {
			articleInfo.setViewCount(articleInfo.getViewCount()+1);
			articleInfoService.updateArticleViewCount(articleInfo);
		}
		map.put("articleInfo", articleInfo);
		
		return "portal/article";
	}
	/**
	 * 关于我页面
	 * @return
	 */
	@RequestMapping("aboutme.action")
	public String about(ModelMap map) {
		return "portal/aboutme";
	}
	
	/**
	 * 搜索文章
	 * @return
	 */
	@RequestMapping("search.action")
	public String search(ModelMap map,
			@RequestParam(value="keyWord") String keyWord,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(keyWord)) {
			param.put("keyWord", "%" + keyWord.trim() + "%");
		}
		param.put("status", "1");
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		List<ArticleInfo> list = articleInfoService.list(param,1);
		PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo>(list);
		map.put("pageInfo", pageInfo);
		//回显
		map.put("keyWord", keyWord);
		return "portal/search";
	}
}
