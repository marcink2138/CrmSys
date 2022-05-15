-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 15 Maj 2022, 16:07
-- Wersja serwera: 10.4.22-MariaDB
-- Wersja PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `crmsys`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `account_group`
--

CREATE TABLE `account_group` (
  `id` int(11) NOT NULL,
  `groupName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `account_group`
--

INSERT INTO `account_group` (`id`, `groupName`) VALUES
(2, 'TESTOWA');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `asset`
--

CREATE TABLE `asset` (
  `id` int(11) NOT NULL,
  `orderedBy` int(11) NOT NULL COMMENT 'contactId',
  `soldBy` int(11) NOT NULL COMMENT 'accountId',
  `amount` int(11) NOT NULL,
  `productId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `asset`
--

INSERT INTO `asset` (`id`, `orderedBy`, `soldBy`, `amount`, `productId`) VALUES
(28, 9, 1, 100, 11),
(30, 9, 1, 15, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `company information`
--

CREATE TABLE `company information` (
  `name` varchar(100) DEFAULT NULL,
  `contactId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `contact`
--

CREATE TABLE `contact` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `phone` int(11) NOT NULL,
  `emailAddress` varchar(100) NOT NULL,
  `creationDate` date NOT NULL,
  `language` varchar(50) NOT NULL,
  `addressCity` varchar(100) NOT NULL,
  `addressCountry` varchar(100) NOT NULL,
  `addressPostalCode` varchar(100) NOT NULL,
  `addressStreet` varchar(100) NOT NULL,
  `addressHouseNumber` varchar(100) NOT NULL,
  `accountGroupId` int(11) NOT NULL,
  `leadId` int(11) DEFAULT NULL,
  `createdBy` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `contact`
--

INSERT INTO `contact` (`id`, `name`, `surname`, `phone`, `emailAddress`, `creationDate`, `language`, `addressCity`, `addressCountry`, `addressPostalCode`, `addressStreet`, `addressHouseNumber`, `accountGroupId`, `leadId`, `createdBy`) VALUES
(1, 'Marcin', 'Karcz', 999222555, 'abcd4@abcd.pl', '2022-03-27', 'PL', 'Kraków', 'Polska', '32-011', 'Mickiewicza', '22', 2, NULL, 1),
(9, 'Michał', 'Cieślik', 123456789, 'koloz32@interia.pl', '2022-04-08', 'PL', 'Warszawa', 'Polska', '32-111', 'Sportowa', '31', 2, NULL, 1),
(10, 'Wiktor', 'Tekiela', 777222111, 'wiktor@jdx.pl', '2022-05-10', 'PL', 'Berlin', 'Niemcy', '00-111', 'Alexanderplatz', '31', 2, NULL, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `contact_email_group`
--

CREATE TABLE `contact_email_group` (
  `id` int(11) NOT NULL,
  `contactId` int(11) NOT NULL,
  `emailGroupId` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `contact_email_group`
--

INSERT INTO `contact_email_group` (`id`, `contactId`, `emailGroupId`) VALUES
(22, 1, 7),
(25, 1, 8),
(30, 1, 10),
(23, 9, 7),
(29, 9, 10),
(24, 10, 7),
(26, 10, 8);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `email_group`
--

CREATE TABLE `email_group` (
  `id` int(11) NOT NULL,
  `emailGroupName` varchar(50) NOT NULL,
  `forecastSub` tinyint(1) NOT NULL,
  `userId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `email_group`
--

INSERT INTO `email_group` (`id`, `emailGroupName`, `forecastSub`, `userId`) VALUES
(7, 'Testowa_v1', 1, 1),
(8, 'Testowa_v2', 0, 1),
(10, 'Problem#1', 0, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `email_messages`
--

CREATE TABLE `email_messages` (
  `id` int(11) NOT NULL,
  `topic` varchar(100) NOT NULL,
  `messageContent` longtext NOT NULL,
  `type` varchar(50) NOT NULL,
  `contactEmail` varchar(50) NOT NULL,
  `creationDate` datetime NOT NULL,
  `emailGroupId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `email_messages`
--

INSERT INTO `email_messages` (`id`, `topic`, `messageContent`, `type`, `contactEmail`, `creationDate`, `emailGroupId`) VALUES
(28, 'Witam', 'Mam problem z panelami', 'SEND_BY_CONTACT', 'koloz32@interia.pl', '2022-05-10 21:12:09', NULL),
(29, 'Dzień dobry', 'Mam problem z panelami', 'SEND_BY_CONTACT', 'abcd4@abcd.pl', '2022-05-10 21:13:08', NULL),
(30, 'Dzień dobry', 'Może pan opisać problem?', 'SEND_BY_USER', 'abcd4@abcd.pl', '2022-05-10 21:14:06', NULL),
(31, 'Dzień dobry', 'Proszę opisać dokładnie o co chodzi.', 'SEND_BY_USER', 'koloz32@interia.pl', '2022-05-10 21:14:39', NULL),
(32, 'Witam', 'Oprogramowanie wyrzuca wyjątek nr. x09999 i prosi o kontakt z dostawca paneli.', 'SEND_BY_CONTACT', 'abcd4@abcd.pl', '2022-05-10 21:18:46', NULL),
(33, 'Witam', 'Jest problem chyba z oprogramowaniem ponieważ został wyrzucony wyjątek nr. x09999.', 'SEND_BY_CONTACT', 'koloz32@interia.pl', '2022-05-10 21:19:42', NULL),
(34, 'Dzień dobry', 'Proszę o pilny kontakt!', 'SEND_BY_USER', 'wiktor@jdx.pl', '2022-05-10 21:20:36', NULL),
(40, 'Problem solution', 'ABCDEFG', 'GROUP_EMAIL', '', '2022-05-11 21:29:27', 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `lead`
--

CREATE TABLE `lead` (
  `id` int(11) NOT NULL,
  `potentialEmail` varchar(50) NOT NULL,
  `leadType` varchar(50) NOT NULL,
  `creationDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `lead`
--

INSERT INTO `lead` (`id`, `potentialEmail`, `leadType`, `creationDate`) VALUES
(2, 'test@gmail.pl', 'APP_FORM', '2022-04-07'),
(10, 'abcdefgh@abcdefgh.pl', 'APP_FORM', '2022-05-10');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `otherOptions` varchar(100) NOT NULL,
  `otherOptions2` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `product`
--

INSERT INTO `product` (`id`, `name`, `otherOptions`, `otherOptions2`) VALUES
(10, 'Testowy', 'Opis1', 'Opis2233'),
(11, 'Testowy_v2', 'ABC', 'ABC'),
(12, 'Testowy_v3', 'Test123', 'Test123');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `login` varchar(100) NOT NULL,
  `password` varchar(250) NOT NULL,
  `role` varchar(100) NOT NULL,
  `licenseEnd` date NOT NULL,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `phone` int(11) NOT NULL,
  `groupId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`id`, `email`, `login`, `password`, `role`, `licenseEnd`, `name`, `surname`, `phone`, `groupId`) VALUES
(1, 'admin@admin.pl', 'admin@admin.pl', '$2a$10$Ifu58mXSerGGqOZY4d.WAeyi6xflX4yaQbTALoYA/Zl7tNIR9clcO', 'SIEMA', '2022-03-26', 'admin', 'adminowski', 123456789, 2);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `account_group`
--
ALTER TABLE `account_group`
  ADD PRIMARY KEY (`id`),
  ADD KEY `accountId` (`groupName`);

--
-- Indeksy dla tabeli `asset`
--
ALTER TABLE `asset`
  ADD PRIMARY KEY (`id`),
  ADD KEY `productId` (`productId`),
  ADD KEY `orderedBy` (`orderedBy`),
  ADD KEY `soldBy` (`soldBy`);

--
-- Indeksy dla tabeli `company information`
--
ALTER TABLE `company information`
  ADD KEY `contactId` (`contactId`);

--
-- Indeksy dla tabeli `contact`
--
ALTER TABLE `contact`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `emailAddress` (`emailAddress`),
  ADD KEY `id` (`id`,`name`),
  ADD KEY `leadId` (`leadId`),
  ADD KEY `accountGroupId` (`accountGroupId`),
  ADD KEY `createdBy` (`createdBy`);

--
-- Indeksy dla tabeli `contact_email_group`
--
ALTER TABLE `contact_email_group`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `contactId_2` (`contactId`,`emailGroupId`),
  ADD KEY `contactId` (`contactId`),
  ADD KEY `contact_email_group_ibfk_2` (`emailGroupId`);

--
-- Indeksy dla tabeli `email_group`
--
ALTER TABLE `email_group`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`);

--
-- Indeksy dla tabeli `email_messages`
--
ALTER TABLE `email_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sender/contactEmail` (`contactEmail`) USING BTREE,
  ADD KEY `email_messages_ibfk_1` (`emailGroupId`);

--
-- Indeksy dla tabeli `lead`
--
ALTER TABLE `lead`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `lead_potentialEmail_uindex` (`potentialEmail`);

--
-- Indeksy dla tabeli `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_uindex` (`email`),
  ADD KEY `emailLogin` (`login`),
  ADD KEY `groupId` (`groupId`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `account_group`
--
ALTER TABLE `account_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `asset`
--
ALTER TABLE `asset`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT dla tabeli `contact`
--
ALTER TABLE `contact`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `contact_email_group`
--
ALTER TABLE `contact_email_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT dla tabeli `email_group`
--
ALTER TABLE `email_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `email_messages`
--
ALTER TABLE `email_messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT dla tabeli `lead`
--
ALTER TABLE `lead`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT dla tabeli `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `asset`
--
ALTER TABLE `asset`
  ADD CONSTRAINT `asset_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `product` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `asset_ibfk_2` FOREIGN KEY (`orderedBy`) REFERENCES `contact` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `asset_ibfk_3` FOREIGN KEY (`soldBy`) REFERENCES `users` (`id`) ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `company information`
--
ALTER TABLE `company information`
  ADD CONSTRAINT `company information_ibfk_1` FOREIGN KEY (`contactId`) REFERENCES `contact` (`id`);

--
-- Ograniczenia dla tabeli `contact`
--
ALTER TABLE `contact`
  ADD CONSTRAINT `contact_ibfk_2` FOREIGN KEY (`leadId`) REFERENCES `lead` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `contact_ibfk_3` FOREIGN KEY (`accountGroupId`) REFERENCES `account_group` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `contact_ibfk_4` FOREIGN KEY (`createdBy`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `contact_email_group`
--
ALTER TABLE `contact_email_group`
  ADD CONSTRAINT `contact_email_group_ibfk_1` FOREIGN KEY (`contactId`) REFERENCES `contact` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `contact_email_group_ibfk_2` FOREIGN KEY (`emailGroupId`) REFERENCES `email_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `email_group`
--
ALTER TABLE `email_group`
  ADD CONSTRAINT `email_group_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`);

--
-- Ograniczenia dla tabeli `email_messages`
--
ALTER TABLE `email_messages`
  ADD CONSTRAINT `email_messages_ibfk_1` FOREIGN KEY (`emailGroupId`) REFERENCES `email_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograniczenia dla tabeli `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`groupId`) REFERENCES `account_group` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
