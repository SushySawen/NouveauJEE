<%@ page import="projet.data.Etudiant, projet.data.Note, projet.data.Module" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: sophie
  Date: 09/12/2019
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="notesParEtuModule" type="java.util.Map<java.lang.Integer, java.util.Map<java.lang.Integer, projet.data.Note>>" scope="request"/>
<jsp:useBean id="modules" type="java.util.List<projet.data.Module>" scope="request"></jsp:useBean>
<jsp:useBean id="etudiants" type="java.util.List<projet.data.Etudiant>" scope="request"></jsp:useBean>
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
<jsp:include page='<%= application.getInitParameter("entetedepage")%>'></jsp:include>
<table class="table table-striped">
    <thead><tr><th>Etudiant</th><%for (Module module : modules){
        out.print("<th>"+module.getNom()+"</th>");
    }%></tr></thead>
    <tbody>

    <tbody>
    <%
        for (Etudiant etudiant : etudiants){
            out.print("<tr>");
            out.print("<td>"+etudiant.getPrenom()+" "+etudiant.getNom()+"</td>");
            //on récupère la liste des modules qui sont associés a chaque étudiant
            List<Module> modulesDeEtu=  etudiant.getGroupe().getModules();
            //on récupère les notes des etudiants pour chaque module
            Map<Integer, Note> notes = notesParEtuModule.get(etudiant.getId());
            //si les notes existent, on verifiera que l'étudiant est associé a un module pour pouvoir modifier sa note
            if(notes != null) {
                for (Module module : modules) {
                    out.print("<td>");
                    Note note = notes.get(module.getId());
                    //on vérifie que la note existe et que l'étudiant est censé avec une note pour le module
                    if (modulesDeEtu.contains(module)) {
                        String value = note == null ? "-" : Integer.toString(note.getValeur());
                        out.print(value);
                    }
                    out.print("</td>");
                }
                out.print("</td>");
            }  else {
                //si l'étudiant n'a aucune note, on verifie que l'étudiant est associé a un module pour lui ajouter une note
                for (Module module : modules){
                    out.print("<td>");
                    //on vérifie que la note existe et que l'étudiant est censé avec une note pour le module
                    if (modulesDeEtu.contains(module)) {
                        out.print("-");
                    }
                    out.print("</td>");
                }
            }
            out.print("</tr>");
        }
    %>
    </tbody>
</table>

</body>
</html>
