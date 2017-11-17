<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag description="Layout" pageEncoding="UTF-8" %>
<%@ attribute name="value" type="java.time.LocalDateTime" %>

<fmt:parseDate value="${value}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date"/>
<fmt:formatDate value="${parsedDate}" type="date" pattern="d.M.yyy H.mm"/>