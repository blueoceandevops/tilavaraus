<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag description="Layout" pageEncoding="UTF-8" %>
<%@ attribute name="value" type="java.time.LocalDate" %>

<fmt:parseDate value="${value}" pattern="yyyy-MM-dd" var="parsedDate" type="date"/>
<fmt:formatDate value="${parsedDate}" type="date" pattern="d.M.yyyy"/>