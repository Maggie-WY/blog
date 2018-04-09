<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<div class="wrap">
		<!-- 头部 -->
		<div id="header">
			<c:import url="../header.jsp"></c:import>
		</div>
		<!-- 左侧菜单和主体内容 -->
		<div class="grid-1-7" style="flex: 1;margin:0;">
		
			<c:import url="../menu.jsp"></c:import>
			<!-- 面包屑导航和主体内容 -->
			<div class="content">
				<!--面包屑导航-->
				<div class="content-header">
					<div class="breadcrumb">
						<span>一级菜单名称</span>
						<span class="divider">/</span>
						<span class="active">二级菜单名称</span>
					</div>
				</div>
				<!--主体内容-->
				<div class="list-content">
					<!--块元素-->
					<div class="block">
						<!--页面有多个表格时，可以用于标识表格-->
						<h2>文章分类列表</h2>
						<!--右上角的返回按钮-->
						<a href="javascript:history.back();">
							<button class="button wathet" style="position: absolute;right: 20px;top: 16px;"><span class="icon-arrow_back"></span> 返回</button>
						</a>
						
						<!--正文内容-->
						<div class="main-10">
							<!--表格上方的搜索操作-->
							<div style="text-align:right;margin-bottom:10px;">
								<!-- 标题检索 -->
								<input type="text" class="text" id="title" value="" placeholder="检索标题" />
								<!-- 点击查询按钮 -->
								<button class="button blue" style="margin-top: -3px;" onclick="search()"><span class="icon-search"></span></button>
							</div>
							
							<!--表格上方的操作元素，添加、删除等-->
							<div class="operation-wrap">
								<div class="buttons-wrap">
									<button id="add" class="button blue"><span class="icon-plus"></span> 添加</button>
									<button id="save" class="button green"><span class="icon-check"></span> 保存</button>
									<button id="delete" class="button red"><span class="icon-minus"></span> 删除</button>
								</div>
							</div>
							<table id="table" class="table">
								<thead>
									<tr>
										<th style="width:22px"><input type="checkbox" class="fill listen-1"/> </th>
										<th style="width:15%">排序</th>
										<th>分类名称</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${typeList}" var="entity" varStatus="status" >
										<tr>
											<td><input type="checkbox" class="fill listen-1-2" name="id" value="${entity.id}" /> </td>
											<td><input type="text" class="text" name="sortNo" value="${entity.sortNo}" data-type="正整数" error-msg="必须输入正整数"/></div></td>
											<td><input type="text" class="text" name="name"  data-type="必填" placeholder="请输入分类名称" value="${entity.name}"/></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var idArr = new Array();
	var sortArr = new Array();
	var nameArr = new Array();
	$("#delete").click(function(){
		idArr = [];
		$(':checkbox[name="id"]:checked').each(function(){
			var id = $(this).val();
			if(id=="null"){
				$(this).parent().parent().parent().remove();
			}else{
				idArr.push(id);
			}
		});
		if(idArr.length!=0){
			$.ajax({
				url : "${pageContext.request.contextPath}/type_info/delete.json",
				type : "POST",
				traditional : "true",
				dataType : "json",
				data : {
					"idArr" : idArr,
				},
				success : function(rtn) {
					if(rtn.code="1"){
						javaex.optTip({
							content : rtn.message
						});
						window.location.reload();
					}else{
						javaex.optTip({
							content : rtn.message ,
							type: "error"
						});
					}
				},
				error : function(rtn) {
					alert("未知错误!")
				}
			});
		}
		
	});
	$("#add").click(function(){
		var html = '<tr>';
		html += '<td><input type="checkbox" class="fill listen-1-2" name="id" value="null" /> </td>';
		html += '<td><input type="text" class="text" name="sortNo"  data-type="正整数" error-msg="必须输入正整数" value="${entity.sortNo}" /></div></td>';
		html += '<td><input type="text" class="text" name="name"  data-type="必填" placeholder="请输入分类名称" value="${entity.name}" /></div></td>';
		html += '</tr>';
		$("#table tbody").append(html);
		javaex.render();
	});
	$("#save").click(function(){
		if(!javaexVerify()){
			return;
		}
		idArr = [];
		sortArr = [];
		nameArr = [];
		
		$(':checkbox[name="id"]').each(function(){
			idArr.push($(this).val());
		});
		$('input[name="sortNo"]').each(function(){
			sortArr.push($(this).val());
		});
		$('input[name="name"]').each(function(){
			nameArr.push($(this).val());
		});
		console.log(idArr);
		console.log(sortArr);
		console.log(nameArr);
		$.ajax({
			url : "${pageContext.request.contextPath}/type_info/save.json",
			type : "POST",
			traditional : "true",
			dataType : "json",
			data : {
				"idArr" : idArr,
				"sortArr" : sortArr,
				"nameArr" : nameArr
			},
			success : function(rtn) {
				if(rtn.code="1"){
					javaex.optTip({
						content : rtn.message
					});
					window.location.reload();
				}else{
					javaex.optTip({
						content : rtn.message ,
						type: "error"
					});
				}
			},
			error : function(rtn) {
				alert("未知错误!")
			}
		});
	});
</script>
</html>