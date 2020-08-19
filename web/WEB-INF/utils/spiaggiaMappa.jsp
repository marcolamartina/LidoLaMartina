<%@ page import="model.Prenotazione" %>
<style>
    .grid-container {
        overflow-x: scroll;
        display: grid;
        background-color: sandybrown;
        grid-gap: 1px;

    }

    .grid-container > div {
        background-color: white;
        text-align: center;
        border-style: solid;
        border-color: black;
        border-width: 1px;

    }

</style>
<div class="form-group row">
    <label for="date" class="col-2 col-form-label">Data</label>
    <div class="col-10">
        <input class="form-control" type="date" id="date">
    </div>
</div>

<div>
    <h6>Legenda:</h6>
    <i class="fa fa-square" aria-hidden="true" style="color: white; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Libero</small><br />
    <i class="fa fa-square" aria-hidden="true" style="color: yellow; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Prenotato</small><br />
    <i class="fa fa-square" aria-hidden="true" style="color: red; text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;"></i><small> Occupato</small><br />
</div>
<br />

<div id="mappaLido" class="grid-container">
</div>

<div>
    <h6>Sdraio singole rimaste: <small id="sdraioBattigia"><%=Prenotazione.getSdraioMax()%></small></h6>
</div>

<br />
<div id="prezzi">
    <h6>Prezzi:</h6>
    <small>14.00&euro; Giugno</small><br />
    <small>16.00&euro; Luglio</small><br />
    <small>18.00&euro; Agosto</small><br />
    <small>7.00&euro; Sdraio singola</small><br />
</div>

