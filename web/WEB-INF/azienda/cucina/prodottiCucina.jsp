<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Conferma ordinazione</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

<script src="js/azienda/bar/prodotti.js"></script>
<script>
    $(document).ready(function(){
        init("Cucina")
    });
</script>

<style>
	.line-through { text-decoration:line-through; }
</style>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	
	<div class="jumbotron">
		<h1>Ingredienti</h1>
		<p>Puoi rimuovere e ripristinare dal menu i prodotti non presenti in cucina o di cui non sono disponibili tutti gli ingredienti</p>
	</div>

    <!-- Pannello "Buttons" -->
    <div>
        <a href="CucinaHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
        <a href="ProdottiCucina" ><button type="submit" class="btn btn-primary" name="commit">Prodotti</button></a>
        <br />
        <br />
    </div>
	
	<!-- Pannello "ProdottiBar" -->
       <div>
            <label for="selCategoria">Categoria: </label>
            <select class="form-control" id="selCategoria" name="categoria"></select>
            <br />
            <input class="form-control" id="search" type="text" placeholder="Cerca..">
        </div>
        <br />
        <br />
        <div id="menu">
        	
        </div>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>