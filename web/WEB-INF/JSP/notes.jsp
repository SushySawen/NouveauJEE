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

<h1>Notes des étudiants</h1>
<form method="POST" action="<%=application.getContextPath()%>/do/notes">
    <table class="table table-striped">
        <thead><tr><th>Etudiant</th><%for (Module module : modules){
            out.print("<th>"+module.getNom()+"</th>");
        }%></tr></thead>
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
                            out.print("<input type=\"hidden\" name=\"etuId\" value=\"" + etudiant.getId() + "\">");
                            out.print("<input type=\"hidden\" name=\"modId\" value=\"" + module.getId() + "\">");

                            String value = note == null ? "" : Integer.toString(note.getValeur());
                            out.print("<input type=\"number\" min=\"0\" max=\"20\" name=\"valeur\" value=\""
                                    + value + "\">");
                        }
                        out.print("</td>");
                    }
                    out.print("</td>");
                }  else {
                    //si l'étudiant n'a aucune note, on verifie que l'étudiant est associé a un module pour lui ajouter une note
                    for (Module module : modules){
                        out.print("<td>");
                        if (modulesDeEtu.contains(module)){
                            out.print("<input type=\"hidden\" name=\"etuId\" value=\"" + etudiant.getId() + "\">");
                            out.print("<input type=\"hidden\" name=\"modId\" value=\"" + module.getId() + "\">");
                            out.print("<input type=\"number\" min=\"0\" max=\"20\" name=\"valeur\">");
                        }
                        out.print("</td>");
                    }
                }
                out.print("</tr>");
            }
        %>
        </tbody>
    </table>
    <input type="submit" value="Enregistrer les modifications">
</form>