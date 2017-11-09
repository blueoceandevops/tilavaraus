<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:useBean id="rooms" scope="request" type="java.util.List<fi.xamk.tilavaraus.domain.Room>"/>

<t:layout>
	<div class="card-columns">
		<c:forEach items="${rooms}" var="room">
			<div class="card">
				<img class="card-img-top"
					 src="https://www.xamkravintolat.fi/wp-content/uploads/sites/2/2017/02/Kuitula_ylakuva-2-1920x725.jpg"
					 alt="Card image cap">
				<div class="card-body">
					<h4 class="card-title">${room.name}</h4>
					<p class="card-text">${room.price}€ Max. ${room.capacity} henkilöä</p>
					<a href="${pageContext.request.contextPath}/rooms/${room.id}" class="btn btn-primary">
						<spring:message code="reserve"/>
					</a>
				</div>
			</div>
		</c:forEach>
	</div>

</t:layout>



