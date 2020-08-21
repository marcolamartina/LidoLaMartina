<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Profilo</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

<!-- JS, Popper.js, and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

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