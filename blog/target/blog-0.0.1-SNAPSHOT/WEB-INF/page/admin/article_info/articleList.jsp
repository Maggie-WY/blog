<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章列表</title>
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
						<span>文章管理</span>
						<span class="divider">/</span>
						<span class="active">文章列表</span>
					</div>
				</div>
				<!--主体内容-->
				<div class="list-content">
					<!--块元素-->
					<div class="block">
						<!--页面有多个表格时，可以用于标识表格-->
						<h2>文章列表</h2>
						<!--右上角的返回按钮-->
						<a href="javascript:history.back();">
							<button class="button wathet" style="position: absolute;right: 20px;top: 16px;"><span class="icon-arrow_back"></span> 返回</button>
						</a>
						
						<!--正文内容-->
						<div class="main-10">
							<!--表格上方的搜索操作-->
							<div style="text-align:right;margin-bottom:10px;">
								<!-- 文章类型检索 -->
								<select id="type_id" class="no-shadow">
									<option value="">请选择</option>
									<c:forEach items="${typeList}" var="typeInfo" varStatus="status" >
										<c:choose>
											<c:when test="${typeInfo.id==typeId}">
												<option value="${typeInfo.id}" selected>${typeInfo.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${typeInfo.id}" >${typeInfo.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<!-- 日期检索 -->
								<input type="text" id="date2" class="date" style="width:300px;" value="" readonly/>
								
								<!-- 标题检索 -->
								<input type="text" class="text" id="title" value="${keyWord}" placeholder="检索标题" />
								<!-- 点击查询按钮 -->
								<button class="button blue" style="margin-top: -3px;" onclick="search()"><span class="icon-search"></span></button>
							</div>
							
							<!--表格上方的操作元素，添加、删除等-->
							<div class="operation-wrap">
								<div class="buttons-wrap">
									<a href="${pageContext.request.contextPath}/article_info/edit.action"><button id="add" class="button blue"><span class="icon-plus"></span> 添加</button></a>
								</div>
							</div>
							<table id="table" class="table">
								<thead>
									<tr>
										<th style="width:22px"><input type="checkbox" class="fill listen-1"/> </th>
										<th style="width:30px;text-align:center">序号</th>
										<th style="text-align:center">分类</th>
										<th style="text-align:center">标题</th>
										<th style="text-align:center">简介</th>
										<th style="text-align:center">撰写日期</th>
										<th style="text-align:center">浏览量</th>
										<th style="text-align:center">编辑</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${fn:length(pageInfo.list)==0}">
											<tr>
												<td colspan="6" style="text-align:center">暂无记录</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${pageInfo.list}" var="entity" varStatus="status" >
												<tr>
													<td><input type="checkbox" class="fill listen-1-2" name="id" value="${entity.id}" /> </td>
													<td style="text-align:center">${status.index+1}</td>
													<td style="text-align:center">${entity.type_name}</td>
													<td style="text-align:center">${entity.title}</td>
													<td style="text-align:center">${entity.contentText}</td>
													<td style="text-align:center">${entity.updateTime}</td>
													<td style="text-align:center">${entity.viewCount}</td>
													<td style="text-align:center">
														<a href="${pageContext.request.contextPath}/article_info/edit.action?id=${entity.id}"><button class="button wathet">
																<span class="icon-edit"></span>编辑</button></a>
													</td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<!-- 分页  -->
							<div class="page">
								<ul id="page" class="pagination"></ul>
							</div>
							<!--块元素-->
							<div class="block no-shadow">
								<!--banner用来修饰块元素的名称-->
								<div class="banner">
									<p class="tab fixed">批量操作<span class="hint">Batch operation</span></p>
								</div>
								<!--正文内容-->
								<div class="main" style="margin-bottom:150px;">
									<input type="radio" class="fill" name="radio" value="move"/>批量移动到分类
									<select id="type_id2" class="no-shadow">
									<c:forEach items="${typeList}" var="typeInfo" varStatus="status" >
										<option value="${typeInfo.id}" >${typeInfo.name}</option>
									</c:forEach>
									</select>
									<br/>
									<br/>
									<input type="radio" class="fill" name="radio" value="delete" />批量删除
									<br/>
									<br/>
									<button id="submit2" class="button indigo"><span class="icon-check">提交</span></button>
									<br/>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	var currentPage = "${pageInfo.pageNum}";
	var pageCount = "${pageInfo.pages}";
	
	var startDate = "${startDate}";
	var endDate = "${endDate}";
	$("#submit2").click(function(){
		var idArr = new Array();
		$(':checkbox[name="id"]:checked').each(function(){
			idArr.push($(this).val());
		});
		if(idArr.length==0){
			javaex.optTip({
				content : "至少选择一篇文章",
				type : "error"
			});
			return ;
		}
		var opt = $(':radio[name="radio"]:checked').val();
		if(opt=="move"){
			
			var typeId = $("#type_id2").val();	
			
			$.ajax({
				url : "${pageContext.request.contextPath}/article_info/move.json",
				type : "POST",
				dataType : "json",
				traditional : "true",
				data : {
					"idArr" : idArr,
					"typeId" : typeId
				},
				success : function(rtn) {
					if(rtn.code="1"){
						javaex.optTip({
							content : rtn.message
						});
						// 建议延迟加载
						setTimeout(function() {
							// 刷新页面
							window.location.reload();
						}, 2000);
					}else{
						javaex.optTip({
							content : rtn.message ,
							type: "error"
						});
					}
				},
				error : function(rtn) {
					alert("未知错误，请稍后重试！");
				}
			});
		}else if(opt=="delete"){
			$.ajax({
				url : "${pageContext.request.contextPath}/article_info/update_status.json",
				type : "POST",
				dataType : "json",
				traditional : "true",
				data : {
					"idArr" : idArr,
					"status" : "0"
				},
				success : function(rtn) {
					if (rtn.code=="1") {
						javaex.optTip({
							content : rtn.message
						});
						// 建议延迟加载
						setTimeout(function() {
							// 刷新页面
							window.location.reload();
						}, 2000);
					} else {
						javaex.optTip({
							content : rtn.message,
							type : "error"
						});
					}
				}
			});
		}
		
	});
	javaex.page({
		id : "page",
		pageCount : pageCount,	// 总页数
		currentPage : currentPage,// 默认选中第几页
		position : "center",
		// 返回当前选中的页数
		callback:function(rtn) {
			search(rtn);
		}
	});
	function search(pageNum){
		
		if (pageNum==undefined||pageNum=="undefined") {
			pageNum = 1;
		}
		
		// 文章分类
		var typeId = $("#type_id").val();
		// 关键字检索
		var keyWord = $("#title").val();
		
		window.location.href = "${pageContext.request.contextPath}/article_info/articleList.action"
				+ "?pageNum="+pageNum
				+ "&typeId="+typeId
				+ "&startDate="+startDate
				+ "&endDate="+endDate
				+ "&keyWord="+keyWord
				;
	}
	javaex.select({
		id : "type_id",
		isSearch:false,
	});
	javaex.select({
		id : "type_id2",
		isSearch:false,
	});
	
	javaex.date({
		id : "date2",		// 承载日期组件的id
		monthNum : 2,	// 2代表选择范围日期
		startDate: "${startDate}",	// 开始日期
		endDate: "${endDate}",		// 结束日期
		// 重新选择日期之后返回一个时间对象
		alignment : "right",
		callback: function (rtn) {
			startDate = rtn.startDate;
			endDate = rtn.endDate;
		}
	});
</script>
</html>