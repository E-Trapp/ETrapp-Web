<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
          integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link rel="stylesheet" href="css/style.css">


</head>
<body class="homePage">
<div class="container logoField">
    <div class="row">
        <div class="col">
            <img class="logoHome" src="images/logo_etrapp.png"/>
            <br/><br/><br/>

            <form method="POST" action="v1/auth">
                <div class="form-group">
                    <label for="lblUsername">Username: </label>
                    <input type="username" class="form-control" name="username" aria-describedby="username"
                           placeholder="Enter your username">
                </div>
                <div class="form-group">
                    <label for="lblPassword">Password</label>
                    <input type="password" class="form-control" name="password" placeholder="Enter your Password">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>