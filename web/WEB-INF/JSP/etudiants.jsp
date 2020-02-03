<%@page import="projet.data.Etudiant" %>
<%@page import="java.util.List" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<jsp:useBean id="etudiants" type="java.util.List<projet.data.Etudiant>" scope="request"/>

<h1>Liste des étudiants présents en BD</h1>

<!-- tableau d'étudiants -->
<table class="table table-striped">
    <thead>
    <tr>
        <th>Prénom/nom de l'étudiant</th>
        <th>Son groupe</th>
    <tr>
    </thead>
    <tbody>
    <% for (Etudiant etudiant : etudiants) {%>
    <tr>
        <td>
            <a href="<%=application.getContextPath()%>/do/details?id=<%=etudiant.getId()%>"><%=etudiant.getPrenom()%> <%=etudiant.getNom()%>
            </a></td>
        <td><%=etudiant.getGroupe().getNom()%>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>