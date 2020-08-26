<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0); %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Spiaggia</title>
    <link rel="icon" href="img/logo.jpg" type="image/jpg" />

    <!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

    <style>
        .free{
            cursor: pointer;
        }
    </style>
    <script src="js/cliente/spiaggia.js"></script>


</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">

    <div class="jumbotron">
        <h1>Spiaggia</h1>
        <p>Prenota le tue postazioni o delle sdraio singole. Seleziona nella mappa le postazioni che desideri, comprensive ciascuna di un ombrellone e due sdraio. Puoi anche prenotare delle sdraio da mettere in battigia o da aggiungere vicino la postazione, nei limiti dello spazio a disposizione. Appena hai scelto cosa prenotare clicca su <i>"Conferma"</i></p>
        <p>Le prenotazioni effettuate saranno presenti nella sezione <i>Prenotazioni</i> del menu <i>Account</i></p>
    </div>



    <!-- Pannello "Prenotazione" -->

    <form id="formConferma" action="Spiaggia" method="post">
        <!-- Pannello "Postazioni" -->
        <%@ include file="/WEB-INF/utils/spiaggiaMappa.jsp"%>
        <br />
            <div class="form-group">
                <label for="sdraio">Sdraio singole:</label>
                <select class="form-control" id="sdraio" name="sdraio">

                </select>
            </div>
            <div class="form-group">
                <input style="display: none" type="text" class="form-control" id="posti" name="posti">
            </div>
            <h4 id="totale"></h4>
            <button type="submit" id="conferma" class="btn btn-primary btn-sm" >Conferma</button>

    </form>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>