package com.ssm.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssm.blog.view.ArticleInfo;
import com.ssm.blog.view.TypeInfo;

/**
 * 
 * @author 丁杰
 *
 */
public interface IArticleInfo {
	
	//根据博主id获取所有文章状态为1的文章列表
	public List<ArticleInfo> getArticleList(Integer user_id);
	
	//根据文章id获取文章的信息
	public ArticleInfo getArticleInfoById(int id);

	//插入一条文章记录
	public void insertArticle(ArticleInfo articleInfo);

	//更新一条文章记录
	public void updateArticle(ArticleInfo articleInfo);

	//条件检索文章
	public List<ArticleInfo> getAllArticleList(@Param("param") Map<String, Object> param,@Param("user_id") Integer user_id);

	//批量更新文章
	public void batchUpdate(Map<String, Object> param);
	
	//根据文章类型id获取文章的数量
	public int getArticlesByTypeId(@Param("typeIdArr")String[] idArr, @Param("status")String string);

	//修改文章的状态
	public void changeStatusByTypeId(@Param("typeIdArr")String[] typeIdArr,@Param("user_id")Integer user_id);
	
	//根据文章id获取文章信息
	public ArticleInfo getArticleInfoById(Integer id);

	//更新文章信息
	public void updateArticleViewCount(ArticleInfo articleInfo);

}
