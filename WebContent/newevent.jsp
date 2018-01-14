<%@ page import="cat.udl.etrapp.server.daos.CategoriesDAO" %>
<%@ page import="cat.udl.etrapp.server.models.Category" %>
<%@ page import="cat.udl.etrapp.server.daos.EventsDAO" %>
<%@ page import="cat.udl.etrapp.server.models.Event" %>
<%@ page import="cat.udl.etrapp.server.models.User" %>
<%@ page import="cat.udl.etrapp.server.daos.UsersDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css" >
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <title>Events</title>
</head>
<body>
<nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="/etrapp-server">ETrapp Admin</a>

    <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/etrapp-server">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/etrapp-server/categories.jsp">Categories</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/etrapp-server/events.jsp">Events</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <h1>New Event</h1> <br>

    <form method="POST" action="/etrapp-server/v1/events">
        <div class="form-group">
            <input type="text"
                   class="form-control"
                   name="title"
                   placeholder="Title" />

            <input type="text"
                   class="form-control"
                   name="desc"
                   placeholder="Description" />

            <input type="text"
                   class="form-control"
                   name="loc"
                   placeholder="Location" />

            <input type="datetime-local"
                   class="form-control"
                   name="starts"
                   placeholder="Starts at" />

        </div>
        <button type="submit" class="btn btn-success">Create</button>
    </form>
</div>
</body>
</html>
