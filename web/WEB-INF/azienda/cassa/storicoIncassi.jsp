<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Storico incassi</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/azienda/cassa/storicoIncassi.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	<div class="jumbotron">
		<h1>Storico incassi</h1>
		<p>Da questa sezione potrai guardare tutto lo storico degli incassi dello stabilimento</p>
	</div>
	
	<!-- Pannello "Buttons" -->
       <div>
       		<a href="CassaHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
       		<a href="StoricoIncassi" ><button type="submit" class="btn btn-primary" name="commit">Storico incassi</button></a>
       		<a href="LiberaPostazioneCassa" ><button type="submit" class="btn btn-primary" name="commit">Libera postazione</button></a>
            <br />
            <br />
		</div>
	
	<!-- Pannello "Storico incassi" -->
       <div id="incassi">
       		<div class="form-group row">
			  	<label for="date" class="col-2 col-form-label">Data</label>
			  	<div class="col-10">
			    	<input class="form-control" type="date" id="date">
			  	</div>
			</div>
		</div>
		
		<table class="table table-sm table-borderless" id="tabellaBar'+id+'">
			<thead>
				<tr>
					<th>Giorno</th>
					<th>Settimana</th>
					<th>Mese</th>
					<th>Anno</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td id="giorno"></td>
					<td id="settimana"></td>
					<td id="mese"></td>
					<td id="anno"></td>
				</tr>
			</tbody>
		</table>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>