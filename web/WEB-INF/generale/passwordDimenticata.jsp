<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Password dimenticata</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/generale/signin.js"></script>

</head>
<body>
	<%@ include file="/WEB-INF/utils/header.jsp"%>
	<div class="container">
		
		<div class="jumbotron">
			<h1>Password dimenticata</h1>
			<p>Inserire l'indirizzo email per cui si vuole recuperare la password. Sarà inviata una mail contenente un link per potere impostare una nuova password</p>
		</div>
	
	<!-- Contenitore del form di modifca dei dati -->
		<div>
			<form class="form-horizontal" action="PasswordDimenticata" method="post">
				<div class="form-group">
	      			<label class="control-label col-sm-2" for="email">Email:</label>
	      			<div class="col-sm-10">
	        			<input type="email" class="form-control" id="email" placeholder="Inserisci l'indirizzo email" name="email" oninput="validateEmail()" required="required">
	      			</div>
	    		</div>
	    		
	    		<div class="form-group">        
	      			<div class="col-sm-offset-2 col-sm-10">
	        			<button type="submit" class="btn btn-primary" name="commit">Invia</button>
	      			</div>
	    		</div>
	  		</form>
	  	</div>	
	</div>
	
	
	
	<% if(session.getAttribute("email")!=null){ %>
		<script>$("#email").val("<%= session.getAttribute("email").toString()%>")</script>
		<% 	session.removeAttribute("email");
		} %>

	<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>