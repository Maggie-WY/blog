package com.ssm.blog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssm.blog.view.TypeInfo;
/**
 * 
 * @author jack
 *
 */
@Service("TypeInfoService")
public interface TypeInfoService {

	//根据用户id得到用户的文章分类列表
	public List<TypeInfo> getTypeList(Integer user_id);
	
	//保存文章分类信息
	void save(String[] idArr, String[] sortArr, String[] nameArr, Integer user_id);
	
	//删除文章分类信息
	void delete(String[] idArr);

	//根据文章分类id获取文章分类
	public Object selectByPrimaryKey(Integer id);

}
