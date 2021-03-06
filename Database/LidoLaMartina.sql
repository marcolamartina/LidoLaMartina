-- MySQL Script generated by MySQL Workbench
-- Tue Aug 25 07:14:00 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema LidoLaMartina
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `LidoLaMartina` ;

-- -----------------------------------------------------
-- Schema LidoLaMartina
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `LidoLaMartina` DEFAULT CHARACTER SET utf8 ;
USE `LidoLaMartina` ;

-- -----------------------------------------------------
-- Table `LidoLaMartina`.`AccountAziendale`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LidoLaMartina`.`AccountAziendale` ;

CREATE TABLE IF NOT EXISTS `LidoLaMartina`.`AccountAziendale` (
  `IDAccountAziendale` INT NOT NULL AUTO_INCREMENT,
  `Ruolo` ENUM('Admin', 'Bagnino', 'Bar', 'Cameriere', 'Cassa', 'Cucina', 'Reception') NOT NULL,
  `Utente_IDUtente` INT NOT NULL,
  PRIMARY KEY (`IDAccountAziendale`),
  INDEX `fk_AccountAziendale_Cliente1_idx` (`Utente_IDUtente` ASC) VISIBLE,
  CONSTRAINT `fk_AccountAziendale_Cliente1`
    FOREIGN KEY (`Utente_IDUtente`)
    REFERENCES `LidoLaMartina`.`Utente` (`IDUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LidoLaMartina`.`Conto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LidoLaMartina`.`Conto` ;

CREATE TABLE IF NOT EXISTS `LidoLaMartina`.`Conto` (
  `IDConto` INT NOT NULL AUTO_INCREMENT,
  `Data` DATE NOT NULL,
  `Pagato` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'Indica se il conto è stato pagato (1) o se ancora non è stato saldato (0), sia nel caso di un rimborso, sia nel caso di un pagamento.',
  `Totale` DECIMAL(6,2) NULL DEFAULT 0.00,
  `Ref_IDUtente` INT NOT NULL,
  PRIMARY KEY (`IDConto`),
  INDEX `fk_Conto_Utente1_idx` (`Ref_IDUtente` ASC) VISIBLE,
  CONSTRAINT `fk_Conto_Utente1`
    FOREIGN KEY (`Ref_IDUtente`)
    REFERENCES `LidoLaMartina`.`Utente` (`IDUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LidoLaMartina`.`Ordinazione`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LidoLaMartina`.`Ordinazione` ;

CREATE TABLE IF NOT EXISTS `LidoLaMartina`.`Ordinazione` (
  `IDOrdinazione` INT NOT NULL AUTO_INCREMENT,
  `Quantita` INT NOT NULL,
  `Consegnata` BIT(1) NOT NULL DEFAULT b'0',
  `Ref_IDConto` INT NOT NULL,
  `Tavolo` INT NOT NULL,
  `Prodotto_IDProdotto` INT NOT NULL,
  `Note` VARCHAR(200) NULL,
  PRIMARY KEY (`IDOrdinazione`),
  INDEX `fk_Ordinazione_Conto1_idx` (`Ref_IDConto` ASC) VISIBLE,
  INDEX `fk_Ordinazione_Prodotto1_idx` (`Prodotto_IDProdotto` ASC) VISIBLE,
  CONSTRAINT `fk_Ordinazione_Conto1`
    FOREIGN KEY (`Ref_IDConto`)
    REFERENCES `LidoLaMartina`.`Conto` (`IDConto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ordinazione_Prodotto1`
    FOREIGN KEY (`Prodotto_IDProdotto`)
    REFERENCES `LidoLaMartina`.`Prodotto` (`IDProdotto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LidoLaMartina`.`Postazione`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LidoLaMartina`.`Postazione` ;

CREATE TABLE IF NOT EXISTS `LidoLaMartina`.`Postazione` (
  `Numero` INT NOT NULL AUTO_INCREMENT,
  `X` INT NOT NULL,
  `Y` INT NOT NULL,
  PRIMARY KEY (`Numero`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LidoLaMartina`.`Prenotazione`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LidoLaMartina`.`Prenotazione` ;

CREATE TABLE IF NOT EXISTS `LidoLaMartina`.`Prenotazione` (
  `IDPrenotazione` INT NOT NULL AUTO_INCREMENT,
  `Data` DATE NOT NULL,
  `Utente_IDUtente` INT NOT NULL,
  `Conto_IDConto` INT NOT NULL,
  `Sdraio` INT NULL,
  `Prezzo` DECIMAL(5,2) NOT NULL,
  `Postazione_Numero` INT NULL,
  `Occupata` BIT(1) NOT NULL DEFAULT b'0',
  `Liberata` BIT(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`IDPrenotazione`),
  INDEX `fk_Prenotazione_Utente1_idx` (`Utente_IDUtente` ASC) VISIBLE,
  INDEX `fk_Prenotazione_Conto1_idx` (`Conto_IDConto` ASC) VISIBLE,
  INDEX `fk_Prenotazione_Postazione1_idx` (`Postazione_Numero` ASC) VISIBLE,
  CONSTRAINT `fk_Prenotazione_Utente1`
    FOREIGN KEY (`Utente_IDUtente`)
    REFERENCES `LidoLaMartina`.`Utente` (`IDUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Conto1`
    FOREIGN KEY (`Conto_IDConto`)
    REFERENCES `LidoLaMartina`.`Conto` (`IDConto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Prenotazione_Postazione1`
    FOREIGN KEY (`Postazione_Numero`)
    REFERENCES `LidoLaMartina`.`Postazione` (`Numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LidoLaMartina`.`Prodotto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LidoLaMartina`.`Prodotto` ;

CREATE TABLE IF NOT EXISTS `LidoLaMartina`.`Prodotto` (
  `IDProdotto` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Prezzo` DECIMAL(5,2) NOT NULL,
  `Categoria` VARCHAR(45) NOT NULL,
  `Ingredienti` VARCHAR(150) NULL,
  `Disponibile` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`IDProdotto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `LidoLaMartina`.`Utente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `LidoLaMartina`.`Utente` ;

CREATE TABLE IF NOT EXISTS `LidoLaMartina`.`Utente` (
  `IDUtente` INT NOT NULL AUTO_INCREMENT,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NULL,
  `Password` VARCHAR(45) NULL,
  `TokenResetPassword` INT NULL DEFAULT 0 COMMENT 'Token generato ad una richiesta di reimpostazione della password da parte di un cliente.',
  `Cellulare` VARCHAR(45) NULL,
  PRIMARY KEY (`IDUtente`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
