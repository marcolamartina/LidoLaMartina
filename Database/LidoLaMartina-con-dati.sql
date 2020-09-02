CREATE DATABASE  IF NOT EXISTS `LidoLaMartina` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `LidoLaMartina`;
-- MySQL dump 10.13  Distrib 8.0.13, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: LidoLaMartina
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AccountAziendale`
--

DROP TABLE IF EXISTS `AccountAziendale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `AccountAziendale` (
  `IDAccountAziendale` int(11) NOT NULL AUTO_INCREMENT,
  `Ruolo` enum('Admin','Bagnino','Bar','Cameriere','Cassa','Cucina','Reception') NOT NULL,
  `Utente_IDUtente` int(11) NOT NULL,
  PRIMARY KEY (`IDAccountAziendale`),
  KEY `fk_AccountAziendale_Cliente1_idx` (`Utente_IDUtente`),
  CONSTRAINT `fk_AccountAziendale_Cliente1` FOREIGN KEY (`Utente_IDUtente`) REFERENCES `utente` (`idutente`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AccountAziendale`
--

LOCK TABLES `AccountAziendale` WRITE;
/*!40000 ALTER TABLE `AccountAziendale` DISABLE KEYS */;
INSERT INTO `AccountAziendale` VALUES (1,'Cassa',2),(2,'Bagnino',3),(3,'Bar',4),(4,'Cameriere',5),(5,'Cucina',6),(6,'Reception',7),(7,'Admin',1),(8,'Bagnino',1),(9,'Bar',1),(10,'Cameriere',1),(11,'Cassa',1),(12,'Cucina',1),(13,'Reception',1);
/*!40000 ALTER TABLE `AccountAziendale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Conto`
--

DROP TABLE IF EXISTS `Conto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Conto` (
  `IDConto` int(11) NOT NULL AUTO_INCREMENT,
  `Data` date NOT NULL,
  `Pagato` bit(1) NOT NULL DEFAULT b'0' COMMENT 'Indica se il conto è stato pagato (1) o se ancora non è stato saldato (0), sia nel caso di un rimborso, sia nel caso di un pagamento.',
  `Totale` decimal(6,2) DEFAULT '0.00',
  `Ref_IDUtente` int(11) NOT NULL,
  PRIMARY KEY (`IDConto`),
  KEY `fk_Conto_Utente1_idx` (`Ref_IDUtente`),
  CONSTRAINT `fk_Conto_Utente1` FOREIGN KEY (`Ref_IDUtente`) REFERENCES `utente` (`idutente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Conto`
--

LOCK TABLES `Conto` WRITE;
/*!40000 ALTER TABLE `Conto` DISABLE KEYS */;
INSERT INTO `Conto` VALUES (1,'2020-08-28',_binary '',69.00,8),(2,'2020-08-29',_binary '\0',0.00,8);
/*!40000 ALTER TABLE `Conto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Ordinazione`
--

DROP TABLE IF EXISTS `Ordinazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Ordinazione` (
  `IDOrdinazione` int(11) NOT NULL AUTO_INCREMENT,
  `Quantita` int(11) NOT NULL,
  `Consegnata` bit(1) NOT NULL DEFAULT b'0',
  `Ref_IDConto` int(11) NOT NULL,
  `Tavolo` int(11) NOT NULL,
  `Prodotto_IDProdotto` int(11) NOT NULL,
  `Note` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`IDOrdinazione`),
  KEY `fk_Ordinazione_Conto1_idx` (`Ref_IDConto`),
  KEY `fk_Ordinazione_Prodotto1_idx` (`Prodotto_IDProdotto`),
  CONSTRAINT `fk_Ordinazione_Conto1` FOREIGN KEY (`Ref_IDConto`) REFERENCES `conto` (`idconto`),
  CONSTRAINT `fk_Ordinazione_Prodotto1` FOREIGN KEY (`Prodotto_IDProdotto`) REFERENCES `prodotto` (`idprodotto`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ordinazione`
--

LOCK TABLES `Ordinazione` WRITE;
/*!40000 ALTER TABLE `Ordinazione` DISABLE KEYS */;
INSERT INTO `Ordinazione` VALUES (1,1,_binary '\0',1,5,1,NULL),(2,1,_binary '\0',1,5,3,NULL),(3,1,_binary '\0',1,5,66,NULL),(4,3,_binary '\0',2,6,3,NULL),(5,1,_binary '\0',2,6,17,NULL),(6,3,_binary '\0',2,6,44,NULL);
/*!40000 ALTER TABLE `Ordinazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Postazione`
--

DROP TABLE IF EXISTS `Postazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Postazione` (
  `Numero` int(11) NOT NULL AUTO_INCREMENT,
  `X` int(11) NOT NULL,
  `Y` int(11) NOT NULL,
  PRIMARY KEY (`Numero`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Postazione`
--

LOCK TABLES `Postazione` WRITE;
/*!40000 ALTER TABLE `Postazione` DISABLE KEYS */;
INSERT INTO `Postazione` VALUES (1,1,27),(2,1,26),(3,1,25),(4,1,24),(5,1,23),(6,1,22),(7,1,21),(8,1,20),(9,1,19),(10,1,18),(11,1,17),(12,1,16),(13,1,14),(14,1,13),(15,1,12),(16,1,11),(17,1,10),(18,1,9),(19,1,8),(20,1,7),(21,1,6),(22,1,5),(23,1,4),(24,1,3),(25,1,2),(26,1,1),(27,2,27),(28,2,26),(29,2,25),(30,2,24),(31,2,23),(32,2,22),(33,2,21),(34,2,20),(35,2,19),(36,2,18),(37,2,17),(38,2,16),(39,2,14),(40,2,13),(41,2,12),(42,2,11),(43,2,10),(44,2,9),(45,2,8),(46,2,7),(47,2,6),(48,2,5),(49,2,4),(50,2,3),(51,2,2),(52,2,1),(53,3,27),(54,3,26),(55,3,25),(56,3,24),(57,3,23),(58,3,22),(59,3,21),(60,3,20),(61,3,19),(62,3,18),(63,3,12),(64,3,11),(65,3,10),(66,3,9),(67,3,8),(68,3,7),(69,3,6),(70,3,5),(71,3,4),(72,3,3),(73,3,2),(74,3,1),(75,4,27),(76,4,26),(77,4,25),(78,4,24),(79,4,23),(80,4,22),(81,4,21),(82,4,20),(83,4,19),(84,4,10),(85,4,9),(86,4,8),(87,4,7),(88,4,6),(89,4,5),(90,4,4),(91,4,3),(92,4,2),(93,4,1),(94,5,27),(95,5,26),(96,5,25),(97,5,24),(98,5,23),(99,5,22),(100,5,21),(101,5,20),(102,5,10),(103,5,9),(104,5,8),(105,5,7),(106,5,6),(107,5,5),(108,5,4),(109,5,3),(110,5,2),(111,5,1),(112,6,27),(113,6,26),(114,6,25),(115,6,24),(116,6,23),(117,6,22),(118,6,21),(119,6,20),(120,6,10),(121,6,9),(122,6,8),(123,6,7),(124,6,6),(125,6,5),(126,6,4),(127,6,3),(128,6,2),(129,6,1),(130,7,27),(131,7,26),(132,7,25),(133,7,24),(134,7,23),(135,7,22),(136,7,21),(137,7,20);
/*!40000 ALTER TABLE `Postazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Prenotazione`
--

DROP TABLE IF EXISTS `Prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Prenotazione` (
  `IDPrenotazione` int(11) NOT NULL AUTO_INCREMENT,
  `Data` date NOT NULL,
  `Utente_IDUtente` int(11) NOT NULL,
  `Conto_IDConto` int(11) NOT NULL,
  `Sdraio` int(11) DEFAULT NULL,
  `Prezzo` decimal(5,2) NOT NULL,
  `Postazione_Numero` int(11) DEFAULT NULL,
  `Occupata` bit(1) NOT NULL DEFAULT b'0',
  `Liberata` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`IDPrenotazione`),
  KEY `fk_Prenotazione_Utente1_idx` (`Utente_IDUtente`),
  KEY `fk_Prenotazione_Conto1_idx` (`Conto_IDConto`),
  KEY `fk_Prenotazione_Postazione1_idx` (`Postazione_Numero`),
  CONSTRAINT `fk_Prenotazione_Conto1` FOREIGN KEY (`Conto_IDConto`) REFERENCES `conto` (`idconto`),
  CONSTRAINT `fk_Prenotazione_Postazione1` FOREIGN KEY (`Postazione_Numero`) REFERENCES `postazione` (`numero`),
  CONSTRAINT `fk_Prenotazione_Utente1` FOREIGN KEY (`Utente_IDUtente`) REFERENCES `utente` (`idutente`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prenotazione`
--

LOCK TABLES `Prenotazione` WRITE;
/*!40000 ALTER TABLE `Prenotazione` DISABLE KEYS */;
INSERT INTO `Prenotazione` VALUES (1,'2020-08-28',8,1,2,7.00,NULL,_binary '\0',_binary '\0'),(2,'2020-08-28',8,1,0,18.00,14,_binary '',_binary '\0'),(3,'2020-08-28',8,1,0,18.00,15,_binary '',_binary '\0'),(4,'2020-08-29',8,2,1,7.00,NULL,_binary '\0',_binary '\0'),(5,'2020-08-29',8,2,0,18.00,3,_binary '',_binary '\0'),(6,'2020-08-29',8,2,0,18.00,4,_binary '',_binary '\0'),(7,'2020-08-29',8,2,0,18.00,5,_binary '',_binary '\0');
/*!40000 ALTER TABLE `Prenotazione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Prodotto`
--

DROP TABLE IF EXISTS `Prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Prodotto` (
  `IDProdotto` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `Prezzo` decimal(5,2) NOT NULL,
  `Categoria` varchar(45) NOT NULL,
  `Ingredienti` varchar(150) DEFAULT NULL,
  `Disponibile` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`IDProdotto`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prodotto`
--

LOCK TABLES `Prodotto` WRITE;
/*!40000 ALTER TABLE `Prodotto` DISABLE KEYS */;
INSERT INTO `Prodotto` VALUES (1,'Antipasto della casa',10.00,'Antipasti','Salumi, formaggi e bruschette miste',1),(2,'Bruschette miste (10pz.)',5.00,'Antipasti','Bruschette miste con pomodoro, patè vari, salumi e formaggi',1),(3,'Gorgo Beach',5.00,'Panini','Hamburger, pomodoro, mozzarella, cipolla, insalata verde, ketchup',1),(4,'Claudia',5.00,'Panini','Prosciutto crudo, insalata verde, mozzarella, olio, sale, origano',1),(5,'Francy',5.00,'Panini','Tonno, pomodoro, maionese, lattuga',1),(6,'Caprese',5.00,'Panini','Pomodoro, mozzarella, basilico, olio, sale, pepe',1),(7,'Cartoccio',5.00,'Panini','Mozzarella, prosciutto cotto, salsa rosa',1),(8,'Piccante',5.00,'Panini','Salame piccante, sottiletta, tabasco, ketchup',1),(9,'Hot Dog',5.00,'Panini','Wurstel, ketchup',1),(10,'Semplice',5.00,'Panini','Prosciutto cotto, mozzarella, pomodoro, olio, sale',1),(11,'Corallo',5.00,'Panini','Prosciutto crudo, mozzarella, pomodoro, salsa rosa',1),(12,'Saporito',5.00,'Panini','Salame piccante, cipolla, pomodoro, mozzarella, maionese',1),(13,'Topolino',5.00,'Panini','Mozzarella, prosciutto cotto, wurstel, salsa rosa',1),(14,'Salmone',5.00,'Panini','Salmone, rucola, mozzarella, salsa rosa',1),(15,'Vegetariano',5.00,'Panini','Verdure grigliate, maionese, emmental',1),(16,'Particolare',5.00,'Panini','Tonno, lattuga, uovo sodo, pomodoro, emmental, senape',1),(17,'Sottiletta e prosciutto cotto',2.50,'Toast',NULL,1),(18,'Mozzarella e prosciutto crudo',3.00,'Toast',NULL,1),(19,'Salmone, mozzarella e rucola',3.00,'Toast',NULL,1),(20,'Tonno, mozzarella e lattuga',3.00,'Toast',NULL,1),(21,'Gorgo Beach',5.00,'Piadine','Prosciutto crudo, mozzarella, pomodoro, olio piccante',1),(22,'Semplice',5.00,'Piadine','Prosciutto cotto, mozzarella',1),(23,'Caprese',5.00,'Piadine','Pomodoro, mozzarella, basilico',1),(24,'Vegetariano',5.00,'Piadine','Verdure miste grigliate, mozzarella, maionese',1),(25,'Salmone',5.00,'Piadine','Salmone, mozzarella, rucola, salsa rosa',1),(26,'Caprese',7.00,'Insalate','Pomodoro, mozzarella, basilico',1),(27,'Caprese Fantasia',8.50,'Insalate','Pomodoro, mozzarella, basilico, cipolla, carote, rucola',1),(28,'Salmone',12.00,'Insalate','Salmone, pomodoro, mozzarella, basilico',1),(29,'Verde',6.50,'Insalate','Lattuga, mais, carote, pomodoro',1),(30,'Tonno',8.50,'Insalate','Tonno, lattuga, pomodoro, cipolla',1),(31,'Sorpesa',10.00,'Insalate','A umore dello chef',1),(32,'Insalata di mare',10.00,'Insalate','Misto di pesce',1),(33,'Caffè caldo',1.00,'Caffetteria',NULL,1),(34,'Caffè freddo',1.50,'Caffetteria',NULL,1),(35,'Caffè corretto',1.50,'Caffetteria',NULL,1),(36,'Cappuccino',2.00,'Caffetteria',NULL,1),(37,'Cornetto',1.00,'Caffetteria',NULL,1),(38,'Sfogliatina',1.50,'Caffetteria',NULL,1),(39,'Acqua 50cl',1.00,'Bibite',NULL,1),(40,'Lemon soda 33cl',2.50,'Bibite',NULL,1),(41,'Chinotto 33cl',2.50,'Bibite',NULL,1),(42,'Fanta 33cl',2.50,'Bibite',NULL,1),(43,'Sprite 33cl',2.50,'Bibite',NULL,1),(44,'Coca Cola 33cl',2.50,'Bibite',NULL,1),(45,'Thé pesca/limone 33cl',2.50,'Bibite',NULL,1),(46,'Succhi di frutta 33cl',2.50,'Bibite',NULL,1),(47,'Red Bull 33cl',3.50,'Bibite',NULL,1),(48,'Crodino 33cl',2.50,'Bibite',NULL,1),(49,'Bitter bianco/rosso 33cl',2.50,'Bibite',NULL,1),(50,'Campari Soda 33cl',3.00,'Bibite',NULL,1),(51,'Aperol Soda 33cl',3.00,'Bibite',NULL,1),(52,'Aperol 33cl',3.50,'Bibite',NULL,1),(53,'Martini bianco/rosso 33cl',3.50,'Bibite',NULL,1),(54,'Bottiglia 75cl',25.00,'Prosecco',NULL,1),(55,'Bicchiere',5.00,'Prosecco',NULL,1),(56,'Bottiglia 75cl',25.00,'Vini',NULL,1),(57,'Bicchiere',5.00,'Vini',NULL,1),(58,'Ichnusa 33cl',3.00,'Birre',NULL,1),(59,'Peroni 33cl',2.00,'Birre',NULL,1),(60,'Moretti 33cl',2.00,'Birre',NULL,1),(61,'Peroni Chill Lemon 33cl',2.50,'Birre',NULL,1),(62,'Messina Cristalli di Sale 33cl',3.00,'Birre',NULL,1),(63,'Ceres 33cl',4.00,'Birre',NULL,1),(64,'Corona 33cl',5.00,'Birre',NULL,1),(65,'Tennent\'s 33cl',5.00,'Birre',NULL,1),(66,'Bud 33cl',4.00,'Birre',NULL,1),(67,'Pilsner Urquell 33cl',3.00,'Birre',NULL,1),(68,'Forst Felsenkeller 33cl',3.00,'Birre',NULL,1),(69,'Goose Island IPA 33cl',5.00,'Birre',NULL,1),(70,'Peroni Forte 33cl',3.50,'Birre',NULL,1),(71,'Gin Tonic',5.00,'Long Drink',NULL,1),(72,'Campari Orange',5.00,'Long Drink',NULL,1),(73,'Rum e Cola',5.00,'Long Drink',NULL,1),(74,'Jack Daniels e Coca',5.00,'Long Drink',NULL,1),(75,'Mojito',5.00,'Pestati','Rum scuro, lime a pezzi, zucchero di canna',1),(76,'Caipirinha',5.00,'Pestati','Cachaça, lime a pezzi, zucchero di canna',1),(77,'Caipiroska',5.00,'Pestati','Vodka fragola, lime a pezzi, zucchero di canna',1),(78,'Negroni',5.00,'Pre Dinner','Campari, Martini rosso, gin',1),(79,'Americano',5.00,'Pre Dinner','Campari, Martini rosso, Soda',1),(80,'Negroni sbagliato',5.00,'Pre Dinner','Campari, Martini rosso, prosecco',1),(81,'Aperol Spritz',5.00,'Pre Dinner','Aperol, prosecco, soda',1),(82,'Moscow Mule',5.00,'Cocktail','Vodka, succo di lime, ginger beer',1),(83,'Long Island',5.00,'Cocktail','Rum, vodka, triple sec, gin, sweet sour, coca cola',1),(84,'Cosmopolitan',5.00,'Cocktail','Vodka, succo di lime, triple sec, succo di mirtillo',1),(85,'Margarita',5.00,'Cocktail','Tequila, triple sec, succo di lime',1),(86,'Vodka Belvedere',7.00,'Premium Spirit\'s',NULL,1),(87,'Vodka Beluga',7.00,'Premium Spirit\'s',NULL,1),(88,'Gentleman Jack',7.00,'Premium Spirit\'s',NULL,1),(89,'Woodford Reserve',7.00,'Premium Spirit\'s',NULL,1),(90,'Jack Daniel\'s Barrel',7.00,'Premium Spirit\'s',NULL,1),(91,'Appleton Vx',7.00,'Premium Spirit\'s',NULL,1),(92,'Don Papa Rum',7.00,'Premium Spirit\'s',NULL,1),(93,'Gin Malfy',7.00,'Premium Spirit\'s',NULL,1),(94,'Gin London n°1',7.00,'Premium Spirit\'s',NULL,1),(95,'Gin Haswell',7.00,'Premium Spirit\'s',NULL,1),(96,'Gin Mare',7.00,'Premium Spirit\'s',NULL,1),(97,'Gin Hendrick\'s',7.00,'Premium Spirit\'s',NULL,1),(98,'Spaghetti alla carbonara',6.00,'Pasta','Pasta fresca all\'uovo, uovo, pancetta, pecorino',1),(99,'Fettuccine al ragù',6.00,'Pasta','Pasta fresca all\'uovo, ragù alla bolognese',1),(100,'Tortellini con panna e prosciutto',6.00,'Pasta','Pasta fresca all\'uovo, prosciutto cotto, panna fresca',1),(101,'Lasagna alla bolognese',6.00,'Pasta','Pasta fresca all\'uovo, ragù alla bolognese',1),(102,'Trofie al pesto',6.00,'Pasta','Pasta fresca all\'uovo, pesto alla genovese, pinoli, pecorino',1),(103,'Penne asparagi e pomodorini senza glutine',6.00,'Pasta','Pasta senza glutine, asparagi, pomodorini',1),(104,'Penne integrali alla siciliana',6.00,'Pasta','Pasta integrale bio, pomodorini, melanzane, olive, capperi',1);
/*!40000 ALTER TABLE `Prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Utente`
--

DROP TABLE IF EXISTS `Utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `Utente` (
  `IDUtente` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `TokenResetPassword` int(11) DEFAULT '0' COMMENT 'Token generato ad una richiesta di reimpostazione della password da parte di un cliente.',
  `Cellulare` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IDUtente`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Utente`
--

LOCK TABLES `Utente` WRITE;
/*!40000 ALTER TABLE `Utente` DISABLE KEYS */;
INSERT INTO `Utente` VALUES (1,'Marco','Amministratore','marcoamministratore@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000'),(2,'Mariella','Cassiera','mariellacassiera@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000'),(3,'Luca','Bagnino','lucabagnino@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000'),(4,'Maria','Barista','mariabarista@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000'),(5,'Francesco','Cameriere','francescocameriere@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000'),(6,'Barbara','Cuoca','barbaracuoca@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000'),(7,'Pasquale','Receptionist','pasqualereceptionist@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000'),(8,'Davide','Cliente','davidecliente@gmail.com','2ac9cb7dc02b3c0083eb70898e549b63',0,'3270000000');
/*!40000 ALTER TABLE `Utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-29 17:30:30
