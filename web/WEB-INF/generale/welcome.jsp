<%@ page import="utils.Mailer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Gorgo Beach</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/generale/welcome.js"></script>
<script src="js/generale/spiaggiaMappa.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	
	<div class="jumbotron">
		<h1>Gorgo Beach</h1>
		<p>A pochi km da Cefalù, il lido Gorgo Beach offre un'ottima location, comodi lettini e ombrelloni ampi. Al bar splendide e gustose insalate, panini e quant'altro. Prezzi onesti, siete in Sicilia, ossia in Paradiso.</p>
		<p>Registrati ed effettua l'accesso per potere prenotare le postazioni e le sdraio, ma anche per prenotare cibo e bibite durante la tua visita.</p>	
		<p style="display: inline">Per qualsiasi esigenza </p>
			<div style="display: inline" class="dropdown">
				<a href="" data-toggle="dropdown">contattaci</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="mailto:<%=Mailer.getIndirizzoEmail()%>"><i class="fas fa-envelope mr-3"></i><%=Mailer.getIndirizzoEmail()%></a>
					<a class="dropdown-item" href="tel:+39<%=Mailer.getCellulare()%>"><i class="fas fa-phone mr-3"></i><%=Mailer.getCellulare()%></a>
					<a class="dropdown-item" href="https://www.facebook.com/Lido-Gorgo-Beach-1436330719989547/"><i class="fab fa-facebook-square mr-3"></i>Facebook</a>
					<a class="dropdown-item" href="http://m.me/1436330719989547/"><i class="fab fa-facebook-messenger mr-3"></i>Messenger</a>
					<a class="dropdown-item" href="https://api.whatsapp.com/send?phone=39<%=Mailer.getCellulare()%>"><i class="fab fa-whatsapp mr-3"></i>Whatsapp</a>
				</div>
			</div>
	</div>	
	
	<div id="carousel" class="carousel slide" data-ride="carousel">

	  <!-- Indicators -->
	  <ul class="carousel-indicators">
	    <li data-target="#carousel" data-slide-to="0" class="active"></li>
	    <li data-target="#carousel" data-slide-to="1"></li>
	    <li data-target="#carousel" data-slide-to="2"></li>
	  </ul>
	
	  <!-- The slideshow -->
	  <div class="carousel-inner">
	    <div class="carousel-item active">
	      <img src="img/lidogorgo1.jpg" alt="image_1" style="width:100%; height:100%">
	    </div>
	    <div class="carousel-item">
	      <img src="img/lidogorgo2.jpg" alt="image_2" style="width:100%; height:100%">
	    </div>
	    <div class="carousel-item">
	      <img src="img/lidogorgo3.jpg" alt="image_3" style="width:100%; height:100%">
	    </div>
	  </div>
	
	  <!-- Left and right controls -->
	  <a class="carousel-control-prev" href="#carousel" data-slide="prev">
	    <span class="carousel-control-prev-icon"></span>
	  </a>
	  <a class="carousel-control-next" href="#carousel" data-slide="next">
	    <span class="carousel-control-next-icon"></span>
	  </a>
	
	</div>
	<br />

	<!-- Pannello "Mappa" -->
	<div>
		<h2>Dove siamo</h2>
		<iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3143.4864481018635!2d13.913303650675772!3d38.01243750612397!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x131738756dfc13ef%3A0x7c7bf4e75a2f5888!2slido%20gorgo%20beach!5e0!3m2!1sit!2sit!4v1596644982915!5m2!1sit!2sit" width="100%" height="300" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
	</div>
	<br />
	<br />

	<!-- Pannello "Postazioni" -->
	<h2>La nostra spiaggia</h2>
	<%@ include file="/WEB-INF/utils/spiaggiaMappa.jsp"%>
	<br />
	<br />

	<!-- Pannello "Menu" -->
	<div id="menuWelcome">
        <h2>Il nostro menu</h2>	
        <br />
    </div>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>
