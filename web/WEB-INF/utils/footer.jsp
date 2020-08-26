<%@ page import="utils.Mailer" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0); %>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
</head>
<body>

	<div class="container">
		<br />
		<br />
		<br />
		<div class="jumbotron small">
			<footer class="text-center">
				<!-- Grid row -->
				<div class="row">

                    <!-- Grid column -->
                    <div class="col-sm-6 mb-sm-0 mb-3 text-left">

                        <!-- Content -->
                        <!-- Links -->
                        <h6 class="text-uppercase font-weight-bold">Contatti</h6>

                        <p><i class="fas fa-map-marker-alt mr-3"></i><a href="https://www.google.com/maps/dir//38.0124333,13.9154977/@38.012433,13.915498,16z?hl=it"> Via Gorgo Lungo, 23, 90010 Gorgo Lungo PA</a></p>
                        <p><i class="fas fa-envelope mr-3"></i><a href="mailto:<%=Mailer.getIndirizzoEmail()%>"><%=Mailer.getIndirizzoEmail()%></a></p>
                        <p><i class="fas fa-phone mr-3"></i><a href="tel:+39<%=Mailer.getCellulare()%>"><%=Mailer.getCellulare()%></a></p>

                    </div>
                    <!-- Grid column -->

                    <hr class="clearfix w-100 d-sm-none pb-3">

                    <!-- Grid column -->
                    <div class="col-sm-3 mb-sm-0 mb-3 text-left">

                        <!-- Content -->
                        <!-- Links -->
                        <h6 class="text-uppercase font-weight-bold">Social</h6>

                        <p><i class="fab fa-facebook-square mr-3"></i><a href="https://www.facebook.com/Lido-Gorgo-Beach-1436330719989547/">Facebook</a></p>
                        <p><i class="fab fa-facebook-messenger mr-3"></i><a href="http://m.me/1436330719989547/">Messenger</a></p>
                        <p><i class="fab fa-whatsapp mr-3"></i><a href="https://api.whatsapp.com/send?phone=39<%=Mailer.getCellulare()%>">Whatsapp</a></p>

                    </div>
                    <!-- Grid column -->

                    <hr class="clearfix w-100 d-sm-none pb-3">

					<!-- Grid column -->
					<div class="col-sm-3 mt-sm-0 mt-3 text-left">

						<!-- Content -->
						<h6 class="text-uppercase font-weight-bold">Orari di apertura</h6>
						<p>Tutti i giorni: 8:00-20:00</p>
						<p>Da Giugno a Settembre</p>

					</div>
					<!-- Grid column -->





				</div>
				<!-- Grid row -->

		        <!-- Footer Text -->
                <br />
		        <p>Powered by Marco La Martina</p>
		        <p>© 2020 Copyright</p>
			</footer>
		</div>
	</div>


</body>
</html>