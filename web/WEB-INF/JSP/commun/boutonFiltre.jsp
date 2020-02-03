<%--
  Created by IntelliJ IDEA.
  User: sophie
  Date: 02/02/2020
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="groupe" type="java.lang.Integer" scope="session"/>
<html>
<head>
    <%
        if (groupe != null) {
    %>
    <form>
        <button class="btn btn-danger" type="submit" name="supprimerFiltre" value="true">Supprimer le choix de groupe
        </button>
    </form>
    <%
        }
    %>
    <title>BoutonFiltre</title>
</head>

<body>
</body>
</html>
