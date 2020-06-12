-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  sam. 06 juin 2020 à 20:25
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `project`
--

-- --------------------------------------------------------

--
-- Structure de la table `absence`
--

DROP TABLE IF EXISTS `absence`;
CREATE TABLE IF NOT EXISTS `absence` (
  `ID` bigint(20) NOT NULL,
  `DATEDEBUT` datetime DEFAULT NULL,
  `DATEFIN` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `MINUTEABSENCE` bigint(20) DEFAULT NULL,
  `MOTIFABSENCE_ID` bigint(20) DEFAULT NULL,
  `PERSONNE_ID` bigint(20) DEFAULT NULL,
  `TYPEABSENCE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ABSENCE_MOTIFABSENCE_ID` (`MOTIFABSENCE_ID`),
  KEY `FK_ABSENCE_PERSONNE_ID` (`PERSONNE_ID`),
  KEY `FK_ABSENCE_TYPEABSENCE_ID` (`TYPEABSENCE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `absence`
--

INSERT INTO `absence` (`ID`, `DATEDEBUT`, `DATEFIN`, `DESCRIPTION`, `MINUTEABSENCE`, `MOTIFABSENCE_ID`, `PERSONNE_ID`, `TYPEABSENCE_ID`) VALUES
(201, '2020-06-01 00:00:00', '2020-06-01 00:00:00', '', 0, 1, 1, 51),
(202, '2020-04-14 00:00:00', '2020-05-05 00:00:00', '', 0, 5, 1, 52),
(203, '2020-06-06 00:00:00', '2020-06-06 00:00:00', '', 0, 31, 2, 55),
(204, '2020-06-01 00:00:00', '2020-06-04 00:00:00', '', 0, 5, 3, 51),
(205, '2020-06-13 00:00:00', '2020-06-09 00:00:00', '', 0, 3, 4, 53),
(207, '2020-06-09 00:00:00', '2020-06-12 00:00:00', '', 0, 24, 4, 206);

-- --------------------------------------------------------

--
-- Structure de la table `absences`
--

DROP TABLE IF EXISTS `absences`;
CREATE TABLE IF NOT EXISTS `absences` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATEABSENCE` date DEFAULT NULL,
  `JOURS` float DEFAULT NULL,
  `MINUTEABSENCE` int(11) DEFAULT NULL,
  `NOMBREHEURE` float DEFAULT NULL,
  `MOTIFABSENCES_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ABSENCES_MOTIFABSENCES_ID` (`MOTIFABSENCES_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `groupetravail`
--

DROP TABLE IF EXISTS `groupetravail`;
CREATE TABLE IF NOT EXISTS `groupetravail` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `groupetravail`
--

INSERT INTO `groupetravail` (`ID`, `LIBELLE`) VALUES
(1, '5005'),
(2, '5006'),
(3, '5007'),
(4, '5008'),
(5, '5009'),
(6, '803'),
(7, '201');

-- --------------------------------------------------------

--
-- Structure de la table `motifabsence`
--

DROP TABLE IF EXISTS `motifabsence`;
CREATE TABLE IF NOT EXISTS `motifabsence` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `motifabsence`
--

INSERT INTO `motifabsence` (`ID`, `LIBELLE`) VALUES
(1, 'Administratif'),
(2, 'Certificat médical'),
(3, 'Maladie'),
(4, 'Mise a pied'),
(5, 'Personnel'),
(6, 'Congé maternité'),
(7, 'Congé paternité'),
(8, 'Décès père'),
(9, 'Décès mère'),
(10, 'Décès frére'),
(11, 'Décès soeur'),
(12, 'Décès beau père'),
(13, 'Décès belle mère'),
(14, 'Décès beau frère'),
(15, 'Décès belle soeur'),
(16, 'Décès proche'),
(17, 'Décès grand père'),
(18, 'Décès grande mère'),
(19, 'Décès conjoint'),
(20, 'Décès beau grand père'),
(21, 'Fiançailles'),
(22, 'Décès belle grande mère'),
(23, 'Fiançailles'),
(24, 'Congé mariage'),
(25, 'Circoncision'),
(26, 'Opération chirurgicale du conjoint'),
(27, 'Opération chirurgicale enfant'),
(28, 'Transport'),
(29, 'Maladie enfant'),
(30, 'Maladie conjoint'),
(31, 'Sous charge');

-- --------------------------------------------------------

--
-- Structure de la table `motifretard`
--

DROP TABLE IF EXISTS `motifretard`;
CREATE TABLE IF NOT EXISTS `motifretard` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `motifretard`
--

INSERT INTO `motifretard` (`ID`, `LIBELLE`) VALUES
(208, 'Retard autorisée'),
(209, 'Retard non autorisée'),
(210, 'sortie autorisée'),
(211, 'sortie non autorisée'),
(212, 'Sous charge');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `ID` bigint(20) NOT NULL,
  `ADRESSE` varchar(255) DEFAULT NULL,
  `CIN` varchar(255) DEFAULT NULL,
  `DATEEMBAUCHE` date DEFAULT NULL,
  `DATENAISSANCE` date DEFAULT NULL,
  `MATRICULE` varchar(255) DEFAULT NULL,
   `PASSWORD` varchar(255) DEFAULT NULL,
  `NBRENFANT` int(11) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `NOMCONJOINT` varchar(255) DEFAULT NULL,
  `NUMEROCNSS` varchar(255) DEFAULT NULL,
  `NUMEROMUTUEL` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `TEL` varchar(255) DEFAULT NULL,
  `GROUPETRAVAIL_ID` bigint(20) DEFAULT NULL,
  `PROFESSION_ID` bigint(20) DEFAULT NULL,
  `SECTIONTRAVAIL_ID` bigint(20) DEFAULT NULL,
  `SITUATIONFAMILLIALE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PERSONNE_GROUPETRAVAIL_ID` (`GROUPETRAVAIL_ID`),
  KEY `FK_PERSONNE_SITUATIONFAMILLIALE_ID` (`SITUATIONFAMILLIALE_ID`),
  KEY `FK_PERSONNE_PROFESSION_ID` (`PROFESSION_ID`),
  KEY `FK_PERSONNE_SECTIONTRAVAIL_ID` (`SECTIONTRAVAIL_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `personne`
--

INSERT INTO `personne` (`ID`, `ADRESSE`, `CIN`, `DATEEMBAUCHE`, `DATENAISSANCE`, `MATRICULE`, `NBRENFANT`, `NOM`, `NOMCONJOINT`, `NUMEROCNSS`, `NUMEROMUTUEL`, `PRENOM`, `TEL`, `GROUPETRAVAIL_ID`, `PROFESSION_ID`, `SECTIONTRAVAIL_ID`, `SITUATIONFAMILLIALE_ID`) VALUES
(1, 'Hay lahrech bloc 14-Marrakech', 'EE23564', '1993-01-09', '1976-01-12', '1', 0, 'CHBANI', 'sans', '123456789', '1', 'HALIMA', '0631254896', 1, 49, 102, 151),
(2, 'Daoudiate n 13-Marrakech', 'EA123456', '1989-06-05', '1977-06-05', '2', 3, 'BENNANI', 'FILALI NAJIA', '456123789', '2', 'AHMED', '0645442255', 2, 35, 101, 152),
(3, 'Lamcella n13 syba -Marrakech', 'EH123789', '2016-01-02', '1993-09-08', '3', 5, 'FANANE', '', '789456321', '3', 'KAOUTAR', '0758692514', 4, 37, 103, 154),
(4, 'Targa n 23 -Marrakech', 'EE56421', '1999-09-11', '1961-05-09', '4', 0, 'FATHI', '', '121364789', '4', 'AMINA', '0562349875', 6, 38, 102, 151),
(5, 'Hay lbahya 32 -Marrakech', 'EE585376', '2015-05-06', '1998-03-04', '5', 0, 'KOUDI', '', '121458967', '5', 'FATIMA EZZAHRA', '0623512485', 6, 34, 103, 151);

-- --------------------------------------------------------

--
-- Structure de la table `profession`
--

DROP TABLE IF EXISTS `profession`;
CREATE TABLE IF NOT EXISTS `profession` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `profession`
--

INSERT INTO `profession` (`ID`, `LIBELLE`) VALUES
(34, 'Directrice RH'),
(35, 'Agent de Serigraphie'),
(36, 'Agent de picot'),
(37, 'Assistante RH'),
(38, 'Monitrice de groupe'),
(39, 'Chef d\'atelier'),
(40, 'Opératrice de groupe'),
(41, 'Chef de groupe'),
(42, 'Agent de saisie'),
(43, 'Responsable Logiciel'),
(44, 'Ingénieur de logistique'),
(45, 'Responsable Logistique'),
(46, 'Responsable d\'informatique'),
(47, 'Informaticien(ne)'),
(48, 'Technicien(ne)'),
(49, 'Agent de méthode'),
(50, 'Mécanicien(ne)');

-- --------------------------------------------------------

--
-- Structure de la table `retard`
--

DROP TABLE IF EXISTS `retard`;
CREATE TABLE IF NOT EXISTS `retard` (
  `ID` bigint(20) NOT NULL,
  `DATERETARD` datetime DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `NOMBREHEURE` float DEFAULT NULL,
  `MOTIFRETARD_ID` bigint(20) DEFAULT NULL,
  `PERSONNE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_RETARD_MOTIFRETARD_ID` (`MOTIFRETARD_ID`),
  KEY `FK_RETARD_PERSONNE_ID` (`PERSONNE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `retard`
--

INSERT INTO `retard` (`ID`, `DATERETARD`, `DESCRIPTION`, `NOMBREHEURE`, `MOTIFRETARD_ID`, `PERSONNE_ID`) VALUES
(213, '2020-06-01 00:00:00', 'Administratif', 0.25, 208, 5),
(214, '2020-06-02 00:00:00', 'Panne voiture', 3, 208, 5),
(215, '2020-06-04 00:00:00', 'banque', 1, 210, 1),
(251, '2020-05-05 00:00:00', 'abandon de poste', 3, 211, 3);

-- --------------------------------------------------------

--
-- Structure de la table `sectiontravail`
--

DROP TABLE IF EXISTS `sectiontravail`;
CREATE TABLE IF NOT EXISTS `sectiontravail` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  `CHEF_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SECTIONTRAVAIL_CHEF_ID` (`CHEF_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `sectiontravail`
--

INSERT INTO `sectiontravail` (`ID`, `LIBELLE`, `CHEF_ID`) VALUES
(101, 'Serigraphie', 1),
(102, 'Picot', 1),
(103, 'Responsable', 1);

-- --------------------------------------------------------

--
-- Structure de la table `sequence`
--

DROP TABLE IF EXISTS `sequence`;
CREATE TABLE IF NOT EXISTS `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `situationfamilliale`
--

DROP TABLE IF EXISTS `situationfamilliale`;
CREATE TABLE IF NOT EXISTS `situationfamilliale` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `situationfamilliale`
--

INSERT INTO `situationfamilliale` (`ID`, `LIBELLE`) VALUES
(151, 'Célibataire'),
(152, 'Marié(e)'),
(153, 'Veuf(ve)'),
(154, 'Divorcé(e)');

-- --------------------------------------------------------

--
-- Structure de la table `tache`
--

DROP TABLE IF EXISTS `tache`;
CREATE TABLE IF NOT EXISTS `tache` (
  `ID` bigint(20) NOT NULL,
  `DATEDEBUTESTIME` datetime DEFAULT NULL,
  `DATEDEBUTREEL` datetime DEFAULT NULL,
  `DATEFINESTIME` datetime DEFAULT NULL,
  `DATEFINTREEL` datetime DEFAULT NULL,
  `NOTE` varchar(255) DEFAULT NULL,
  `PERSONNE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TACHE_PERSONNE_ID` (`PERSONNE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `tache`
--

INSERT INTO `tache` (`ID`, `DATEDEBUTESTIME`, `DATEDEBUTREEL`, `DATEFINESTIME`, `DATEFINTREEL`, `NOTE`, `PERSONNE_ID`) VALUES
(252, '2020-06-09 00:00:00', NULL, '2020-06-09 00:00:00', NULL, 'Sous charge', 3),
(253, NULL, '2020-06-07 00:00:00', NULL, '2020-06-08 00:00:00', 'Opération conjoint', 3),
(254, '2020-06-02 00:00:00', NULL, '2020-06-18 00:00:00', NULL, 'Autorisation', 1);

-- --------------------------------------------------------

--
-- Structure de la table `typeabsence`
--

DROP TABLE IF EXISTS `typeabsence`;
CREATE TABLE IF NOT EXISTS `typeabsence` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `typeabsence`
--

INSERT INTO `typeabsence` (`ID`, `LIBELLE`) VALUES
(51, 'Absence justifiée'),
(52, 'Absence non justifiée'),
(53, 'Sortie autorisée'),
(54, 'Sortie non autorisée'),
(55, 'Sous charge'),
(56, 'Discipline'),
(57, 'Autorisation'),
(58, 'Maladie'),
(206, 'Absence légale');
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
