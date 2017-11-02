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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">

</head>
<body>
<table class="table">
    <jsp:useBean id="reservations" scope="request" type="java.util.List<com.example.demo.domain.Reservation>"/>
    <tr>
        <th>Käyttäjä</th>
        <th>Tilan nimi</th>
        <th>Aloitusaika</th>
        <th>Lopetusaika</th>
        <th>Henkilömäärä</th>
        <th>Oheispalvelut</th>
    </tr>
    <c:forEach items="${reservations}" var="reservation">
        <tr>
            <td>${reservation.user}</td>
            <td>${reservation.room.name}</td>
            <td>${reservation.startTime}</td>
            <td>${reservation.endTime}</td>
            <td>${reservation.personCount}</td>
            <td><ul>
                <c:forEach items="${reservation.additionalServices}" var="additionalService">
                    <li>${additionalService}</li>
                </c:forEach>
            </ul></td>

        </tr>
    </c:forEach>
</table>
</body>
</html>