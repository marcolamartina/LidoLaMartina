<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Nuova Prenotazione</title>
    <link rel="icon" href="img/logo.jpg" type="image/jpg" />

    <!-- CSS only -->
<link rel="stylesheet" href="bootstrap-4.1.3-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="fontawesome-free-5.14.0-web/css/all.css">

<!-- JS, Popper.js, and jQuery -->
<script src="jquery/jquery-3.5.1.min.js"></script>
<script src="bootstrap-4.1.3-dist/js/bootstrap.bundle.min.js"></script>

    <script src="js/cliente/spiaggia.js"></script>
    <script src="js/azienda/reception/nuovaPrenotazione.js"></script>

    <style>
        .free{
            cursor: pointer;
        }
    </style>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">

    <div class="jumbotron">
        <h1>Nuova Prenotazione</h1>
        <p>In questa sezione potrai effettuare una prenotazione per un utente</p>
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

    <!-- Contenitore del form di ricerca account -->
    <div>
        <form class="form-horizontal" action="javascript:searchAccount();">
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
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary" name="commit">Cerca</button>
                </div>
            </div>
        </form>
    </div>


    <!-- Contenitore dei risultati della ricerca -->
    <div id="risultatiRicerca">
        <h2 id="nessunAccount" style="display:none">Non esiste nessun utente che corrisponde ai dati che hai inserito</h2>
        <div id="containerRisultati" >
        </div>
        <br />
        <br />
        <div id="containerSpiaggia" style="display:none">

            <form id="formConferma" action="Prenota" method="post">
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
                <div class="form-group">
                    <input style="display: none" type="text" class="form-control" id="idutente" name="idutente">
                </div>
                <h4 id="totale"></h4>
                <button type="submit" id="conferma" class="btn btn-primary btn-sm" >Conferma</button>

            </form>
        </div>
    </div>



</div>

<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>