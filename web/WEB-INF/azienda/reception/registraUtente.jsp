<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registra Utente</title>
    <link rel="icon" href="img/logo.jpg" type="image/jpg" />

    <!-- CSS only -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

    <!-- JS, Popper.js, and jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

    <script src="js/generale/signin.js"></script>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">

    <div class="jumbotron">
        <h1>Registra utente</h1>
        <p>In questa sezione potrai registrare un nuovo utente</p>
    </div>

    <!-- Pannello "Buttons" -->
    <div>
        <a href="ReceptionHome" ><button type="submit" class="btn btn-primary" name="commit"><i class="fa fa-home"></i> Home</button></a>
        <a href="NuovaPrenotazione" ><button type="submit" class="btn btn-primary" name="commit">Prenota</button></a>
        <a href="RegistraUtente" ><button type="submit" class="btn btn-primary" name="commit">Registra utente</button></a>
        <a href="LiberaPostazioneReception" ><button type="submit" class="btn btn-primary" name="commit">Libera postazione</button></a>
        <br />
        <br />
    </div>

    <!-- Contenitore del form di registrazione -->
    <div>
        <form class="form-horizontal" action="RegistraUtente" method="post">
            <div class="form-group">
                <label class="control-label col-sm-2" for="nome">Nome:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="nome" placeholder="Inserisci il nome" name="nome" oninput="validateName()" required="required">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="cognome">Cognome:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="cognome" placeholder="Inserisci il cognome" name="cognome" oninput="validateSurname()" required="required">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="cellulare">Cellulare:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="cellulare" placeholder="Inserisci il numero di cellulare" name="cellulare" oninput="validateCel()" required="required">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="email">Email:</label>
                <div class="col-sm-10">
                    <input type="email" class="form-control" id="email" placeholder="Inserisci l'indirizzo email" name="email" oninput="validateEmail()" required="required">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="password">Password:</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="password" placeholder="Inserisci la password" name="password" oninput="validatePassword()" autocomplete="new-password" required="required">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2" for="password_conf">Conferma password:</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="password_conf" placeholder="Conferma la password" name="password_conf" oninput="validatePasswordConf()" autocomplete="new-password" required="required">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary" name="commit">Conferma</button>
                </div>
            </div>
        </form>
    </div>
</div>



<% if(session.getAttribute("nome")!=null){ %>
<script>$("#nome").val("<%= session.getAttribute("nome").toString()%>")</script>
<% 	session.removeAttribute("nome");
} %>

<% if(session.getAttribute("cognome")!=null){ %>
<script>$("#cognome").val("<%= session.getAttribute("cognome").toString()%>")</script>
<% 	session.removeAttribute("cognome");
} %>

<% if(session.getAttribute("email")!=null){ %>
<script>$("#email").val("<%= session.getAttribute("email").toString()%>")</script>
<% 	session.removeAttribute("email");
} %>

<% if(session.getAttribute("cellulare")!=null){ %>
<script>$("#cellulare").val("<%= session.getAttribute("cellulare").toString()%>")</script>
<% 	session.removeAttribute("cellulare");
} %>

<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>