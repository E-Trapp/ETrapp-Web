<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
	
	
	<div class="col-2 menu">
		<div class="row">
			<div class="col">
				<img class="logoControl" src="images/logo_etrapp.png" />
			</div>
		</div>
		<div class="row menuTitle">
			<div class="col">
				ETRAPP ADMIN
			</div>
		</div>
				
		<div class="row menuLinks" onclick="selectHome()">
			<div class="col">
				<img class="menuImg" src="images/news.png" />   Events Dashboard
			</div>
		</div>
		<div class="row menuLinks">
			<div class="col">
				<img class="menuImg" src="images/statistics.png" />   Statistics Dashboard
			</div>
		</div>
		<div class="row menuTitle">
			<div class="col">
				Categories
			</div>
		</div>
		
		<%
			//conectionMenu.connectionOpen();
			
			//List<Category> CategoryArray = new ArrayList<Category>();	
// 			String queryMenu = "SELECT * FROM Category;";
// 			System.out.println(queryMenu);
// 			ResultSet resultMenu = conectionMenu.stm.executeQuery(queryMenu);
	
// 			while(resultMenu.next()){
// 				Category cat = new Category(
// 					resultMenu.getInt("id"),
// 					resultMenu.getString("title")
// 				);
// 				CategoryArray.add(cat);
// 			}
			
// 			conectionMenu.connectionClose();
			
			
// 			for(int i=0;i<CategoryArray.size();i++){
		%>
			<div class="row menuLinks" onclick="selectCategory(0)">
				<div class="col">
<%-- 					<%=CategoryArray.get(i).title%> --%>
				</div>
			</div>
<%-- 		<% --%>
// 			}
<%-- 		%> --%>
		
				
	</div>

