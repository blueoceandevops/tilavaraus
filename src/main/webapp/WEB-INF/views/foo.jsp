<%@page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
	      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Document</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
	      integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

</head>
<body>
<ul>
	<jsp:useBean id="rooms" scope="request" type="java.util.List<fi.xamk.tilavaraus.domain.Room>"/>
	<c:forEach items="${rooms}" var="room">
		<li>
			<a href="${pageContext.request.contextPath}/${room.id}">${room.name}</a>
			<p>${room.price}€ Max. ${room.capacity} henkilöä</p>
		</li>
	</c:forEach>
</ul>
</body>
</html>