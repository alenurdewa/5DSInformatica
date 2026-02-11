-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 11, 2026 alle 12:57
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `incidenti`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `morti_e_feriti_incidenti_stradali`
--

CREATE TABLE `morti_e_feriti_incidenti_stradali` (
  `id` int(11) NOT NULL,
  `Territorio` varchar(34) DEFAULT NULL,
  `RESULT` char(1) DEFAULT NULL,
  `PERSON_CLASS` char(1) DEFAULT NULL,
  `Osservazione` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dump dei dati per la tabella `morti_e_feriti_incidenti_stradali`
--

INSERT INTO `morti_e_feriti_incidenti_stradali` (`id`, `Territorio`, `RESULT`, `PERSON_CLASS`, `Osservazione`) VALUES
(1, 'Piemonte', 'M', 'C', 116),
(2, 'Piemonte', 'M', 'P', 23),
(3, 'Piemonte', 'M', 'X', 32),
(4, 'Piemonte', 'M', '9', 171),
(5, 'Piemonte', 'F', 'C', 9925),
(6, 'Piemonte', 'F', 'P', 3298),
(7, 'Piemonte', 'F', 'X', 1469),
(8, 'Piemonte', 'F', '9', 14692),
(9, 'Piemonte', '9', 'C', 10041),
(10, 'Piemonte', '9', 'P', 3321),
(11, 'Piemonte', '9', 'X', 1501),
(12, 'Piemonte', '9', '9', 14863),
(13, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'M', 'C', 5),
(14, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'M', 'P', 1),
(15, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'M', 'X', 1),
(16, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'M', '9', 7),
(17, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'F', 'C', 262),
(18, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'F', 'P', 83),
(19, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'F', 'X', 50),
(20, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', 'F', '9', 395),
(21, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', '9', 'C', 267),
(22, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', '9', 'P', 84),
(23, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', '9', 'X', 51),
(24, '\'Valle d\"\'Aosta / Vallée d\"\'Aoste\'', '9', '9', 402),
(25, 'Liguria', 'M', 'C', 41),
(26, 'Liguria', 'M', 'P', 4),
(27, 'Liguria', 'M', 'X', 17),
(28, 'Liguria', 'M', '9', 62),
(29, 'Liguria', 'F', 'C', 6602),
(30, 'Liguria', 'F', 'P', 1890),
(31, 'Liguria', 'F', 'X', 1275),
(32, 'Liguria', 'F', '9', 9767),
(33, 'Liguria', '9', 'C', 6643),
(34, 'Liguria', '9', 'P', 1894),
(35, 'Liguria', '9', 'X', 1292),
(36, 'Liguria', '9', '9', 9829),
(37, 'Lombardia', 'M', 'C', 271),
(38, 'Lombardia', 'M', 'P', 37),
(39, 'Lombardia', 'M', 'X', 75),
(40, 'Lombardia', 'M', '9', 383),
(41, 'Lombardia', 'F', 'C', 28338),
(42, 'Lombardia', 'F', 'P', 6954),
(43, 'Lombardia', 'F', 'X', 3677),
(44, 'Lombardia', 'F', '9', 38969),
(45, 'Lombardia', '9', 'C', 28609),
(46, 'Lombardia', '9', 'P', 6991),
(47, 'Lombardia', '9', 'X', 3752),
(48, 'Lombardia', '9', '9', 39352),
(49, 'Provincia Autonoma Bolzano / Bozen', 'M', 'C', 26),
(50, 'Provincia Autonoma Bolzano / Bozen', 'M', 'P', 5),
(51, 'Provincia Autonoma Bolzano / Bozen', 'M', 'X', 1),
(52, 'Provincia Autonoma Bolzano / Bozen', 'M', '9', 32),
(53, 'Provincia Autonoma Bolzano / Bozen', 'F', 'C', 1567),
(54, 'Provincia Autonoma Bolzano / Bozen', 'F', 'P', 499),
(55, 'Provincia Autonoma Bolzano / Bozen', 'F', 'X', 175),
(56, 'Provincia Autonoma Bolzano / Bozen', 'F', '9', 2241),
(57, 'Provincia Autonoma Bolzano / Bozen', '9', 'C', 1593),
(58, 'Provincia Autonoma Bolzano / Bozen', '9', 'P', 504),
(59, 'Provincia Autonoma Bolzano / Bozen', '9', 'X', 176),
(60, 'Provincia Autonoma Bolzano / Bozen', '9', '9', 2273),
(61, 'Provincia Autonoma Trento', 'M', 'C', 30),
(62, 'Provincia Autonoma Trento', 'M', 'P', 2),
(63, 'Provincia Autonoma Trento', 'M', 'X', 6),
(64, 'Provincia Autonoma Trento', 'M', '9', 38),
(65, 'Provincia Autonoma Trento', 'F', 'C', 1212),
(66, 'Provincia Autonoma Trento', 'F', 'P', 363),
(67, 'Provincia Autonoma Trento', 'F', 'X', 145),
(68, 'Provincia Autonoma Trento', 'F', '9', 1720),
(69, 'Provincia Autonoma Trento', '9', 'C', 1242),
(70, 'Provincia Autonoma Trento', '9', 'P', 365),
(71, 'Provincia Autonoma Trento', '9', 'X', 151),
(72, 'Provincia Autonoma Trento', '9', '9', 1758),
(73, 'Veneto', 'M', 'C', 210),
(74, 'Veneto', 'M', 'P', 29),
(75, 'Veneto', 'M', 'X', 30),
(76, 'Veneto', 'M', '9', 269),
(77, 'Veneto', 'F', 'C', 12740),
(78, 'Veneto', 'F', 'P', 3301),
(79, 'Veneto', 'F', 'X', 1180),
(80, 'Veneto', 'F', '9', 17221),
(81, 'Veneto', '9', 'C', 12950),
(82, 'Veneto', '9', 'P', 3330),
(83, 'Veneto', '9', 'X', 1210),
(84, 'Veneto', '9', '9', 17490),
(85, 'Friuli-Venezia Giulia', 'M', 'C', 63),
(86, 'Friuli-Venezia Giulia', 'M', 'P', 6),
(87, 'Friuli-Venezia Giulia', 'M', 'X', 4),
(88, 'Friuli-Venezia Giulia', 'M', '9', 73),
(89, 'Friuli-Venezia Giulia', 'F', 'C', 3078),
(90, 'Friuli-Venezia Giulia', 'F', 'P', 864),
(91, 'Friuli-Venezia Giulia', 'F', 'X', 333),
(92, 'Friuli-Venezia Giulia', 'F', '9', 4275),
(93, 'Friuli-Venezia Giulia', '9', 'C', 3141),
(94, 'Friuli-Venezia Giulia', '9', 'P', 870),
(95, 'Friuli-Venezia Giulia', '9', 'X', 337),
(96, 'Friuli-Venezia Giulia', '9', '9', 4348),
(97, 'Emilia-Romagna', 'M', 'C', 214),
(98, 'Emilia-Romagna', 'M', 'P', 23),
(99, 'Emilia-Romagna', 'M', 'X', 36),
(100, 'Emilia-Romagna', 'M', '9', 273),
(101, 'Emilia-Romagna', 'F', 'C', 15999),
(102, 'Emilia-Romagna', 'F', 'P', 3946),
(103, 'Emilia-Romagna', 'F', 'X', 1687),
(104, 'Emilia-Romagna', 'F', '9', 21632),
(105, 'Emilia-Romagna', '9', 'C', 16213),
(106, 'Emilia-Romagna', '9', 'P', 3969),
(107, 'Emilia-Romagna', '9', 'X', 1723),
(108, 'Emilia-Romagna', '9', '9', 21905),
(109, 'Toscana', 'M', 'C', 135),
(110, 'Toscana', 'M', 'P', 20),
(111, 'Toscana', 'M', 'X', 33),
(112, 'Toscana', 'M', '9', 188),
(113, 'Toscana', 'F', 'C', 14231),
(114, 'Toscana', 'F', 'P', 3374),
(115, 'Toscana', 'F', 'X', 1860),
(116, 'Toscana', 'F', '9', 19465),
(117, 'Toscana', '9', 'C', 14366),
(118, 'Toscana', '9', 'P', 3394),
(119, 'Toscana', '9', 'X', 1893),
(120, 'Toscana', '9', '9', 19653),
(121, 'Umbria', 'M', 'C', 47),
(122, 'Umbria', 'M', 'P', 8),
(123, 'Umbria', 'M', 'X', 7),
(124, 'Umbria', 'M', '9', 62),
(125, 'Umbria', 'F', 'C', 2388),
(126, 'Umbria', 'F', 'P', 725),
(127, 'Umbria', 'F', 'X', 290),
(128, 'Umbria', 'F', '9', 3403),
(129, 'Umbria', '9', 'C', 2435),
(130, 'Umbria', '9', 'P', 733),
(131, 'Umbria', '9', 'X', 297),
(132, 'Umbria', '9', '9', 3465),
(133, 'Marche', 'M', 'C', 53),
(134, 'Marche', 'M', 'P', 9),
(135, 'Marche', 'M', 'X', 10),
(136, 'Marche', 'M', '9', 72),
(137, 'Marche', 'F', 'C', 4934),
(138, 'Marche', 'F', 'P', 1360),
(139, 'Marche', 'F', 'X', 538),
(140, 'Marche', 'F', '9', 6832),
(141, 'Marche', '9', 'C', 4987),
(142, 'Marche', '9', 'P', 1369),
(143, 'Marche', '9', 'X', 548),
(144, 'Marche', '9', '9', 6904),
(145, 'Lazio', 'M', 'C', 201),
(146, 'Lazio', 'M', 'P', 37),
(147, 'Lazio', 'M', 'X', 81),
(148, 'Lazio', 'M', '9', 319),
(149, 'Lazio', 'F', 'C', 19125),
(150, 'Lazio', 'F', 'P', 5589),
(151, 'Lazio', 'F', 'X', 2980),
(152, 'Lazio', 'F', '9', 27694),
(153, 'Lazio', '9', 'C', 19326),
(154, 'Lazio', '9', 'P', 5626),
(155, 'Lazio', '9', 'X', 3061),
(156, 'Lazio', '9', '9', 28013),
(157, 'Abruzzo', 'M', 'C', 59),
(158, 'Abruzzo', 'M', 'P', 10),
(159, 'Abruzzo', 'M', 'X', 17),
(160, 'Abruzzo', 'M', '9', 86),
(161, 'Abruzzo', 'F', 'C', 3242),
(162, 'Abruzzo', 'F', 'P', 1007),
(163, 'Abruzzo', 'F', 'X', 398),
(164, 'Abruzzo', 'F', '9', 4647),
(165, 'Abruzzo', '9', 'C', 3301),
(166, 'Abruzzo', '9', 'P', 1017),
(167, 'Abruzzo', '9', 'X', 415),
(168, 'Abruzzo', '9', '9', 4733),
(169, 'Molise', 'M', 'C', 7),
(170, 'Molise', 'M', 'P', 2),
(171, 'Molise', 'M', 'X', 3),
(172, 'Molise', 'M', '9', 12),
(173, 'Molise', 'F', 'C', 473),
(174, 'Molise', 'F', 'P', 186),
(175, 'Molise', 'F', 'X', 65),
(176, 'Molise', 'F', '9', 724),
(177, 'Molise', '9', 'C', 480),
(178, 'Molise', '9', 'P', 188),
(179, 'Molise', '9', 'X', 68),
(180, 'Molise', '9', '9', 736),
(181, 'Campania', 'M', 'C', 188),
(182, 'Campania', 'M', 'P', 34),
(183, 'Campania', 'M', 'X', 39),
(184, 'Campania', 'M', '9', 261),
(185, 'Campania', 'F', 'C', 10300),
(186, 'Campania', 'F', 'P', 3899),
(187, 'Campania', 'F', 'X', 1187),
(188, 'Campania', 'F', '9', 15386),
(189, 'Campania', '9', 'C', 10488),
(190, 'Campania', '9', 'P', 3933),
(191, 'Campania', '9', 'X', 1226),
(192, 'Campania', '9', '9', 15647),
(193, 'Puglia', 'M', 'C', 174),
(194, 'Puglia', 'M', 'P', 53),
(195, 'Puglia', 'M', 'X', 14),
(196, 'Puglia', 'M', '9', 241),
(197, 'Puglia', 'F', 'C', 10641),
(198, 'Puglia', 'F', 'P', 5083),
(199, 'Puglia', 'F', 'X', 1289),
(200, 'Puglia', 'F', '9', 17013),
(201, 'Puglia', '9', 'C', 10815),
(202, 'Puglia', '9', 'P', 5136),
(203, 'Puglia', '9', 'X', 1303),
(204, 'Puglia', '9', '9', 17254),
(205, 'Basilicata', 'M', 'C', 22),
(206, 'Basilicata', 'M', 'P', 8),
(207, 'Basilicata', 'M', 'X', 2),
(208, 'Basilicata', 'M', '9', 32),
(209, 'Basilicata', 'F', 'C', 967),
(210, 'Basilicata', 'F', 'P', 508),
(211, 'Basilicata', 'F', 'X', 111),
(212, 'Basilicata', 'F', '9', 1586),
(213, 'Basilicata', '9', 'C', 989),
(214, 'Basilicata', '9', 'P', 516),
(215, 'Basilicata', '9', 'X', 113),
(216, 'Basilicata', '9', '9', 1618),
(217, 'Calabria', 'M', 'C', 67),
(218, 'Calabria', 'M', 'P', 18),
(219, 'Calabria', 'M', 'X', 11),
(220, 'Calabria', 'M', '9', 96),
(221, 'Calabria', 'F', 'C', 3100),
(222, 'Calabria', 'F', 'P', 1310),
(223, 'Calabria', 'F', 'X', 306),
(224, 'Calabria', 'F', '9', 4716),
(225, 'Calabria', '9', 'C', 3167),
(226, 'Calabria', '9', 'P', 1328),
(227, 'Calabria', '9', 'X', 317),
(228, 'Calabria', '9', '9', 4812),
(229, 'Sicilia', 'M', 'C', 166),
(230, 'Sicilia', 'M', 'P', 40),
(231, 'Sicilia', 'M', 'X', 34),
(232, 'Sicilia', 'M', '9', 240),
(233, 'Sicilia', 'F', 'C', 11147),
(234, 'Sicilia', 'F', 'P', 4389),
(235, 'Sicilia', 'F', 'X', 1031),
(236, 'Sicilia', 'F', '9', 16567),
(237, 'Sicilia', '9', 'C', 11313),
(238, 'Sicilia', '9', 'P', 4429),
(239, 'Sicilia', '9', 'X', 1065),
(240, 'Sicilia', '9', '9', 16807),
(241, 'Sardegna', 'M', 'C', 80),
(242, 'Sardegna', 'M', 'P', 16),
(243, 'Sardegna', 'M', 'X', 17),
(244, 'Sardegna', 'M', '9', 113),
(245, 'Sardegna', 'F', 'C', 3237),
(246, 'Sardegna', 'F', 'P', 1179),
(247, 'Sardegna', 'F', 'X', 492),
(248, 'Sardegna', 'F', '9', 4908),
(249, 'Sardegna', '9', 'C', 3317),
(250, 'Sardegna', '9', 'P', 1195),
(251, 'Sardegna', '9', 'X', 509),
(252, 'Sardegna', '9', '9', 5021);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `morti_e_feriti_incidenti_stradali`
--
ALTER TABLE `morti_e_feriti_incidenti_stradali`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_result_esito` (`RESULT`),
  ADD KEY `fk_result_person` (`PERSON_CLASS`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `morti_e_feriti_incidenti_stradali`
--
ALTER TABLE `morti_e_feriti_incidenti_stradali`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=253;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `morti_e_feriti_incidenti_stradali`
--
ALTER TABLE `morti_e_feriti_incidenti_stradali`
  ADD CONSTRAINT `fk_result_esito` FOREIGN KEY (`RESULT`) REFERENCES `esito` (`ID`),
  ADD CONSTRAINT `fk_result_person` FOREIGN KEY (`PERSON_CLASS`) REFERENCES `ruolo` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
