<%@ page import="projet.data.Etudiant"%><%--
  Created by IntelliJ IDEA.
  User: sophie
  Date: 09/12/2019
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:useBean id="etudiants"
	type="java.util.Collection<projet.data.Etudiant>" scope="request" />
<html>
<head>
	<title><%= application.getInitParameter("title")%></title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</head>
<body>
<h1>Notes des étudiants</h1>
<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
	<a class="nav-link" href="${pageContext.request.contextPath}/do/groupes">voir les groupes</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/consulterNotes">Consulter les notes</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/notes">Editez les notes</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/consulterAbsences">Consulter les absences</a>
	<a class="nav-link" href="${pageContext.request.contextPath}/do/absences">Editez les absences</a>
</nav>
	<table class="table table-striped">
		<%
			for (Etudiant etudiant : etudiants) {
				out.print("<tr>");
				out.print("<td>" + etudiant.getPrenom() + " "
						+ etudiant.getNom() + "</td>");
				out.print("<td>"+ "<input type=\"number\" name=\""+etudiant+"\" value=\""+etudiant.getNbAbsences()+"\">" +"</td>");
				out.print("</tr>");
			}
		%>
	</table>
<input type="button" value="Enregistrer les modifications">
</body>
</html>
