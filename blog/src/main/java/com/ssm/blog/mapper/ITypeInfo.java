package com.ssm.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssm.blog.view.TypeInfo;

public interface ITypeInfo {
	
	//根据用户id得到用户的文章分类列表
	public List<TypeInfo> getTypeList(Integer user_id);
	
	//插入一条文章分类记录
	public int insert(@Param("sortNo") String sort,@Param("name") String name,@Param("user_id") Integer user_id);

	//修改一条文章分类记录
	public int update(@Param("id") String id,@Param("sortNo") String sort,@Param("name") String name);

	//批量删除文章分类记录
	public int deleteTypes(@Param("idArr") String[] idArr);
}
