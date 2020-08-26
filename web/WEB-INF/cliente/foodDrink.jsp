<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0); %>
    
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Food & Drink</title>
<link rel="icon" href="img/logo.jpg" type="image/jpg" />

<!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>


<script src="js/cliente/fooddrink.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">
	
	<div class="jumbotron">
		<h1>Food & Drink</h1>
		<p>Aggiungi al carrello tutto ciò che desideri, conferma l'ordine dal carrello e poi paga tranquillamente in cassa</p>
	</div>
	
	
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
		
		<!-- The Modal -->
		<div class="modal" id="modalSucc">
  			<div class="modal-dialog">
    			<div class="modal-content">

      				<!-- Modal Header -->
      				<div class="modal-header">
        				<h4 class="modal-title">Aggiunto al carrello</h4>
        				<button type="button" class="close" data-dismiss="modal">&times;</button>
      				</div>

      				<!-- Modal body -->
      				<div class="modal-body">
        				Il prodotto è stato aggiunto al carrello
      				</div>

      				<!-- Modal footer -->
      				<div class="modal-footer">
        				<button type="button" class="btn btn-success" data-dismiss="modal">Chiudi</button>
      				</div>

    			</div>
  			</div>
		</div>
</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>