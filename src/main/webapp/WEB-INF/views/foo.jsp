<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
	<ul>
		<jsp:useBean id="rooms" scope="request" type="java.util.List<fi.xamk.tilavaraus.domain.Room>"/>
		<c:forEach items="${rooms}" var="room">
			<li>
				<a href="${pageContext.request.contextPath}/rooms/${room.id}">${room.name}</a>
				<p>${room.price}€ Max. ${room.capacity} henkilöä</p>
			</li>
		</c:forEach>
	</ul>
</t:layout>



