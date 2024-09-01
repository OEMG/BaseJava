<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.CompanySection" %>
<%@ page import="com.basejava.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table>
        <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.png"
                                                                                          alt="Edit Resume"></a></h2>
        <p>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <c:out value="${contactEntry.key.toHtml(contactEntry.value)}" escapeXml="false"/><br/>
            </c:forEach>
        <p>
        <hr>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
            <h2><a name="type.name">${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                    <%=((TextSection) section).getDescription()%>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                            <ul>
                                <c:forEach var="item" items="<%=((ListSection) section).getList()%>">
                                    <li>${item}</li>
                                </c:forEach>
                            </ul>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((CompanySection) section).getCompanies()%>">
                                <c:choose>
                                    <c:when test="${empty org.website}">
                                        <h3>${org.name}</h3>
                                    </c:when>
                                    <c:otherwise>
                                        <h3><a href="${org.website}">${org.name}</a></h3>
                                    </c:otherwise>
                                </c:choose>
                        <c:forEach var="position" items="${org.periods}">
                            <jsp:useBean id="position" type="com.basejava.webapp.model.Period"/>
                            <%=HtmlUtil.formatDates(position)%>
                            <b>${position.title}</b><br>${position.description}<br><br>
                        </c:forEach>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
    </table>
    <button type="button" onclick="window.location.href='/resumes/resume'">Назад</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>