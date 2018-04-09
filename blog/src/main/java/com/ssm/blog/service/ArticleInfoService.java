package com.ssm.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssm.blog.view.ArticleInfo;
import com.ssm.blog.view.TypeInfo;

/**
 * 
 * @author 丁杰
 *
 */
@Service("ArticleInfoService")
public interface ArticleInfoService {
	
	//根据博主id获取所有文章状态为1的文章列表
	List<ArticleInfo> getArticleList(Integer user_id);
	
	//根据文章id获取文章的信息
	ArticleInfo getArticleInfoById(Integer id);
	
	//条件检索文章
	List<ArticleInfo> list(Map<String, Object> param, int user_id);
	
	//根据文章类型id获取文章的数量
	int getArticlesByTypeId(String[] idArr, String string);

	//保存一条文章记录
	void save(ArticleInfo articleInfo);
	
	//批量更新文章
	void batchUpdate(Map<String, Object> param);
	
	//修改文章的状态
	void changeStatusByTypeId(String[] idArr,Integer user_id);
	
	//修改文章的文章浏览量
	void updateArticleViewCount(ArticleInfo articleInfo);

}
