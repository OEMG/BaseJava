<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <a href="resume?uuid=${resume.uuid}&action=add" style="float: right"><img src="img/add2.png" alt="Add Resume" title="Add Resume"></a>
    <table>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Редактировать</th>
            <th>Удалить</th>
        </tr>
        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><c:out value="${resume.getContact(ContactType.MAIL)}"/></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.svg" alt="Edit Resume" title="Edit Resume"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete2.png" alt="Delete Resume" title="Delete Resume"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>