<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Conto</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>


<script src="js/cliente/conto.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	
	<div class="jumbotron">
		<h1>Conto</h1>
		<p>Ecco la sezione dedicata al conto. Puoi vedere il conto di oggi e i conti non ancora pagati di altre giornate</p>
	</div>
	
	<!-- Pannello "Conti" -->
       <div id="conto">
       		<h2 id="contoVuoto" style="display:none">Non ci sono conti aperti</h2>
            <br />
            
		</div>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>