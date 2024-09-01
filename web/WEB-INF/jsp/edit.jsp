<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.CompanySection" %>
<%@ page import="com.basejava.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd>
                <input type="text" name="fullName" size=50 value="${resume.fullName}">
                <c:if test="${not empty errorMessage}">
                    <div style="color:red;">${errorMessage}</div>
                </c:if>
            </dd>
        </dl>

        <h2>Контакты:</h2>
        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt><c:out value='${type.title}'/></dt>
                <dd>
                    <input type="text" name="${type.name()}" size=30
                           value="<c:out value='${resume.getContact(type)}'/>">
                </dd>
            </dl>
        </c:forEach>

        <hr>

        <c:forEach var="type" items="${SectionType.values()}">
            <c:set var="section" value="${resume.getSection(type)}"/>

            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE' || type == 'PERSONAL'}">
                    <textarea name="${type}" cols="80" rows="5">${section}</textarea>
                </c:when>

                <%--                <c:when test="${type == 'QUALIFICATIONS' || type == 'ACHIEVEMENT'}">--%>
                <%--                    <textarea name="${type}" cols="80" rows="5">--%>
                <%--                        <c:forEach var="item" items="${section.list}">--%>
                <%--                            ${item}<br/>--%>
                <%--                        </c:forEach>--%>
                <%--                    </textarea>--%>
                <%--                </c:when>--%>

                <c:when test="${type == 'QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                    <textarea name="${type}" cols="80" rows="5">
                        <c:forEach var="item" items="${section.list}" varStatus="status">
                            ${fn:escapeXml(item)}
                            <c:if test="${!status.last}">\n</c:if>
                        </c:forEach>
                    </textarea>
                </c:when>


                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="${section.companies}" varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                                                            <dd><input type="text" name='${type}' size=100 value="${org.name}"></dd>
<%--                            <input type="text" name="${type}${counter.index}name" size="100"--%>
<%--                                   value="${org.name != null ? org.name : ''}">--%>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name='${type}url' size=100 value="${org.website}"></dd>
                            </dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="positions" items="${org.periods}">
                                <jsp:useBean id="positions" type="com.basejava.webapp.model.Period"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}startDate" size=10
                                               value="<%=DateUtil.format(positions.getStartDate())%>"
                                               placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(positions.getEndDate())%>"
                                               placeholder="MM/yyyy">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${type}${counter.index}title' size=75
                                               value="${positions.title}">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${type}${counter.index}description" rows=5
                                                  cols=75>${positions.description}</textarea></dd>
                                </dl>
                                <hr color="black">
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>

        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.location.href='/resumes/resume'">Отменить</button>

    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
