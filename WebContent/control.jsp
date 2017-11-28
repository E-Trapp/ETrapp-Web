<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home - Etrapp</title>

<link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Montserrat|Ubuntu" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
<link rel="stylesheet" href="css/style.css" >

<script>
	var confirmDelete = function(p) {
		if(window.confirm("Estás seguro que deseas borrar?")){
			location.href="DeleteEvent?id=" + p + "";
		}
	}

	var selectCategory = function(p) {
		location.href="control.jsp?id=" + p + "";
	}

	var selectHome = function() {
		location.href="control.jsp";
	}
	
</script>


</head>
<body>

	
	<div class="container-fluid pageControl">
		<div class="row">
			<!--<jsp:include page="control_menu.jsp"></jsp:include>-->
			<%@ include file="control_menu.jsp"%>
			
			<div class="col-2"></div>
			
			<div class="col-10 content">
				<div class="content-fluid">
					<div class="row header">
						<div class="col-11">
							<h1>Events Dashboard</h1>
						</div>
						<!--  <div class="row avatar"> -->
							<div class="col-1">
								<img class="avatarAdmin" src="images/dogavatar.png" />
								<span style="font-size: 0.8rem;">tankintat</span>
								<span style="font-size: 0.8rem;">Logout</span>
							</div>
						<!-- </div> -->
					</div>
					
					<div class="row barmenu">
						<div class="col-12">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="Busco un evento...">
									<span class="input-group-btn">
									<button class="btn btn-secondary" type="button">Go!</button>
								</span>
							</div>
						</div>
					</div>
					
					
					<%
						//conection.connectionOpen();
					
						//List<Event> EventsArray = new ArrayList<Event>();
												
						String idCategory = request.getParameter("id");
						
						String query = "SELECT * FROM Event where visible is true ";
						if(idCategory != null)
							query += "AND categoryid = " + idCategory;
						query+=";";
						
						System.out.println(query);
						//ResultSet rs = conection.stm.executeQuery(query);

// 						while(rs.next()){
// 							Event ev = new Event(
// 								rs.getInt("id"),
// 								rs.getInt("userid"),
// 								rs.getInt("categoryid"),
// 								rs.getString("title"),
// 								rs.getString("eventpic")
// 								,rs.getString("createdate")
// 								,rs.getString("closedate")
// 							);
// 							EventsArray.add(ev);
// 						}
						
// 						conection.connectionClose();
						
						
						//for(int i=0;i<EventsArray.size();i++){
					%>
							<div class="row events" style="background-image: url('images/lleidamanis.jpg');">
								<div class="col-3">
									<div class="eventContent">
<%-- 										<div class="titleEvent"><%=EventsArray.get(i).title%></div> --%>
<%-- 										<div class="dateEvent"><%=EventsArray.get(i).createdate%></div> --%>
									</div>
								</div>
								<div class="col-7">
								</div>
								<div class="col-1 eventButton information">
									<img src="images/information.png" />
									<div class="">
										Views: 123456
									</div>
									<div class="">
										Subs: 1000
									</div>
								</div>
						
							</div>	
				
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>