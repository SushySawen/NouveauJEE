<%@ page import="projet.data.Etudiant" %><%--
  Created by IntelliJ IDEA.
  User: sophie
  Date: 09/12/2019
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="etudiants"
             type="java.util.Collection<projet.data.Etudiant>" scope="request"/>

<h1>Consultation des absences pour tous les étudiants</h1>
<table class="table table-striped">
    <thead>
    <tr>
        <th>Etudiant</th>
        <th>Absences</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Etudiant etudiant : etudiants) {
            out.print("<tr>");
            out.print("<td>" + etudiant.getPrenom() + " "
                    + etudiant.getNom() + "</td>");
            out.print("<td>" + etudiant.getNbAbsences() + "</td>");
            out.print("</tr>");
        }
    %>
    </tbody>
</table>
