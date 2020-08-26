<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Profilo</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<%@ include file="/WEB-INF/utils/header.jsp"%>
	<div class="container">
		
		<div class="jumbotron">
			<h1>Profilo</h1>
		</div>
	
	<!-- Contenitore delle informazioni personali -->
		<div>
			<% Account account= (Account)session.getAttribute("account");%>
			<h4>Nome:</h4><%= account.getUtente().getNome() %>
			<hr />
			<h4>Cognome:</h4><%= account.getUtente().getCognome() %>
			<hr />
			<h4>Email:</h4><%= account.getUtente().getEmail() %>
			<hr />
			<h4>Cellulare:</h4><%= account.getUtente().getCellulare() %>
			<hr />
			<a href="ModificaDati"><button class="btn btn-primary">Modifica dati</button></a>
			<a href="ModificaPassword"><button class="btn btn-primary">Modifica password</button></a>
	  	</div>	
	</div>
	<% if(session.getAttribute("email")!=null){ %>
		<script>$("#email").val("<%= session.getAttribute("email").toString()%>")</script>
		<% 	session.removeAttribute("email");
		} %>


	<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>