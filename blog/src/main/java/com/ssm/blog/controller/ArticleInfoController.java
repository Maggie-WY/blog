package com.ssm.blog.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.blog.exception.CustomException;
import com.ssm.blog.service.ArticleInfoService;
import com.ssm.blog.service.TypeInfoService;
import com.ssm.blog.view.ArticleInfo;
import com.ssm.blog.view.Result;
import com.ssm.blog.view.TypeInfo;
import com.ssm.blog.view.UserInfo;

/**
 * 
 * @author 丁杰
 *
 */
@Controller
@RequestMapping("/article_info")
public class ArticleInfoController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	ArticleInfoService articleInfoService;
	
	@Autowired
	TypeInfoService typeInfoService;
	
	/**
	 * 获取状态为1的文章的文章列表并且返回至页面list_normal.jsp
	 * pageNum默认是第一页 pageSize默认一页有十条记录
	 * @param map
	 * @return	
	 * @throws CustomException
	 */
	@RequestMapping("/articleList.action")
	public String articleList(ModelMap map,HttpSession session,
			@RequestParam(required = false,value="typeId") Integer type_id,
			@RequestParam(required = false,value="startDate") String startDate,
			@RequestParam(required = false,value="endDate") String endDate,
			@RequestParam(required = false,value="keyWord") String keyWord,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws CustomException {	
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("typeId", type_id);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		if (!StringUtils.isEmpty(keyWord)) {
			param.put("keyWord", "%" + keyWord.trim() + "%");
		}	
		//查找状态为1的文章
		param.put("status", "1");
		
		int user_id = ((UserInfo)session.getAttribute("userInfo")).getId();		
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo> (articleInfoService.list(param,user_id));
		
		map.put("typeList", typeInfoService.getTypeList(user_id));
		map.put("pageInfo", pageInfo);
		
		//在页面上回显
		map.put("typeId", type_id);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("keyWord", keyWord);
		
		return "admin/article_info/list_normal";
	}
		
	/**
	 * 获取状态为0的文章的文章列表并且返回至页面list_recycle.jsp
	 * pageNum默认是第一页 pageSize默认一页有十条记录
	 * @param map
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/recycleList.action")
	public String recycleList(ModelMap map,HttpSession session,
			@RequestParam(required = false,value="typeId") Integer type_id,
			@RequestParam(required = false,value="startDate") String startDate,
			@RequestParam(required = false,value="endDate") String endDate,
			@RequestParam(required = false,value="keyWord") String keyWord,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize) throws CustomException {	
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", type_id);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		if (!StringUtils.isEmpty(keyWord)) {
			param.put("keyWord", "%" + keyWord.trim() + "%");
		}
		//查找状态为0的文章 即回收站的文章
		param.put("status","0");
		
		//根据博主id查询文章列表以及文章类型
		int user_id = ((UserInfo)session.getAttribute("userInfo")).getId();
		List<TypeInfo> typeList = typeInfoService.getTypeList(user_id);
		
		// pageHelper分页插件
		// 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<ArticleInfo> pageInfo = new PageInfo<ArticleInfo> (articleInfoService.list(param,user_id));
		
		//回显
		map.put("typeId", type_id);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("keyWord", keyWord);
		
		map.put("typeList", typeInfoService.getTypeList(user_id));
		map.put("pageInfo", pageInfo);
		
		return "admin/article_info/list_recycle";
	}
	
	/**
	 * 根据文章id批量更改文章的状态
	 * @param idArr     文章id
	 * @param status	文章需要更改为的状态
	 * @return			
	 */
	@RequestMapping("updateStatus.json")
	@ResponseBody
	public Result updateStatus(
			@RequestParam(value = "idArr") String[] idArr,
			@RequestParam(value = "status") String status) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idArr", idArr);
		param.put("status", status);
		
		articleInfoService.batchUpdate(param);
		
		return new Result("1","操作成功");
	}
	
	/**
	 * 根据文章id批量更改文章的类型
	 * @param idArr     文章id
	 * @param typeId    需要改为的文章类型id
	 * @return
	 */
	@RequestMapping("updateType.json")
	@ResponseBody
	public Result updateType(
			@RequestParam(value = "idArr") String[] idArr,
			@RequestParam(value = "typeId") String typeId) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("idArr", idArr);
		param.put("typeId", typeId);
		
		articleInfoService.batchUpdate(param);
		
		return new Result("1","操作成功");
	}
	
	/**
	 * 保存文章
	 * @param articleInfo
	 * @return
	 */
	@RequestMapping("/save.json")
	@ResponseBody
	public Result saveArticle(ArticleInfo articleInfo) {

		articleInfoService.save(articleInfo);
		
		return new Result("1","操作成功");
	}
	
	/**
	 * 编辑或新增文章
	 * @param map
	 * @param id
	 * @param session
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/edit.action")
	public String editArticle(ModelMap map,@RequestParam(required = false,value = "id") Integer id,HttpSession session) throws CustomException {	
		if(id==null) {
			
		}else {
			ArticleInfo articleInfo = articleInfoService.getArticleInfoById(id);
			map.put("articleInfo", articleInfo);
		}
		
		//根据博主id查询博主的文章类型列表
		List<TypeInfo> typeList = typeInfoService.getTypeList(((UserInfo)session.getAttribute("userInfo")).getId());
		map.put("typeList", typeList);
		
		return "admin/article_info/edit";
	}
	
	/**
	 * 上传文件到磁盘（物理路径）
	 * @throws IOException 
	 */
	@RequestMapping("upload.json")
	@ResponseBody
	public Result upload(MultipartFile file, HttpServletRequest request) throws IOException {
		
		// 文件原名称
		String szFileName = file.getOriginalFilename();
		// 重命名后的文件名称
		String szNewFileName = "";
		// 根据日期自动创建3级目录
		String szDateFolder = "";
		
		// 上传文件
		if (file!=null && szFileName!=null && szFileName.length()>0) {
			Date date = new Date();
			szDateFolder = new SimpleDateFormat("yyyy/MM/dd").format(date);
			// 存储文件的物理路径
			String szFilePath = "E:\\upload\\" + szDateFolder;
			// 自动创建文件夹
			File f = new File(szFilePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			// 新的文件名称
			szNewFileName = UUID.randomUUID() + szFileName.substring(szFileName.lastIndexOf("."));
			// 新文件
			File newFile = new File(szFilePath+"\\"+szNewFileName);
			
			// 将内存中的数据写入磁盘
			file.transferTo(newFile);
		}
		Result rs = new Result();
		rs.setCode("1");
		rs.setMessage("上传成功");
		rs.getDate().put("imgUrl", szDateFolder+"/"+szNewFileName);
		return rs;
		
	}
}
