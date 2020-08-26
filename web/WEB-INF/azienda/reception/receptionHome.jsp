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

    <script src="js/azienda/reception/receptionHome.js"></script>
    <style>
        .btn-occupata{
            background-color: red;
            border-color: black;
        }

        .btn-prenotata{
            background-color: yellow;
            border-color: black;
        }


         .occupata{
             cursor: pointer;
         }

        .prenotata{
            cursor: pointer;
        }
    </style>

</head>
<body>
<%@ include file="/WEB-INF/utils/header.jsp"%>
<div class="container">

    <div class="jumbotron">
        <h1>Ciao ${account.utente.nome}!</h1>
        <p>Da questa sezione potrai vedere lo stato delle postazioni, registrare l'arrivo dei clienti o liberare le postazioni. Potrai anche registrare nuovi clienti così da poter effettuare per loro nuove prenotazioni</p>
        </p>
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

    <!-- Pannello "Postazioni" -->
    <%@ include file="/WEB-INF/utils/spiaggiaMappa.jsp"%>

    <!-- Pannello "Cerca" -->
    <input class="form-control" id="search" type="text" placeholder="Cerca..">
    <br />
    <table id="spiaggiaTable" class="table table-borderless">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Sdraio</th>
            <th>Postazioni</th>
        </tr>
        </thead>
        <tbody id="spiaggiaTableBody">
        </tbody>
        <div id="modals">
        </div>
    </table>

</div>
<%@ include file="/WEB-INF/utils/footer.jsp"%>
</body>
</html>