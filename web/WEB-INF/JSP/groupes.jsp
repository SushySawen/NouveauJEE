<%@page import="projet.data.Groupe" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<jsp:useBean id="groupes" type="java.util.List<projet.data.Groupe>" scope="request"/>

<h1>Liste des groupes présents en BD</h1>

<!-- tableau de groupes -->
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
            <td>
                <button class="btn btn-primary" type="submit" name="ajouterFiltre" value="<%=groupe.getId()%>">
                    <%=groupe.getNom()%>
                </button>
            </td>
            <td><%=groupe.getEtudiants().size()%>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</form>