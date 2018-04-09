package com.ssm.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.omg.PortableInterceptor.DISCARDING;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssm.blog.exception.CustomException;
import com.ssm.blog.mapper.ITypeInfo;
import com.ssm.blog.mapper.IUserInfo;
import com.ssm.blog.mapper.TypeInfoMapper;
import com.ssm.blog.service.TypeInfoService;
import com.ssm.blog.view.TypeInfo;

/**
 * 
 * @author 丁杰
 *
 */
@Service("TypeInfoServiceImpl")
public class TypeInfoServiceImpl implements TypeInfoService{
	
	@Autowired
	private ITypeInfo iTypeInfo;
	
	@Autowired
	private TypeInfoMapper typeInfoMapper;

	//根据用户id得到用户的文章分类列表
	public ArrayList<TypeInfo> getTypeList(Integer user_id) {
		return (ArrayList<TypeInfo>) iTypeInfo.getTypeList(user_id);
	}

	//保存文章分类信息
	public void save(String[] idArr, String[] sortArr, String[] nameArr,Integer user_id) {
		
		for(int i = 0;i<idArr.length;i++) {
			if(idArr[i].equals("null")||idArr[i].isEmpty()||idArr[i].equals(" ")) {
				iTypeInfo.insert(sortArr[i],nameArr[i],user_id);
			}else {
				iTypeInfo.update(idArr[i],sortArr[i],nameArr[i]);
			}	
		}
		
	}
	
	//删除文章分类信息
	public void delete(String[] idArr) {
		if(idArr.length>0) {
			iTypeInfo.deleteTypes(idArr);
		}
	}

	//根据文章分类id获取文章分类
	public Object selectByPrimaryKey(Integer id) {
		return typeInfoMapper.selectByPrimaryKey(id);
	}

}
