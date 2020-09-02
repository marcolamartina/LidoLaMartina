*
ALTER TABLE Utente AUTO_INCREMENT = 1;
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Marco', 'Amministratore', 'marcoamministratore@gmail.com', MD5('Password1'), '3270000000');
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Mariella', 'Cassiera', 'mariellacassiera@gmail.com', MD5('Password1'), '3270000000');
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Luca', 'Bagnino', 'lucabagnino@gmail.com', MD5('Password1'), '3270000000');
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Maria', 'Barista', 'mariabarista@gmail.com', MD5('Password1'), '3270000000');
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Francesco', 'Cameriere', 'francescocameriere@gmail.com', MD5('Password1'), '3270000000');
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Barbara', 'Cuoca', 'barbaracuoca@gmail.com', MD5('Password1'), '3270000000');
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Pasquale', 'Receptionist', 'pasqualereceptionist@gmail.com', MD5('Password1'), '3270000000');
insert into Utente (Nome, Cognome, Email, Password, Cellulare) VALUES ('Davide', 'Cliente', 'davidecliente@gmail.com', MD5('Password1'), '3270000000');
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Cassa', 2);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Bagnino', 3);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Bar', 4);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Cameriere', 5);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Cucina', 6);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Reception', 7);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Admin', 1);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Bagnino', 1);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Bar', 1);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Cameriere', 1);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Cassa', 1);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Cucina', 1);
insert into AccountAziendale (Ruolo, Utente_IDUtente) VALUES ('Reception', 1);



ALTER TABLE Prodotto AUTO_INCREMENT = 1;
INSERT INTO Prodotto (Nome, Prezzo, Categoria, Ingredienti) VALUES
('Antipasto della casa', 10.00, 'Antipasti', 'Salumi, formaggi e bruschette miste'),
('Bruschette miste (10pz.)', 5.00, 'Antipasti', 'Bruschette miste con pomodoro, patè vari, salumi e formaggi'),
('Gorgo Beach', 5.00, 'Panini', 'Hamburger, pomodoro, mozzarella, cipolla, insalata verde, ketchup'),
('Claudia', 5.00, 'Panini', 'Prosciutto crudo, insalata verde, mozzarella, olio, sale, origano'),
('Francy', 5.00, 'Panini', 'Tonno, pomodoro, maionese, lattuga'),
('Caprese', 5.00, 'Panini', 'Pomodoro, mozzarella, basilico, olio, sale, pepe'),
('Cartoccio', 5.00, 'Panini', 'Mozzarella, prosciutto cotto, salsa rosa'),
('Piccante', 5.00, 'Panini', 'Salame piccante, sottiletta, tabasco, ketchup'),
('Hot Dog', 5.00, 'Panini', 'Wurstel, ketchup'),
('Semplice', 5.00, 'Panini', 'Prosciutto cotto, mozzarella, pomodoro, olio, sale'),
('Corallo', 5.00, 'Panini', 'Prosciutto crudo, mozzarella, pomodoro, salsa rosa'),
('Saporito', 5.00, 'Panini', 'Salame piccante, cipolla, pomodoro, mozzarella, maionese'),
('Topolino', 5.00, 'Panini', 'Mozzarella, prosciutto cotto, wurstel, salsa rosa'),
('Salmone', 5.00, 'Panini', 'Salmone, rucola, mozzarella, salsa rosa'),
('Vegetariano', 5.00, 'Panini', 'Verdure grigliate, maionese, emmental'),
('Particolare', 5.00, 'Panini', 'Tonno, lattuga, uovo sodo, pomodoro, emmental, senape');

INSERT INTO Prodotto (Nome, Prezzo, Categoria) VALUES
('Sottiletta e prosciutto cotto', 2.50, 'Toast'),
('Mozzarella e prosciutto crudo', 3.00, 'Toast'),
('Salmone, mozzarella e rucola', 3.00, 'Toast'),
('Tonno, mozzarella e lattuga', 3.00, 'Toast');


INSERT INTO Prodotto (Nome, Prezzo, Categoria, Ingredienti) VALUES
('Gorgo Beach', 5.00, 'Piadine', 'Prosciutto crudo, mozzarella, pomodoro, olio piccante'),
('Semplice', 5.00, 'Piadine', 'Prosciutto cotto, mozzarella'),
('Caprese', 5.00, 'Piadine', 'Pomodoro, mozzarella, basilico'),
('Vegetariano', 5.00, 'Piadine', 'Verdure miste grigliate, mozzarella, maionese'),
('Salmone', 5.00, 'Piadine', 'Salmone, mozzarella, rucola, salsa rosa'),


('Caprese', 7.00, 'Insalate', 'Pomodoro, mozzarella, basilico'),
('Caprese Fantasia', 8.50, 'Insalate', 'Pomodoro, mozzarella, basilico, cipolla, carote, rucola'),
('Salmone', 12.00, 'Insalate', 'Salmone, pomodoro, mozzarella, basilico'),
('Verde', 6.50, 'Insalate', 'Lattuga, mais, carote, pomodoro'),
('Tonno', 8.50, 'Insalate', 'Tonno, lattuga, pomodoro, cipolla'),
('Sorpesa', 10.00, 'Insalate', 'A umore dello chef'),
('Insalata di mare', 10.00, 'Insalate', 'Misto di pesce');


INSERT INTO Prodotto (Nome, Prezzo, Categoria) VALUES
('Caffè caldo', 1.00, 'Caffetteria'),
('Caffè freddo', 1.50, 'Caffetteria'),
('Caffè corretto', 1.50, 'Caffetteria'),
('Cappuccino', 2.00, 'Caffetteria'),
('Cornetto', 1.00, 'Caffetteria'),
('Sfogliatina', 1.50, 'Caffetteria'),


('Acqua 50cl', 1.00, 'Bibite'),
('Lemon soda 33cl', 2.50, 'Bibite'),
('Chinotto 33cl', 2.50, 'Bibite'),
('Fanta 33cl', 2.50, 'Bibite'),
('Sprite 33cl', 2.50, 'Bibite'),
('Coca Cola 33cl', 2.50, 'Bibite'),
('Thé pesca/limone 33cl', 2.50, 'Bibite'),
('Succhi di frutta 33cl', 2.50, 'Bibite'),
('Red Bull 33cl', 3.50, 'Bibite'),
('Crodino 33cl', 2.50, 'Bibite'),
('Bitter bianco/rosso 33cl', 2.50, 'Bibite'),
('Campari Soda 33cl', 3.00, 'Bibite'),
('Aperol Soda 33cl', 3.00, 'Bibite'),
('Aperol 33cl', 3.50, 'Bibite'),
('Martini bianco/rosso 33cl', 3.50, 'Bibite'),


('Bottiglia 75cl', 25.00, 'Prosecco'),
('Bicchiere', 5.00, 'Prosecco'),


('Bottiglia 75cl', 25.00, 'Vini'),
('Bicchiere', 5.00, 'Vini'),


('Ichnusa 33cl', 3.00, 'Birre'),
('Peroni 33cl', 2.00, 'Birre'),
('Moretti 33cl', 2.00, 'Birre'),
('Peroni Chill Lemon 33cl', 2.50, 'Birre'),
('Messina Cristalli di Sale 33cl', 3.00, 'Birre'),
('Ceres 33cl', 4.00, 'Birre'),
('Corona 33cl', 5.00, 'Birre'),
('Tennent\'s 33cl', 5.00, 'Birre'),
('Bud 33cl', 4.00, 'Birre'),
('Pilsner Urquell 33cl', 3.00, 'Birre'),
('Forst Felsenkeller 33cl', 3.00, 'Birre'),
('Goose Island IPA 33cl', 5.00, 'Birre'),
('Peroni Forte 33cl', 3.50, 'Birre'),

('Gin Tonic', 5.00, 'Long Drink'),
('Campari Orange', 5.00, 'Long Drink'),
('Rum e Cola', 5.00, 'Long Drink'),
('Jack Daniels e Coca', 5.00, 'Long Drink');


INSERT INTO Prodotto (Nome, Prezzo, Categoria, Ingredienti) VALUES
('Mojito', 5.00, 'Pestati', 'Rum scuro, lime a pezzi, zucchero di canna'),
('Caipirinha', 5.00, 'Pestati', 'Cachaça, lime a pezzi, zucchero di canna'),
('Caipiroska', 5.00, 'Pestati', 'Vodka fragola, lime a pezzi, zucchero di canna'),


('Negroni', 5.00, 'Pre Dinner', 'Campari, Martini rosso, gin'),
('Americano', 5.00, 'Pre Dinner', 'Campari, Martini rosso, Soda'),
('Negroni sbagliato', 5.00, 'Pre Dinner', 'Campari, Martini rosso, prosecco'),
('Aperol Spritz', 5.00, 'Pre Dinner', 'Aperol, prosecco, soda'),


('Moscow Mule', 5.00, 'Cocktail', 'Vodka, succo di lime, ginger beer'),
('Long Island', 5.00, 'Cocktail', 'Rum, vodka, triple sec, gin, sweet sour, coca cola'),
('Cosmopolitan', 5.00, 'Cocktail', 'Vodka, succo di lime, triple sec, succo di mirtillo'),
('Margarita', 5.00, 'Cocktail', 'Tequila, triple sec, succo di lime');

INSERT INTO Prodotto (Nome, Prezzo, Categoria) VALUES
('Vodka Belvedere', 7.00, 'Premium Spirit\'s'),
('Vodka Beluga', 7.00, 'Premium Spirit\'s'),
('Gentleman Jack', 7.00, 'Premium Spirit\'s'),
('Woodford Reserve', 7.00, 'Premium Spirit\'s'),
('Jack Daniel\'s Barrel', 7.00, 'Premium Spirit\'s'),
('Appleton Vx', 7.00, 'Premium Spirit\'s'),
('Don Papa Rum', 7.00, 'Premium Spirit\'s'),
('Gin Malfy', 7.00, 'Premium Spirit\'s'),
('Gin London n°1', 7.00, 'Premium Spirit\'s'),
('Gin Haswell', 7.00, 'Premium Spirit\'s'),
('Gin Mare', 7.00, 'Premium Spirit\'s'),
('Gin Hendrick\'s', 7.00, 'Premium Spirit\'s');


INSERT INTO Prodotto (Nome, Prezzo, Categoria, Ingredienti) VALUES
('Spaghetti alla carbonara', 6.00, 'Pasta', 'Pasta fresca all\'uovo, uovo, pancetta, pecorino'),
('Fettuccine al ragù', 6.00, 'Pasta','Pasta fresca all\'uovo, ragù alla bolognese'),
('Tortellini con panna e prosciutto', 6.00, 'Pasta', 'Pasta fresca all\'uovo, prosciutto cotto, panna fresca'),
('Lasagna alla bolognese', 6.00, 'Pasta', 'Pasta fresca all\'uovo, ragù alla bolognese'),
('Trofie al pesto', 6.00, 'Pasta', 'Pasta fresca all\'uovo, pesto alla genovese, pinoli, pecorino'),
('Penne asparagi e pomodorini senza glutine', 6.00, 'Pasta', 'Pasta senza glutine, asparagi, pomodorini'),
('Penne integrali alla siciliana', 6.00, 'Pasta', 'Pasta integrale bio, pomodorini, melanzane, olive, capperi');


insert into Postazione (Numero, X, Y) values (1,1,27),(2,1,26),(3,1,25),(4,1,24),(5,1,23),(6,1,22),(7,1,21),(8,1,20),(9,1,19),(10,1,18),(11,1,17),(12,1,16),(13,1,14),(14,1,13),(15,1,12),(16,1,11),(17,1,10),(18,1,9),(19,1,8),(20,1,7),(21,1,6),(22,1,5),(23,1,4),(24,1,3),(25,1,2),(26,1,1),(27,2,27),(28,2,26),(29,2,25),(30,2,24),(31,2,23),(32,2,22),(33,2,21),(34,2,20),(35,2,19),(36,2,18),(37,2,17),(38,2,16),(39,2,14),(40,2,13),(41,2,12),(42,2,11),(43,2,10),(44,2,9),(45,2,8),(46,2,7),(47,2,6),(48,2,5),(49,2,4),(50,2,3),(51,2,2),(52,2,1),(53,3,27),(54,3,26),(55,3,25),(56,3,24),(57,3,23),(58,3,22),(59,3,21),(60,3,20),(61,3,19),(62,3,18),(63,3,12),(64,3,11),(65,3,10),(66,3,9),(67,3,8),(68,3,7),(69,3,6),(70,3,5),(71,3,4),(72,3,3),(73,3,2),(74,3,1),(75,4,27),(76,4,26),(77,4,25),(78,4,24),(79,4,23),(80,4,22),(81,4,21),(82,4,20),(83,4,19),(84,4,10),(85,4,9),(86,4,8),(87,4,7),(88,4,6),(89,4,5),(90,4,4),(91,4,3),(92,4,2),(93,4,1),(94,5,27),(95,5,26),(96,5,25),(97,5,24),(98,5,23),(99,5,22),(100,5,21),(101,5,20),(102,5,10),(103,5,9),(104,5,8),(105,5,7),(106,5,6),(107,5,5),(108,5,4),(109,5,3),(110,5,2),(111,5,1),(112,6,27),(113,6,26),(114,6,25),(115,6,24),(116,6,23),(117,6,22),(118,6,21),(119,6,20),(120,6,10),(121,6,9),(122,6,8),(123,6,7),(124,6,6),(125,6,5),(126,6,4),(127,6,3),(128,6,2),(129,6,1),(130,7,27),(131,7,26),(132,7,25),(133,7,24),(134,7,23),(135,7,22),(136,7,21),(137,7,20);

