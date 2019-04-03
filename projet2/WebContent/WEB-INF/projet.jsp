<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" %>
<%@
page import="java.util.ArrayList,java.util.List"
 %>
<% 
List<String[]>  attribut = (List<String[]>) request.getAttribute("donnees");
%>
<p>Veuillez entrer les codes communes de 2 communes pour calculer la distance :</p>
<form method="post" action="calculdistance">
	<input type="text" id="ville1" name="ville1">
	<input type="text" id="ville2" name="ville2">
	<input type="submit" value="Submit" class="sansLabel" />
</form>


<table>
	<tr>
		<th>Code commune</th>
		<th>Nom commune</th>
		<th>Code postal</th>
		<th>Libelle acheminement</th>
		<th>Ligne 5</th>
		<th>Coordonnées GPS</th>
	</tr>
<%
for (int i = 0; i < attribut.size(); i++){
	String codeC = attribut.get(i)[1];
	String nomC = attribut.get(i)[2];
	String codeP = attribut.get(i)[3];
	String libellA = attribut.get(i)[4];
	String ligne5 = attribut.get(i)[5];
	String coordGPS = attribut.get(i)[6];
	%>
	<tr>
	  <td><%=codeC %></td>
	  <td><%=nomC %></td>
	  <td><%=codeP %></td>
	  <td><%=libellA %></td>
	  <td><%=ligne5 %></td>
	  <td><%=coordGPS %></td>
	</tr>
	
	<%
}

%>
</table>