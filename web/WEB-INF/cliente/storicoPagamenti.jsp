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
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

<!-- JS, Popper.js, and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

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