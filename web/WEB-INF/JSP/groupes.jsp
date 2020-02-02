<%@page import="projet.data.Groupe"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="groupes" type="java.util.List<projet.data.Groupe>" scope="request"/>


<html>
<head>
<title><%=application.getInitParameter("title")%></title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</head>
<body>

<h1>Liste des groupes présents en BD</h1>
<jsp:include page='<%= application.getInitParameter("entetedepage")%>'></jsp:include>


<!-- tableau de groupes  -->
<form method="POST" action="<%=application.getContextPath()%>/do/groupes">
	<table class="table table-striped">
		<thead>
		<tr>
			<th>Nom du groupe</th>
			<th>Nombre d'étudiants</th>
		</tr>
		</thead>
		<tbody>
	<% for (Groupe groupe : groupes) {%>

		<tr>
			<td><button class="btn btn-primary" type="submit" name="ajouterFiltre" value="<%=groupe.getId()%>">
				<%=groupe.getNom()%></button></td>

			<td><%=groupe.getEtudiants().size()%></td>
		</tr>
<% } %>
		</tbody>
	</table>
</form>


</body>
</html>