package com.ssm.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssm.blog.mapper.ArticleInfoMapper;
import com.ssm.blog.mapper.IArticleInfo;
import com.ssm.blog.mapper.IUserInfo;
import com.ssm.blog.service.ArticleInfoService;
import com.ssm.blog.service.UserInfoService;
import com.ssm.blog.view.ArticleInfo;
import com.ssm.blog.view.UserInfo;

/**
 * 
 * @author 丁杰
 *
 */
@Service("ArticleInfoServiceImpl")
public class ArticleInfoServiceImpl implements ArticleInfoService{
	
	@Autowired
	IArticleInfo iArticleInfo;
	
	//根据博主id获取所有文章状态为1的文章列表
	public List<ArticleInfo> getArticleList(Integer user_id) {

		return iArticleInfo.getArticleList(user_id);
	}
	
	//根据文章id获取文章的信息
	public ArticleInfo getArticleInfoById(Integer id) {
		
		return iArticleInfo.getArticleInfoById(id);
	}
	
	//条件检索文章
	public List<ArticleInfo> list(Map<String, Object> param, int user_id) {
		return iArticleInfo.getAllArticleList(param,user_id);
	}
	
	//根据文章类型id获取文章的数量
	public int getArticlesByTypeId(String[] idArr, String string) {
		return iArticleInfo.getArticlesByTypeId(idArr,string);
	}
	
	//保存一条文章记录
	public void save(ArticleInfo articleInfo) {
		if(articleInfo.getId()==null) {
			//新增
			articleInfo.setUserId(1);
			articleInfo.setStatus(1);
			articleInfo.setUpdateTime(new Date());

			articleInfo.setViewCount(1);
			iArticleInfo.insertArticle(articleInfo);
		}else {
			articleInfo.setUpdateTime(new Date());
			iArticleInfo.updateArticle(articleInfo);
		}
	}

	//批量更新文章
	public void batchUpdate(Map<String, Object> param) {
		iArticleInfo.batchUpdate(param);
	}

	//修改文章的状态
	public void changeStatusByTypeId(String[] idArr,Integer user_id) {
		iArticleInfo.changeStatusByTypeId(idArr,user_id);
	}
	
	//修改文章的文章浏览量
	public void updateArticleViewCount(ArticleInfo articleInfo) {
		iArticleInfo.updateArticleViewCount(articleInfo);
	}

}
