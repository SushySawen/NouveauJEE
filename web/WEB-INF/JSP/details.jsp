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

<h1>Détails de l'étudiant</h1>
<%-- Affichage des détails de l'étudiant--%>
<p>Fiche de : <%= etudiant.getNom()%> <%= etudiant.getPrenom()%>
</p>
<table class="table">
    <tr>
        <td>Groupe</td>
        <td><%= etudiant.getGroupe().getNom()%>
        </td>
    </tr>
    <%--    <tr><td>Moyenne Generale</td><td><%= etudiant.%></td></tr>--%>
    <tr>
        <td>Absences</td>
        <td><%= etudiant.getNbAbsences()%>
        </td>
    </tr>
</table>
