<%@ page import="projet.data.Etudiant" %>
<%@ page import="projet.data.GestionFactory" %><%--
  Created by IntelliJ IDEA.
  User: sophie
  Date: 25/11/2019
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="etudiant" class="projet.data.Etudiant" scope="request"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</head>
<body>
<h1>Détails de l'étudiant</h1>
<jsp:include page='<%= application.getInitParameter("entetedepage")%>'></jsp:include>
<%-- Affichage des détails de l'étudiant--%>
<p>Fiche de : <%= etudiant.getNom()%> <%= etudiant.getPrenom()%></p>
<table class="table">
    <tr><td>Groupe</td><td><%= etudiant.getGroupe().getNom()%></td></tr>
<%--    <tr><td>Moyenne Generale</td><td><%= etudiant.%></td></tr>--%>
    <tr><td>Absences</td><td><%= etudiant.getNbAbsences()%></td></tr>
</table>

</body>
</html>
