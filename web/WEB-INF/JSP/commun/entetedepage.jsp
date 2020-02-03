<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
    <a class="nav-link" href="<%= application.getContextPath()%>/do/etudiants">Consulter tous les étudiants</a>
    <a class="nav-link" href="${pageContext.request.contextPath}/do/groupes">Choisir un groupe</a>
    <a class="nav-link" href="${pageContext.request.contextPath}/do/consulterNotes">Consulter les notes</a>
    <a class="nav-link" href="${pageContext.request.contextPath}/do/notes">Editez les notes</a>
    <a class="nav-link" href="${pageContext.request.contextPath}/do/consulterAbsences">Consulter les absences</a>
    <a class="nav-link" href="${pageContext.request.contextPath}/do/absences">Editez les absences</a>

</nav>
<%--
<% if (request.getSession().getAttribute("groupe") != null){

%>
<jsp:include page='<%= application.getInitParameter("boutonFiltre")%>'></jsp:include>
<%
    }
%>--%>
