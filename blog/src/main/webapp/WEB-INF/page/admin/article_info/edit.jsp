<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文章编辑</title>
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
						<span class="active">文章编辑</span>
					</div>
				</div>
				<!--主体内容-->
				<div class="list-content">
					<!--块元素-->
					<div class="block">
						<!--修饰块元素名称-->
						<div class="banner">
							<p class="tab fixed">文章编辑<span class="hint">Article Editor</span></p>
						</div>
						<!--正文-->
						<div class="main">
							<!--表单-->
							<form id="form" action="" method="">
								<!--输入框-->
								<div class="unit">
									<div class="left">
										<p class="subtitle">标题</p>
									</div>
									<div class="right">
										<c:choose>
											<c:when test="${empty articleInfo}">
												<input type="text" class="text" name="title" data-type="必填"  placeholder="请输入文章标题" value=""/>
												<input type="hidden" id="id" name="id" value="" />
											</c:when>
											<c:otherwise>
												<input type="text" class="text" name="title" data-type="必填"  placeholder="请输入文章标题" value="${articleInfo.title }"/>
												<input type="hidden" id="id" name="id" value="${articleInfo.id}" />
											</c:otherwise>
										</c:choose>
									</div>
									<!--清浮动-->
									<span class="clearfix"></span>
								</div>
								<!--分割线-->
								<p class="divider"></p>
								<!--文章封面-->
								<div class="unit">
									<div class="left">
										<p class="subtitle">封面</p>
									</div>
									<div class="right">
										<!-- 上传按钮 -->
										<a href="javascript:;">
											<label id="container" for="upload" style="display: inline-block; width:132px;height:74px;">
												<c:choose>
													<c:when test="${empty articleInfo}">
														<img src="${pageContext.request.contextPath}/static/javaex/pc/images/default.png" width="100%" height="100%" />
													</c:when>
													<c:otherwise>
														<img src="${articleInfo.cover}" width="100%" height="100%" />
													</c:otherwise>
												</c:choose>
											
											</label>
											<input type="file" class="hide" id="upload" accept="image/gif, image/jpeg, image/jpg, image/png" />
										</a>
										<input type="hidden" id="cover" name="cover" value="">
									</div>
									<!--清浮动-->
									<span class="clearfix"></span>
								</div>
								<!--分割线-->
								<p class="divider"></p>
								<!--下拉选择框-->
								<div class="unit">
									<div class="left">
										<p class="subtitle">所属分类</p>
									</div>
									<div class="right">
										<select id="typeInfo_id" name="typeId">
										<c:choose>
											<c:when test="${empty articleInfo}">
												<c:forEach items="${typeList}" var="typeInfo" varStatus="status" >
													<option value="${typeInfo.id}">${typeInfo.name}</option>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach items="${typeList}" var="typeInfo" varStatus="status" >
													<c:choose>
														<c:when test="${articleInfo.typeId==typeInfo.id}">
															<option selected  value="${typeInfo.id}">${typeInfo.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${typeInfo.id}">${typeInfo.name}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</c:otherwise>
										</c:choose>
										</select>
									</div>
									<!--清浮动-->
									<span class="clearfix"></span>
								</div>
								<!--分割线-->
								<p class="divider"></p>
								
								<!--文本域-->
								<div class="unit">
									<div class="left">
										<p class="subtitle">内容</p>
									</div>
									<div class="right">
										<div id="edit" class="edit-container" ></div>
										
										<input type="hidden" id="content" name="content" value="">
										<input type="hidden" id="content_text" name="contentText" value="">
									</div>
									<!--清浮动-->
									<span class="clearfix"></span>
								</div>
								<!--分割线-->
								<p class="divider"></p>
								
								<!--提交按钮-->
								<div class="unit">
									<div style="margin-left: 200px;">
										<!--表单提交时，必须是input元素，并指定type类型为button，否则ajax提交时，会返回error回调函数-->
										<input type="button" id="return" class="button no" value="返回" />
										<input type="button" id="submit" class="button yes" value="保存" />
									</div>
									<!--清浮动-->
									<span class="clearfix"></span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	$("#return").click(function(){
		history.back();
	});
	$("#submit").click(function(){
		if(javaexVerify()){
			$.ajax({
				url : "${pageContext.request.contextPath}/article_info/save.json",
				type : "POST",
				dataType : "json",
				data : $("#form").serialize(),
				success : function(rtn) {
					if(rtn.code="1"){
						javaex.optTip({
							content : rtn.message
						});
						window.location.href = "${pageContext.request.contextPath}/article_info/articleList.action";
					}else{
						javaex.optTip({
							content : rtn.message ,
							type: "error"
						});
					}
				},
				error : function(rtn) {
					alert("未知错误！");
				}
			});
		}
	});
	javaex.select({
		id : "typeInfo_id",
		isSearch:false
	});
	var content ='${articleInfo.content}';
	javaex.edit({
		content:content,
		id : "edit",
		callback : function(rtn) {
			$("#content").val(rtn.html);
			$("#content_text").val(rtn.text.substring(0,50));
		}
	});
	javaex.upload({
		type : "image",
		url : "${pageContext.request.contextPath}/article_info/upload.json",	// 请求路径
		id : "upload",	// <input type="file" />的id
		param : "file",			// 参数名称，SSM中与MultipartFile的参数名保持一致
		dataType : "url",		// 返回的数据类型：base64 或 url
		callback : function (rtn) {
			// 后台返回的数据
			console.log(rtn);
			if (rtn.code=="1") {
				$("#container img").attr("src", "/upload/"+rtn.date.imgUrl);
				$("#cover").val("/upload/"+rtn.date.imgUrl);
			} else {
				javaex.optTip({
				content : rtn.message,
					type : "error"
				});
			}
		}
	});
</script>
</html>