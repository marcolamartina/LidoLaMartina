<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader ("Expires", 0); %>
<%@ page import="model.Prenotazione" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.text.DecimalFormat" %>
<style>
    .grid-container {
        overflow-x: scroll;
        display: grid;
        background-color: #F2D16B;
        grid-gap: 1px;

    }

    .grid-container > div {
        background-color: white;
        text-align: center;
        border-style: solid;
        border-color: black;
        border-width: 1px;

    }

    .right{
        float: right;
        width: 50%;
        text-align: right;
    }

    .left{
        float: left;
        width: 50%;
        text-align: left;
    }

</style>
<div class="form-group row">
    <label for="date" class="col-2 col-form-label">Data</label>
    <div class="col-10">
        <input class="form-control" type="date" id="date" name="date">
    </div>
</div>

<div style="display: flex">
    <div id="legenda" class="left">
        <h6>Legenda</h6>
        <i class="fa fa-square" aria-hidden="true" style="color: white; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Libero</small><br />
        <i class="fa fa-square" aria-hidden="true" style="color: yellow; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Prenotato</small><br />
        <i class="fa fa-square" aria-hidden="true" style="color: red; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Occupato</small><br />
    </div>

    <div id="prezzi" class="right">
        <h6>Prezzi</h6>
        <% DecimalFormat dec = new DecimalFormat("#0.00");%>
        <small>Giugno <%=dec.format(Prenotazione.getPrezzoPostazione(LocalDate.of(2020,6,1)))%>&euro;</small><br />
        <small>Luglio <%=dec.format(Prenotazione.getPrezzoPostazione(LocalDate.of(2020,7,1)))%>&euro;</small><br />
        <small>Agosto <%=dec.format(Prenotazione.getPrezzoPostazione(LocalDate.of(2020,8,1)))%>&euro;</small><br />
        <small>Settembre <%=dec.format(Prenotazione.getPrezzoPostazione(LocalDate.of(2020,9,1)))%>&euro;</small><br />
        <small>Sdraio singola &nbsp;&nbsp;<%=dec.format(Prenotazione.getPrezzoSdraio())%>&euro;</small><br />
    </div>
</div>
<br />

<div id="mappaLido" class="grid-container">
</div>

<div>
    <h6>Sdraio singole rimaste: <small id="sdraioBattigia"><%=Prenotazione.getSdraioMax()%></small></h6>
</div>
<br />
<br />



