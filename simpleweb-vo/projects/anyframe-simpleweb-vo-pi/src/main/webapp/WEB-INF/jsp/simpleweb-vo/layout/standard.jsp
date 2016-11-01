<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://www.anyframejava.org/tags/simpleweb" prefix="simpleweb" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Anyframe Samples</title>
	<!-- for sample codes -->
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/dijit/themes/tundra/tundra.css" />" />
	<link rel="stylesheet" href="<c:url value='/sample/css/admin.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/sample/css/left.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/sample/css/tundra-customized.css'/>" type="text/css">
	<script type="text/javascript" src="<c:url value='/sample/javascript/CommonScript.js'/>"></script>
	
	<!-- dojo -->
	<script type="text/javascript" src="<c:url value="/resources/dojo/dojo.js" />"></script>  
    <script type="text/javascript" src="<c:url value="/resources/dojo/io/iframe.js" />"></script>  
    <script type="text/javascript" src="<c:url value="/resources/org/anyframe/spring/Anyframe-Spring.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/org/anyframe/spring/Anyframe-Spring-Dojo.js" />"></script>
	
	<!-- for jquery -->
	<script type="text/javascript" src="<c:url value='/simpleweb/jquery/jquery-1.4.2.min.js'/>"></script>

	<!-- jquery uploadify -->
	<link rel="stylesheet" href="<c:url value='/simpleweb/jquery/uploadify/uploadify.css'/>" type="text/css" />
	<script type="text/javascript" src="<c:url value='/simpleweb/jquery/uploadify/swfobject.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/simpleweb/jquery/uploadify/jquery.uploadify.v2.1.0.min.js'/>"></script>
	
</head>
<body class="tundra spring">
<table width="100%" height="520" border="0" cellpadding="0" cellspacing="0">
  <tr>
  <!---- Left Menu ------>
    <td width="177" height="100%" align="left" valign="top"  bgcolor="#eeeeee">
    	<div id="left">
    		<tiles:insertAttribute name="left"/>
    	</div>
    </td>
	<!---- Body ------>
    <td width="100%" height="100%" align="left" valign="top" style="padding:0 20px 0 20px">   
		<div id="body">
        	<tiles:insertAttribute name="body"/>
        </div>	
    </td>
  </tr>
</table>
</body>
</html>