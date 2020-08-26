<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0); %>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">


  
<style>
	label { display: inline }
	button { margin: 2px}
</style>

<script>
	function notify(flag, title, text) { // flag = 0 (info) 1 (success) 2 (error) 
	  	var classe;
		var code; 
		if(flag==0){
			classe="alert alert-info alert-dismissible";
		}else if(flag==1){
			classe="alert alert-success alert-dismissible";
		}else{
			classe="alert alert-danger alert-dismissible";
		}
		code="<div class=\""+classe+"\"> <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button><strong>"+title+" </strong>"+text+"</div>";
		$("#notify").html(code);
	}
</script>

</head>
<body>
	<%@ page import="model.Notify" %>
	<%@ page import="model.Account" %>
	<%@ page import="model.Carrello" %>
		<nav class="navbar navbar-expand-sm bg-white navbar-light fixed-top">
		<div class="container">
			<a class="navbar-brand" href="Homepage" ><img src="img/logogorgo.jpg" alt="logo-gorgo-beach" title="Logo"/></a>
			
			<!-- Collapse button -->
  				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar"
    				aria-controls="navbar" aria-expanded="false" aria-label="Toggle navigation">
    				<span class="navbar-toggler-icon"></span>
  				</button>
  				
  			<!-- Collapsible content -->
  				<div class="collapse navbar-collapse" id="navbar">
  	
				<ul class="nav navbar-nav mr-auto" >
				
				<% if (session.getAttribute("account")!=null) { 
						Account account=(Account)session.getAttribute("account");
						if (account.getRuoli()!=null && account.getRuoli().keySet().size()>1) { %>
							<li class="nav-item dropdown">
							<a class="nav-link" href="#" data-toggle="dropdown"><i class="fa fa-cog"></i> Ruolo</a>
								<div class="dropdown-menu">
							<% for(String i: account.getRuoli().keySet() ){ %>
								<a class="dropdown-item" href="<%=i+"Home"%>"><%=i%></a>
							<% } %>
								</div>
							</li>
						<% } else { %>
							<li class="nav-item"><a class="nav-link" href="ClienteHome"><i class="fa fa-home"></i> Home</a></li> 
					    <% }  %>
				    	<li class="nav-item dropdown">
						    <a class="nav-link" href="#" data-toggle="dropdown"><i class="fa fa-user"></i> Account</a>
						    <div class="dropdown-menu">
							  <small><span class="dropdown-item-text disabled">${account.utente.nome} ${account.utente.cognome}</span></small>
						      <div class="dropdown-divider"></div>
						      <a class="dropdown-item" href="Conto">Conto</a>
							  <a class="dropdown-item" href="Prenotazioni">Prenotazioni</a>
						      <a class="dropdown-item" href="StoricoPagamenti">Storico pagamenti</a>
						      <a class="dropdown-item" href="Profile">Profilo</a>
						      <a class="dropdown-item" href="Logout">Logout</a>
					
						    </div>
						</li>
						<li class="nav-item"><a class="nav-link" href="Carrello"><i class="fa fa-shopping-cart"></i> Carrello
						<% if (session.getAttribute("carrello")!=null) { 
							Carrello carrello=(Carrello)session.getAttribute("carrello"); 
							int numero=carrello.numeroProdotti(); %>
							<span class="badge badge-pill badge-primary" id="counter"><%=numero%></span>
						<% } %>	
						</a></li>
						
						
    			<% } else { %>
    				<li class="nav-item dropdown">
						    <a class="nav-link" href="#" data-toggle="dropdown"><i class="fa fa-user"></i> Account</a>
						   
						    <div class="dropdown-menu">
						      <a class="dropdown-item" href="Login">Accedi</a>
						      <a class="dropdown-item" href="Signin">Registrati</a>
						    </div>
						</li>
    			
    			
    			<% } %>
				
				</ul>
			</div>
			</div>
		</nav>
	<br />
	<br />
	<br />
	<br />
	
	<div class="container" id="notify">
		<% if (session.getAttribute("notify")!=null) {
		    	Notify notifica=(Notify)session.getAttribute("notify");%>
		        <script>notify(<%=notifica.getFlag()%>,"<%=notifica.getTitle()%>","<%=notifica.getText()%>")</script>
    		<% session.removeAttribute("notify");
    		}
    	%>
	</div>
	
	<div class="modal" id="modalErr">
  			<div class="modal-dialog">
    			<div class="modal-content">

      				<!-- Modal Header -->
      				<div class="modal-header">
        				<h4 class="modal-title">Errore</h4>
        				<button type="button" class="close" data-dismiss="modal">&times;</button>
      				</div>

      				<!-- Modal body -->
      				<div class="modal-body">
        				C'è stato un errore imprevisto
      				</div>

      				<!-- Modal footer -->
      				<div class="modal-footer">
        				<button type="button" class="btn btn-danger" data-dismiss="modal">Chiudi</button>
      				</div>

    			</div>
  			</div>
		</div>

</body>
</html>