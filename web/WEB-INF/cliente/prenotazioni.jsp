<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0); %>

<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Prenotazioni</title>
    <link rel="icon" href="img/logo.jpg" type="image/jpg" />

    <!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

    <script src="js/cliente/prenotazioni.js"></script>
    <style>

    </style>
</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">


    <div class="jumbotron">
        <h1>Prenotazioni</h1>
        <p>Puoi vedere ed eliminare le tue prenotazioni</p>
    </div>



    <!-- Pannello "Prenotazioni" -->
    <div id="prenotazioni">
        <h2 id="prenotazioniVuote" style="display:none">Non hai ancora effettuato nessuna prenotazione</h2>
        <br />
    </div>

    <!-- The Modal -->
    <div class="modal" id="modalSucc">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Prenotazione eliminata</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    La prenotazione è stata eliminata correttamente
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