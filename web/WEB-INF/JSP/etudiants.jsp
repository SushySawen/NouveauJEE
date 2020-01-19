<%@page import="projet.data.Etudiant"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="etudiants" type="java.util.List<projet.data.Etudiant>" scope="request"/>


<html>
<head>
<title>Etudiant</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</head>
<body>

<h1>Liste des étudiants présents en BD</h1>
<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
	<a class="nav-link" href="${pageContext.request.contextPath}/do/groupes">voir les groupes</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/consulterNotes">Consulter les notes</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/notes">Editez les notes</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/consulterAbsences">Consulter les absences</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/absences">Editez les absences</a>
</nav>


<!-- tableau d'étudiants  -->
<table>

	<tr>
		<th>Prénom/nom de l'étudiant</th>
		<th>Son groupe</th>
	<tr>
	
	
<% for (Etudiant etudiant : etudiants) {%>

	<tr>

		<td><a href="<%=application.getContextPath()%>/do/details?id=<%=etudiant.getId()%>"><%=etudiant.getPrenom()%> <%=etudiant.getNom()%></a></td>
		<td><%=etudiant.getGroupe().getNom()%></td>
	</tr>
<% } %>
</table>


</body>
</html>