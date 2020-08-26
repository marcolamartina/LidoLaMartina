<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Storico pagamenti</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/cliente/storicoPagamenti.js"></script>
<style>

</style>
</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">

	
	<div class="jumbotron">
		<h1>Storico pagamenti</h1>
		<p>Puoi vedere lo storico dei tuoi pagamenti</p>
	</div>

		

	<!-- Pannello "Storico Pagamenti" -->
       <div id="storicoPagamenti">
       		<h2 id="storicoVuoto" style="display:none">Non hai ancora effettuato nessun acquisto nel nostro lido</h2>
            <br />
            
		</div>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>