-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Sep 17, 2020 at 12:53 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id14821560_db_monaksiv001`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_agenda`
--

CREATE TABLE `tblkit_agenda` (
  `id_agenda` int(11) NOT NULL,
  `id_subrapat` int(11) NOT NULL,
  `nama_agenda` varchar(50) NOT NULL,
  `tanggal_agenda` date NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_agenda`
--

INSERT INTO `tblkit_agenda` (`id_agenda`, `id_subrapat`, `nama_agenda`, `tanggal_agenda`, `status`) VALUES
(3, 5, 'RAPIM 11 FEBRUARI', '2019-02-11', 1),
(4, 3, 'SEMESTER I (2019)', '2019-02-11', 1),
(6, 4, 'SEMESTER 1 (2020)', '2020-02-11', 1),
(7, 9, 'OFI EAM 2019', '2019-01-01', 1),
(8, 10, 'KPKU2018', '2019-03-15', 1),
(9, 9, 'EAMSTAR 2019', '2019-04-01', 1),
(10, 9, 'EAM1.2019', '2019-08-30', 1),
(11, 49, 'CFB 1', '2019-08-14', 1),
(12, 49, 'SEMESTER 1 (2019)', '2019-08-15', 1),
(13, 3, 'SEMESTER 2 (2019)', '2019-08-30', 1),
(14, 33, '2020', '2019-12-04', 1),
(15, 34, '2020', '2019-12-04', 1),
(16, 9, 'EAM2.2019', '2020-01-30', 1),
(17, 4, 'RAKOR SEM-1 2020', '2020-06-12', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_jabatan`
--

CREATE TABLE `tblkit_jabatan` (
  `id_jabatan` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_unit` int(11) NOT NULL,
  `nama_jabatan` varchar(50) NOT NULL,
  `id_atasan` int(11) NOT NULL DEFAULT 1,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_jabatan`
--

INSERT INTO `tblkit_jabatan` (`id_jabatan`, `id_user`, `id_unit`, `nama_jabatan`, `id_atasan`, `status`) VALUES
(1, 1, 1, 'Admin', 0, 1),
(2, 9, 1, 'User', 1, 1),
(3, 10, 1, 'User', 1, 1),
(4, 8, 1, 'Operator', 1, 1),
(5, 11, 2, 'User', 1, 1),
(6, 12, 3, 'User', 1, 1),
(7, 13, 18, 'Viewer', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_log`
--

CREATE TABLE `tblkit_log` (
  `id_log` int(11) NOT NULL,
  `ip` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `aksi` text NOT NULL,
  `waktu` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_log`
--

INSERT INTO `tblkit_log` (`id_log`, `ip`, `nama`, `aksi`, `waktu`) VALUES
(63, '192.168.1.3', 'user1', 'Update Task', '2020-09-12 16:32:28'),
(64, '192.168.1.3', 'user1', 'Update Task', '2020-09-12 16:32:32'),
(65, '192.168.1.3', 'user1', 'Update Task', '2020-09-12 16:35:11'),
(66, '192.168.1.3', 'user1', 'Update Task', '2020-09-12 16:36:56'),
(67, '192.168.1.3', 'user1', 'Update Task', '2020-09-12 16:37:15'),
(68, '192.168.1.3', 'user1', 'Update Task', '2020-09-12 16:41:14'),
(69, '192.168.1.3', 'user1', 'Send to Approval - 48', '2020-09-12 16:41:25'),
(70, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-12 16:50:43'),
(71, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-12 16:50:48'),
(72, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 1 (2020) - Pelaksanaan Rakor untuk kegiatan penyuluhan anti narkotika', '2020-09-12 16:51:05'),
(73, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-12 17:00:57'),
(74, '192.168.1.8', 'user3', 'Login', '2020-09-12 19:00:56'),
(75, '192.168.1.8', 'user3', 'Lihat Detail Task Rapat OFI EAM 2019 - Menikmati Uwuwuwuwuwj', '2020-09-12 19:01:26'),
(76, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:01:41'),
(77, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:01:48'),
(78, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat OFI EAM 2019 - Membuat hal yang baru dalam perencanaan untuk pembuatan jaringan local', '2020-09-12 19:02:04'),
(79, '192.168.1.3', 'user1', 'Update Task', '2020-09-12 19:02:09'),
(80, '192.168.1.8', 'user3', 'Lihat Detail Task Rapat OFI EAM 2019 - Menikmati Uwuwuwuwuwj', '2020-09-12 19:05:52'),
(81, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:05:57'),
(82, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:06:03'),
(83, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:06:05'),
(84, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:06:06'),
(85, '192.168.1.8', 'user3', 'Lihat Detail Task Rapat OFI EAM 2019 - Menikmati Uwuwuwuwuwj', '2020-09-12 19:07:40'),
(86, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:08:08'),
(87, '192.168.1.8', 'user3', 'Update Task', '2020-09-12 19:08:17'),
(88, '192.168.1.8', 'user3', 'Send to Approval - 29', '2020-09-12 19:08:42'),
(89, '192.168.1.8', 'user3', 'Lihat Detail Rapat OFI EAM 2019 - Keputusan Pertama', '2020-09-12 19:12:38'),
(90, '192.168.1.8', 'user3', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-12 19:22:16'),
(91, '192.168.1.8', 'user3', 'Lihat Detail Task Rapat OFI EAM 2019 - Menikmati Uwuwuwuwuwj', '2020-09-12 19:22:25'),
(92, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-12 20:37:27'),
(93, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-12 21:21:29'),
(94, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-12 21:25:55'),
(95, '192.168.1.3', 'user1', 'Logout', '2020-09-12 23:13:12'),
(96, '192.168.1.3', 'admin', 'Login', '2020-09-12 23:13:23'),
(97, '192.168.1.3', 'admin', 'Logout', '2020-09-13 00:01:17'),
(98, '192.168.1.3', 'user1', 'Login', '2020-09-13 00:01:24'),
(99, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER 1 (2020) - Keputusan Ke 3', '2020-09-13 00:01:33'),
(100, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 1 (2020) - Pelaksanaan Rakor untuk kegiatan penyuluhan anti narkotika', '2020-09-13 00:03:16'),
(101, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 1 (2020) - Merencanakan solusi untuk spesifikasi perancangan Server UIKSBS PLN Palembang', '2020-09-13 00:03:23'),
(102, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 1 (2020) - Keputusan 2', '2020-09-13 00:03:58'),
(103, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 00:04:33'),
(104, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 1 (2020) - Merencanakan solusi untuk spesifikasi perancangan Server UIKSBS PLN Palembang', '2020-09-13 00:05:07'),
(105, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 1 (2020) - Pelaksanaan Rakor untuk kegiatan penyuluhan anti narkotika', '2020-09-13 00:05:12'),
(106, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 00:13:13'),
(107, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 00:15:41'),
(108, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 00:47:05'),
(109, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-13 00:47:12'),
(110, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-13 00:48:49'),
(111, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-13 00:52:18'),
(112, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 00:58:03'),
(113, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-13 00:58:26'),
(114, '192.168.1.3', 'user1', 'Lihat Detail Verifikasi Rapat SEMESTER I (2019) - Merencanakan Pembuatan Ruang Lingkup Server Cloud Data Realtime Sharing', '2020-09-13 00:59:36'),
(115, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 00:59:40'),
(116, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-13 01:13:17'),
(117, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 01:18:25'),
(118, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-13 01:25:56'),
(119, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 01:27:23'),
(120, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '2020-09-13 01:28:15'),
(121, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 10:47:00'),
(122, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:04:18'),
(123, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 11:04:40'),
(124, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:07:23'),
(125, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:08:31'),
(126, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:09:03'),
(127, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:09:17'),
(128, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:15:20'),
(129, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:15:29'),
(130, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:17:16'),
(131, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:19:52'),
(132, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:20:47'),
(133, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:20:50'),
(134, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:21:18'),
(135, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 11:22:13'),
(136, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:22:18'),
(137, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:25:10'),
(138, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:25:17'),
(139, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:39:38'),
(140, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:42:01'),
(141, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:42:26'),
(142, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:43:11'),
(143, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:50:28'),
(144, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:53:35'),
(145, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:54:43'),
(146, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:55:50'),
(147, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:56:17'),
(148, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:56:38'),
(149, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 11:57:10'),
(150, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:59:11'),
(151, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 11:59:12'),
(152, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 12:00:46'),
(153, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 12:02:32'),
(154, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 12:02:32'),
(155, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 12:13:53'),
(156, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 12:14:24'),
(157, '192.168.1.3', 'user1', 'Update Task', '2020-09-13 12:15:06'),
(158, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 12:17:35'),
(159, '192.168.1.3', 'user1', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-13 12:27:38'),
(160, '192.168.1.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 12:29:09'),
(161, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 14:13:38'),
(162, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 14:39:57'),
(163, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 14:40:10'),
(164, '192.168.1.3', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 14:42:29'),
(165, '36.68.238.143', 'user1', 'Login', '2020-09-13 15:07:56'),
(166, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:08:10'),
(167, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:11:07'),
(168, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:11:49'),
(169, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:14:56'),
(170, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:18:28'),
(171, '36.68.238.143', 'user1', 'Update Task', '2020-09-13 15:19:22'),
(172, '36.68.238.143', 'user1', 'Update Task', '2020-09-13 15:19:36'),
(173, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:21:31'),
(174, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:22:46'),
(175, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-13 15:25:06'),
(176, '114.125.245.89', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-14 16:01:22'),
(177, '36.68.238.143', 'user3', 'Lihat Detail Verifikasi Rapat CFB 1 - Membuat hal yang baru dalam hal CFB1 untuk yg pertama kali', '2020-09-15 09:33:14'),
(178, '36.68.238.143', 'user3', 'Lihat Detail Rapat CFB 1 - Pelaksanaan Kegiatan Operasi Ruang lingkup kerja server Maction UIKSBS PLN Palembang Sumatera Selatan', '2020-09-15 09:35:55'),
(179, '36.68.238.143', 'user3', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-15 10:05:54'),
(180, '36.68.238.143', 'user3', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-15 10:21:48'),
(181, '36.68.238.143', 'user3', 'Logout', '2020-09-15 10:25:18'),
(182, '36.68.238.143', 'admin', 'Login', '2020-09-15 10:25:35'),
(183, '36.68.238.143', 'admin', 'Lihat Detail Rapat SEMESTER 2 (2019) - Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-15 10:52:32'),
(184, '36.68.238.143', 'admin', 'Lihat Detail Rapat CFB 1 - Pelaksanaan Kegiatan Operasi Ruang lingkup kerja server Maction UIKSBS PLN Palembang Sumatera Selatan', '2020-09-15 11:16:28'),
(185, '36.68.238.143', 'admin', 'Lihat Detail Rapat SEMESTER 1 (2020) - Pelaksanaan Rakor untuk kegiatan penyuluhan anti narkotika', '2020-09-15 12:48:53'),
(186, '36.68.238.143', 'admin', 'Hapus Keputusan Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-15 12:53:51'),
(187, '36.68.238.143', 'admin', 'Hapus Keputusan Mengkaji untuk opsi Sewa Mesin Server pada PLN UIKSBS', '2020-09-15 12:54:28'),
(188, '36.68.238.143', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 2', '2020-09-15 13:11:53'),
(189, '36.68.238.143', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 2', '2020-09-15 13:15:04'),
(190, '36.68.238.143', 'user1', 'Hapus Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke- 3', '2020-09-15 13:59:00'),
(191, '36.68.238.143', 'user1', 'Hapus Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke- 3', '2020-09-15 14:03:44'),
(192, '36.68.238.143', 'user1', 'Hapus Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke- 3', '2020-09-15 14:05:12'),
(193, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-15 14:09:03'),
(194, '36.68.238.143', 'user1', 'Lihat Detail Task Rapat SEMESTER 1 (2020) - Membuat sesuatu untuk menambahkan Keputusan Yang baru pada tahun 2020', '2020-09-15 14:14:26'),
(195, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 3', '2020-09-15 14:35:51'),
(196, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 2', '2020-09-15 14:40:18'),
(197, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 3', '2020-09-15 16:24:18'),
(198, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 2', '2020-09-15 16:24:21'),
(199, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server', '2020-09-15 16:25:32'),
(200, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER 1 (2020) - Pelaksanaan Rakor untuk kegiatan penyuluhan anti narkotika', '2020-09-15 16:26:08'),
(201, '36.68.238.143', 'user1', 'Update Profile', '2020-09-15 16:28:38'),
(202, '36.68.238.143', 'user1', 'Update Profile', '2020-09-15 16:28:46'),
(203, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 3', '2020-09-15 19:01:38'),
(204, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke- 3', '2020-09-15 19:01:38'),
(205, '36.68.238.143', 'user1', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-15 21:44:59'),
(206, '36.68.238.143', 'user1', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-15 21:47:06'),
(207, '36.68.238.143', 'user1', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-15 21:47:18'),
(208, '36.68.238.143', 'user1', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-15 21:47:31'),
(209, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-15 22:27:50'),
(210, '36.68.238.143', 'user1', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-16 01:59:15'),
(211, '36.68.238.143', 'user1', 'Tambah Keputusan Keputusan Sekian 100', '2020-09-16 02:04:18'),
(212, '36.68.238.143', 'user1', 'Lihat Detail Rapat SEMESTER I (2019) - Keputusan Sekian 100', '2020-09-16 02:04:22'),
(213, '36.68.238.143', 'user1', 'Hapus Keputusan Keputusan Sekian 100', '2020-09-16 02:04:31'),
(214, '125.165.226.149', 'user1', 'Lihat Detail Verifikasi Rapat SEMESTER I (2019) - Merencanakan Pembuatan Ruang Lingkup Server Cloud Data Realtime Sharing', '2020-09-16 07:36:14'),
(215, '114.125.250.211', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-16 10:40:50'),
(216, '125.165.130.3', 'user1', 'Tambah Keputusan Perencanaan Software Server Ubuntu pada jaringan UIKSBS', '2020-09-17 19:09:36'),
(217, '125.165.130.3', 'user1', 'Lihat Detail Rapat RAKOR SEM-1 2020 - Perencanaan Software Server Ubuntu pada jaringan UIKSBS', '2020-09-17 19:09:53'),
(218, '125.165.130.3', 'user1', 'Lihat Detail Rapat RAKOR SEM-1 2020 - Perencanaan Software Server Ubuntu pada jaringan UIKSBS', '2020-09-17 19:12:57'),
(219, '125.165.130.3', 'user1', 'Hapus Keputusan Perencanaan Software Server Ubuntu pada jaringan UIKSBS', '2020-09-17 19:14:19'),
(220, '125.165.130.3', 'user1', 'Tambah Keputusan Mengalokasikan Server UIKSBS', '2020-09-17 19:15:44'),
(221, '125.165.130.3', 'user1', 'Lihat Detail Rapat SEMESTER 1 (2020) - Pelaksanaan Rakor untuk kegiatan penyuluhan anti narkotika', '2020-09-17 19:16:11'),
(222, '125.165.130.3', 'user1', 'Lihat Detail Rapat SEMESTER 2 (2019) - Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-17 19:16:17'),
(223, '125.165.130.3', 'user1', 'Tambah Keputusan Pengalokasian Software pada server Jaringan Internet Unit ke-1 UIKSBS', '2020-09-17 19:17:47'),
(224, '125.165.130.3', 'user1', 'Lihat Detail Rapat SEMESTER 1 (2020) - Pengalokasian Software pada server Jaringan Internet Unit ke-1 UIKSBS', '2020-09-17 19:18:17'),
(225, '125.165.130.3', 'user1', 'Update Keputusan Keputusan Pengubahan Lokasi Server Jaringan Unit SDM', '2020-09-17 19:20:10'),
(226, '125.165.130.3', 'user1', 'Lihat Detail Rapat SEMESTER 1 (2020) - Keputusan Pengubahan Lokasi Server Jaringan Unit SDM', '2020-09-17 19:20:25'),
(227, '125.165.130.3', 'user1', 'Logout', '2020-09-17 19:20:54'),
(228, '125.165.130.3', 'admin', 'Login - gagal', '2020-09-17 19:21:04'),
(229, '125.165.130.3', 'admin', 'Login - gagal', '2020-09-17 19:21:10'),
(230, '125.165.130.3', 'Admin', 'Login - gagal', '2020-09-17 19:21:28'),
(231, '125.165.130.3', 'Admin', 'Login', '2020-09-17 19:21:52'),
(232, '125.165.130.3', 'admin', 'Update Keputusan Keputusan Pengubahan Lokasi Server Jaringan Unit SDM', '2020-09-17 19:29:05'),
(233, '125.165.130.3', 'admin', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-17 19:39:27'),
(234, '125.165.130.3', 'admin', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke - 13', '2020-09-17 19:39:38'),
(235, '125.165.130.3', 'admin', 'Update Keputusan Perencanaan Opsi untuk membuat clonning data server yang ke- 2', '2020-09-17 19:42:00');

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_monitoring`
--

CREATE TABLE `tblkit_monitoring` (
  `ID_MON` int(11) NOT NULL,
  `ID_SUBRAPAT` int(11) NOT NULL,
  `ID_AGENDA` int(11) NOT NULL,
  `PROG_ID` varchar(20) NOT NULL,
  `TGL_REPORT` date NOT NULL,
  `TGL_TARGET` date NOT NULL,
  `KEPUTUSAN` varchar(200) NOT NULL,
  `RENCANA_AKSI` varchar(1000) NOT NULL DEFAULT 'Belum ada rencana aksi',
  `PIC` varchar(10) NOT NULL,
  `TGL_SELESAI` date DEFAULT '0000-00-00',
  `APPROVAL` varchar(10) NOT NULL,
  `TGL_APPROVAL` date NOT NULL DEFAULT '0000-00-00',
  `VERIFIKATOR` varchar(10) NOT NULL,
  `TGL_CLOSED` date NOT NULL DEFAULT '0000-00-00',
  `KOMENTAR` text NOT NULL DEFAULT 'Tidak ada komentar',
  `LAST_STATUS` int(1) NOT NULL DEFAULT 1,
  `LAST_UPDATES` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LAMPIRAN` text NOT NULL DEFAULT 'Tidak ada lampiran'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_monitoring`
--

INSERT INTO `tblkit_monitoring` (`ID_MON`, `ID_SUBRAPAT`, `ID_AGENDA`, `PROG_ID`, `TGL_REPORT`, `TGL_TARGET`, `KEPUTUSAN`, `RENCANA_AKSI`, `PIC`, `TGL_SELESAI`, `APPROVAL`, `TGL_APPROVAL`, `VERIFIKATOR`, `TGL_CLOSED`, `KOMENTAR`, `LAST_STATUS`, `LAST_UPDATES`, `LAMPIRAN`) VALUES
(1, 3, 4, 'RKR2019.302', '2019-02-14', '2020-08-01', 'Menyusun Road Map terkait pengelolaan FABA beserta standarisasi perhitungan biaya pengelolaan FABA', 'Monitoring 302', 'user1', '2020-07-29', 'user1', '2020-07-29', 'user1', '2020-07-29', 'uushdvsjsisksnbsbs mpss mpss', 3, '0000-00-00 00:00:00', 'Monitoring_1_RK2019.302.pdf'),
(2, 3, 6, 'RKR2019.2', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', 'Membuat Monitoring Penyampaian Kelengkapan Dokumen SKKI 2019', 'user1', '0000-00-01', 'user4', '0000-00-01', '6895005E', '0000-00-01', '', 5, '0000-00-00 00:00:00', 'Monitoring_2_RKR2019.2.pdf'),
(3, 3, 6, 'RKR2019.3', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', 'KKP sudah dikirim', '8509304Z', '0000-00-02', '7604004G', '0000-00-02', '6895005E', '0000-00-02', '', 2, '0000-00-00 00:00:00', ''),
(4, 3, 6, 'RKR2019.4', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', 'UPDK  BKL telah menyampaikan dokumen kelengkapan SKI 2019 ke UIK SBS', '8207212Z', '2019-03-29', '7704009B2', '2019-03-30', '6895005E', '2019-04-05', 'Dokumen kelengkapan SKI 2019 telah disampaikan ke UIK SBS', 5, '0000-00-00 00:00:00', 'Monitoring_4_RKR2019.4.rar'),
(5, 3, 6, 'RKR2019.5', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', '', '8509422Z', '0000-00-04', '8006237Z', '0000-00-04', '6895005E', '0000-00-04', '', 1, '0000-00-00 00:00:00', ''),
(6, 3, 6, 'RKR2019.6', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', 'on progress', 'user1', '2019-03-15', 'user2', '2019-03-26', 'user2', '2019-04-05', 'Telah dikirimkan dokumen kelengkapan investasi ke UIKSBS', 5, '0000-00-00 00:00:00', ''),
(7, 3, 6, 'RKR2019.7', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', 'Menyusun dokumen kelengkapan investasi tahun 2019 dan menyampaikan ke Kantor UIKSBS Palembang. ', '7093203R', '2019-03-20', '8005005B2', '2019-03-27', '6895005E', '2019-04-04', 'Sudah disampaikan', 5, '0000-00-00 00:00:00', 'Monitoring_7_RKR2019.7.pdf'),
(8, 3, 6, 'RKR2019.8', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', '5 dokumen sudah diserahkan dengan status lengkap', 'user1', '2020-07-28', '7905008B2', '0000-00-07', '6895005E', '0000-00-07', '', 3, '0000-00-00 00:00:00', 'Monitoring_8_RKR2019.8.pdf'),
(9, 3, 6, 'RKR2019.9', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', '', '7095120R', '0000-00-08', '7602006B2', '0000-00-08', '6895005E', '0000-00-08', '', 1, '0000-00-00 00:00:00', ''),
(10, 3, 6, 'RKR2019.10', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', '', '8408506Z', '0000-00-09', '7805003B2', '0000-00-09', '6895005E', '0000-00-09', '', 1, '0000-00-00 00:00:00', ''),
(11, 3, 6, 'RKR2019.11', '2019-02-14', '2019-03-31', 'Unit pelaksana agar dapat segera menyampaikan Dokumen Kelengkapan Investasi 2019 ke Kantor Induk ', '8 dokumen pada awal maret siap di lakdan.\r\n14 dokumen akan di proses selanjutnya.', '8309407Z', '2019-04-02', '7806026Z', '0000-00-10', '6895005E', '0000-00-10', '', 3, '0000-00-00 00:00:00', 'Monitoring_11_RKR2019.11.pdf'),
(12, 3, 6, 'RKR2019.12', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '', '9214624ZY', '0000-00-11', '7394143B', '0000-00-11', '8711801Z', '0000-00-11', '', 1, '0000-00-00 00:00:00', ''),
(13, 3, 6, 'RKR2019.13', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '', '6993250B', '0000-00-12', '7193517B', '0000-00-12', '8711801Z', '0000-00-12', '', 1, '0000-00-00 00:00:00', ''),
(14, 3, 6, 'RKR2019.14', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '', '8609354Z', '0000-00-13', 'user1', '0000-00-13', '8711801Z', '0000-00-13', '', 1, '0000-00-00 00:00:00', ''),
(15, 3, 6, 'RKR2019.15', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '', '8812655ZY', '0000-00-14', '8509426Z', '0000-00-14', '8711801Z', '0000-00-14', '', 1, '0000-00-00 00:00:00', ''),
(16, 3, 4, 'RKR2019.16', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '', 'user1', '0000-00-15', 'user1', '0000-00-15', 'user1', '0000-00-15', '', 2, '2020-08-02 09:31:23', ''),
(17, 3, 6, 'RKR2019.17', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '', '7502049B2', '0000-00-16', '7095120R', '0000-00-16', '8711801Z', '0000-00-16', '', 1, '0000-00-00 00:00:00', ''),
(18, 3, 4, 'RKR2019.18', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', '', '8206053b2', '0000-00-17', '7495066B', '0000-00-17', '8711801Z', '0000-00-17', '', 1, '0000-00-00 00:00:00', ''),
(19, 3, 4, 'RKR2019.19', '2019-02-14', '2020-08-03', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', 'Perspektif dalam hal pengambilan keputusan UIKSBS server linux', 'user1', '0000-00-18', 'user1', '0000-00-18', 'user1', '0000-00-18', '', 2, '2020-08-02 02:11:10', 'Monitoring_19_RKR2019.19.pdf'),
(20, 3, 4, 'RKR2019.20', '2019-02-14', '2019-06-30', 'Seluruh Usulan anggaran preventif & prediktif harus berdasarkan WO yang sudah tercreate di maximo.', 'gyunbv', 'user2', '2020-08-07', 'user1', '0000-00-19', 'user1', '0000-00-19', '', 3, '2020-08-07 05:08:04', 'Monitoring_20_RKR2019.20.pdf'),
(21, 9, 7, 'OFIEAM.1', '2020-07-14', '2020-09-14', 'Keputusan Pertama', 'Belum ada rencana aksi', 'user1', '0000-00-00', 'user2', '0000-00-00', 'user3', '0000-00-00', '', 1, '0000-00-00 00:00:00', ''),
(23, 9, 7, 'OFIEAM.2', '2020-07-14', '2020-09-14', 'Keputusan Pertama', 'Audajsjjs', 'user1', '0000-00-00', 'user2', '0000-00-00', 'user2', '0000-00-00', '', 2, '0000-00-00 00:00:00', 'Monitoring_23_OFIEAM.101.pdf'),
(24, 9, 7, 'OFIEAM.3', '2020-07-14', '2020-09-14', 'Keputusan Ketiga Bersamaan dengan pembaharuan fasilitas IT pada lingkungan PLN Unit Pembangkitan', 'Melakukan Perencanaan untuk melakukan rencana', 'user1', '2020-07-20', 'user2', '0000-00-00', 'user3', '0000-00-00', '', 3, '0000-00-00 00:00:00', 'Monitoring_24_OFIEAM.102.pdf'),
(25, 3, 6, 'RKR2019.21', '2020-07-22', '2020-08-31', 'Membuat sesuatu untuk menambahkan Keputusan Yang baru pada tahun 2020', 'u', 'user1', '0000-00-00', 'user2', '0000-00-00', 'user3', '0000-00-00', '', 2, '0000-00-00 00:00:00', ''),
(26, 9, 7, 'OFIEAM.4', '2020-07-22', '2020-09-30', 'Membuat hal yang baru dalam perencanaan untuk pembuatan jaringan local', 'Belum ada rencana aksi', 'user1', '0000-00-00', 'user1', '0000-00-00', 'user1', '0000-00-00', '', 2, '0000-00-00 00:00:00', ''),
(27, 3, 4, 'RKR2019.22', '2020-07-01', '2020-07-29', 'Merencanakan Pembuatan Ruang Lingkup Server Cloud Data Realtime Sharing', 'Membuat rencana aksi yang baru dan dapaat di selesaikan dengan baik', 'user1', '2020-07-31', 'user1', '2020-08-03', 'user1', '0000-00-00', 'nice', 4, '2020-08-03 02:18:06', 'Monitoring_27_RKR2019.310.pdf'),
(28, 9, 7, 'OFIEAM.5', '2020-07-23', '2020-08-27', 'Jdkdksjdndbdjdhsbssbbb', 'Belum ada rencana aksi', 'user1', '0000-00-00', 'user2', '0000-00-00', 'user3', '0000-00-00', '', 2, '0000-00-00 00:00:00', 'Monitoring_28_OFIEAM.5.pdf'),
(29, 9, 7, 'OFIEAM.6', '2020-07-26', '2020-08-28', 'Menikmati Uwuwuwuwuwj', 'gt', 'user3', '2020-09-12', 'user1', '0000-00-00', 'user2', '0000-00-00', '', 3, '2020-08-08 06:15:07', 'Monitoring_29_OFIEAM.6.pdf'),
(30, 9, 7, 'OFIEAM.7', '2020-07-26', '2020-07-31', 'Keputusan Pengambilan Material untuk Server', 'Belum ada rencana aksi', 'user1', '0000-00-00', 'user2', '0000-00-00', 'user3', '0000-00-00', '', 2, '2020-08-08 05:53:10', 'Monitoring_30_OFIEAM.7.pdf'),
(31, 49, 11, 'OFICFB.1', '2020-07-26', '2020-08-28', 'Membuat hal yang baru dalam hal CFB1 untuk yg pertama kali', 'Rencana Uji Coba 1', 'user1', '2020-07-27', 'user2', '2020-07-27', 'user3', '0000-00-00', 'qwqe', 4, '0000-00-00 00:00:00', 'Monitoring_31_OFICFB.1.pdf'),
(32, 49, 11, 'OFICFB.2', '2020-07-26', '2020-08-23', 'Pelaksanaan Kegiatan Operasi Ruang lingkup kerja server Maction UIKSBS PLN Palembang Sumatera Selatan', 'Mencoba Alur dari Log User pada database', 'user1', '2020-08-02', 'user1', '2020-07-31', 'user1', '2020-07-31', 'uuhh', 3, '2020-08-02 08:40:51', 'Monitoring_32_OFICFB.2.pdf'),
(33, 4, 6, 'RKR2020.1', '2020-07-27', '2020-08-31', 'Keputusan Semeter 1 - 2020', 'Belum ada rencana aksi', 'user2', '0000-00-00', 'user3', '0000-00-00', 'user1', '0000-00-00', '', 1, '0000-00-00 00:00:00', ''),
(34, 4, 6, 'RKR2020.2', '2020-07-28', '2020-09-30', 'Keputusan Pengubahan Lokasi Server Jaringan Unit SDM', 'Planning Pembuatan Server Skala Minimum', 'user2', '0000-00-00', 'user1', '0000-00-00', 'admin', '0000-00-00', '', 1, '2020-09-17 07:29:03', ''),
(35, 4, 6, 'RKR2020.3', '2020-07-28', '2020-08-31', 'Keputusan Ke 3', 'Melaksanakan Kegiatan Pembaharuan Software pada Aplikasi Server Linux Jaringan Local', 'user1', '2020-08-03', 'user1', '0000-00-00', 'user1', '0000-00-00', '', 3, '2020-07-30 08:40:51', 'Monitoring_35_RKR2020.3.pdf'),
(44, 3, 4, 'RKR2019.23', '2020-07-29', '2020-07-31', 'Perencanaan Opsi untuk membuat clonning data server', 'Pelaksanaan Rencana', 'user1', '2020-08-03', 'user1', '0000-00-00', 'user2', '0000-00-00', '', 3, '2020-08-08 12:20:02', 'Monitoring_44_RKR2019.23.pdf'),
(52, 4, 6, 'RKR2020.4', '2020-07-30', '2020-07-30', 'Merencanakan solusi untuk spesifikasi perancangan Server UIKSBS PLN Palembang', 'Monitoring Planning Pemasangan Opsi Mesin Pendingin Server', 'user1', '2020-08-02', 'user1', '2020-08-02', 'user1', '0000-00-00', 'Nice', 3, '2020-08-03 02:21:57', 'Monitoring_52_RKR2020.4.pdf'),
(53, 4, 6, 'RKR2020.5', '2020-08-02', '2020-08-26', 'Pelaksanaan Rakor untuk kegiatan penyuluhan anti narkotika', 'Pembaharuan Ruang Lingkup Server', 'user1', '2020-08-02', 'user1', '2020-08-02', 'user1', '0000-00-00', 'ndjsk', 4, '2020-08-02 02:24:52', 'Monitoring_53_RKR2020.5.pdf'),
(54, 3, 4, 'RKR2019.24', '2020-08-11', '2020-09-16', 'Perencanaan Opsi untuk membuat clonning data server yang ke- 2', 'Belum ada rencana aksi', 'user1', '0000-00-00', 'user1', '0000-00-00', 'user1', '0000-00-00', '', 1, '2020-09-17 07:41:59', ''),
(58, 3, 13, 'RKR2019.25', '2020-09-13', '2020-09-15', 'Perencanaan Opsi untuk membuat clonning data server yang ke - 13', 'Belum ada rencana aksi', 'user1', '0000-00-00', 'user1', '0000-00-00', 'user1', '0000-00-00', '', 1, '2020-09-17 07:39:37', ''),
(61, 4, 6, 'RKR2020.6', '2020-09-17', '2020-09-30', 'Pengalokasian Software pada server Jaringan Internet Unit ke-1 UIKSBS', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran');

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_rapat`
--

CREATE TABLE `tblkit_rapat` (
  `id_rapat` int(11) NOT NULL,
  `nama_rapat` varchar(50) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_rapat`
--

INSERT INTO `tblkit_rapat` (`id_rapat`, `nama_rapat`, `status`) VALUES
(1, 'RAKOR', 1),
(3, 'RAPIM', 1),
(4, 'PROGRAM KERJA', 1),
(5, 'OFI', 1),
(6, 'PLN REGSUM', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_role`
--

CREATE TABLE `tblkit_role` (
  `id_role` int(11) NOT NULL,
  `id_jabatan` int(11) NOT NULL,
  `id_subrapat` int(11) NOT NULL,
  `nama_role` varchar(50) NOT NULL,
  `status_role` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_role`
--

INSERT INTO `tblkit_role` (`id_role`, `id_jabatan`, `id_subrapat`, `nama_role`, `status_role`) VALUES
(1, 1, 3, 'Admin', 1),
(2, 1, 4, 'Admin', 1),
(3, 1, 49, 'Admin', 1),
(4, 1, 9, 'Admin', 1),
(5, 1, 29, 'Admin', 1),
(6, 1, 47, 'Admin', 1),
(7, 2, 49, 'User', 1),
(8, 2, 9, 'User', 1),
(9, 2, 3, 'User', 1),
(10, 2, 4, 'User', 1),
(11, 3, 4, 'User', 1),
(12, 3, 3, 'User', 1),
(13, 3, 9, 'User', 1),
(14, 3, 49, 'User', 1),
(15, 4, 3, 'Operator', 1),
(16, 4, 4, 'Operator', 1),
(17, 4, 9, 'Operator', 1),
(18, 4, 49, 'Operator', 1),
(19, 5, 30, 'User', 1),
(20, 5, 4, 'User', 1),
(21, 5, 9, 'User', 1),
(22, 5, 49, 'User', 1),
(23, 6, 31, 'User', 1),
(24, 6, 4, 'User', 1),
(25, 6, 9, 'User', 1),
(26, 6, 49, 'User', 1),
(28, 1, 10, 'Admin', 1),
(29, 1, 30, 'Admin', 1),
(30, 1, 31, 'Admin', 1),
(31, 1, 32, 'Admin', 1),
(32, 1, 33, 'Admin', 1),
(33, 1, 34, 'Admin', 1),
(34, 1, 35, 'Admin', 1),
(35, 1, 36, 'Admin', 1),
(36, 1, 37, 'Admin', 1),
(37, 1, 38, 'Admin', 1),
(38, 1, 39, 'Admin', 1),
(39, 1, 40, 'Admin', 1),
(40, 1, 41, 'Admin', 1),
(41, 1, 42, 'Admin', 1),
(42, 1, 43, 'Admin', 1),
(43, 1, 44, 'Admin', 1),
(44, 1, 45, 'Admin', 1),
(45, 1, 46, 'Admin', 1),
(47, 1, 48, 'Admin', 1),
(48, 1, 51, 'Admin', 1),
(49, 1, 52, 'Admin', 1),
(50, 7, 46, 'Viewer', 1),
(51, 4, 10, 'Operator', 1),
(52, 4, 29, 'Operator', 1),
(53, 4, 30, 'Operator', 1),
(54, 4, 31, 'Operator', 1),
(55, 4, 32, 'Operator', 1),
(56, 4, 43, 'Operator', 1),
(57, 4, 44, 'Operator', 1),
(58, 4, 45, 'Operator', 1),
(59, 4, 46, 'Operator', 1),
(60, 4, 47, 'Operator', 1),
(61, 4, 48, 'Operator', 1),
(62, 4, 51, 'Operator', 1),
(63, 4, 52, 'Operator', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_subrapat`
--

CREATE TABLE `tblkit_subrapat` (
  `id_subrapat` int(11) NOT NULL,
  `id_rapat` int(11) NOT NULL,
  `nama_subrapat` varchar(50) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_subrapat`
--

INSERT INTO `tblkit_subrapat` (`id_subrapat`, `id_rapat`, `nama_subrapat`, `status`) VALUES
(3, 1, '2019', 1),
(4, 1, '2020', 1),
(9, 5, 'EAM', 1),
(10, 5, 'KPKU', 1),
(29, 4, 'KI ENJINIRING', 1),
(30, 4, 'KI PRODUKSI', 1),
(31, 4, 'KI KEUANGAN', 1),
(32, 4, 'KI SDM & UMUM', 1),
(33, 4, 'UPDK KERAMASAN', 1),
(34, 4, 'UPDK JAMBI', 1),
(35, 4, 'UPK BENGKULU', 1),
(36, 4, 'UPDK BANDAR ALMPUNG ', 1),
(37, 4, 'UPK TARAHAN', 1),
(38, 4, 'UPK SEBALANG', 1),
(39, 4, 'UPK TELUK SIRIH', 1),
(40, 4, 'UPK OMBILIN', 1),
(41, 4, 'UPK BUKITTINGGI', 1),
(42, 4, 'UPK BUKIT ASAM', 1),
(43, 4, 'KI RENDAN', 1),
(44, 4, 'KI LAKDAN', 1),
(45, 4, 'KI K3 L', 1),
(46, 4, 'KI STAFF AHLI', 1),
(47, 6, 'FORUM PEMBANGKITAN', 1),
(48, 6, 'RAKOR REGSUM', 1),
(49, 5, 'CFB', 1),
(51, 3, '2019', 1),
(52, 3, '2020', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_unit`
--

CREATE TABLE `tblkit_unit` (
  `id_unit` int(11) NOT NULL,
  `nama_unit` varchar(50) NOT NULL,
  `kode_unit` varchar(10) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_unit`
--

INSERT INTO `tblkit_unit` (`id_unit`, `nama_unit`, `kode_unit`, `status`) VALUES
(1, 'KI ENJINIRING', 'ENJ', 1),
(2, 'KI PRODUKSI', 'PRO', 1),
(3, 'KI KEUANGAN', 'KEU', 1),
(4, 'KI SDM & UMUM', 'SUM', 1),
(5, 'UPDK KERAMASAN', 'KRM', 1),
(6, 'UPDK JAMBI', 'JMB', 1),
(7, 'UPK BENGKULU', 'BKL', 1),
(8, 'UPDK BANDAR LAMPUNG', 'BDL', 1),
(9, 'UPK TARAHAN', 'TAR', 1),
(10, 'UPK SEBALANG', 'BLG', 1),
(11, 'UPK TELUK SIRIH', 'TIR', 1),
(12, 'UPK OMBILIN', 'OMB', 1),
(13, 'UPK BUKITTINGGI', 'BKT', 1),
(14, 'UPK BUKIT ASAM', 'BAM', 1),
(15, 'KI RENDAN', 'REN', 1),
(16, 'KI LAKDAN', 'LAK', 1),
(17, 'KI K3 L', 'K3L', 1),
(18, 'KI STAFF AHLI', 'STH', 1),
(19, 'UPHK PALEMBANG', 'UPHK', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tblkit_user`
--

CREATE TABLE `tblkit_user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `status` varchar(3) NOT NULL DEFAULT 'off',
  `sessionID` varchar(30) NOT NULL,
  `lastLogin` datetime DEFAULT '0000-00-00 00:00:00',
  `level` int(1) DEFAULT 0 COMMENT 'Level : 1 = admin, 2 = user, 3 = operator, 4 = review'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblkit_user`
--

INSERT INTO `tblkit_user` (`id_user`, `username`, `password`, `nama`, `email`, `status`, `sessionID`, `lastLogin`, `level`) VALUES
(1, 'admin', 'a5e8eba644fdfcfdb4b9000693550c40', 'Admin', 'admin@gmail.com', 'off', '', '2020-09-17 19:49:22', 1),
(8, 'user1', 'e10adc3949ba59abbe56e057f20f883e', 'User - Operator', 'user1@gmail.com', 'off', '8a1c9fe7-3a0c-45e8-9ecb-4bb8ae', '2020-09-17 19:20:55', 3),
(9, 'user2', 'e10adc3949ba59abbe56e057f20f883e', 'Lukman Haris', 'user2@gmail.com', 'off', '193u19hdaad', '2020-07-27 11:31:14', 2),
(10, 'user3', 'e10adc3949ba59abbe56e057f20f883e', 'Andi Wibowo', 'user3@gmail.com', 'off', '13813ahdgadtae12', '2020-09-15 10:25:19', 2),
(11, 'user4', 'e10adc3949ba59abbe56e057f20f883e', 'Budi Wijaya', 'user4@gmail.com', 'off', 'aefa1931ada', '0000-00-00 00:00:00', 2),
(12, 'user5', 'e10adc3949ba59abbe56e057f20f883e', 'Mehri Paniza', 'user5@gmail.com', 'off', 'adajhd71bda11', '0000-00-00 00:00:00', 2),
(13, 'user6', 'e10adc3949ba59abbe56e057f20f883e', 'Viola Anatasya', 'user6@gmail.com', 'off', 'adahh28112', '2020-09-11 12:20:31', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblkit_agenda`
--
ALTER TABLE `tblkit_agenda`
  ADD PRIMARY KEY (`id_agenda`);

--
-- Indexes for table `tblkit_jabatan`
--
ALTER TABLE `tblkit_jabatan`
  ADD PRIMARY KEY (`id_jabatan`);

--
-- Indexes for table `tblkit_log`
--
ALTER TABLE `tblkit_log`
  ADD PRIMARY KEY (`id_log`);

--
-- Indexes for table `tblkit_monitoring`
--
ALTER TABLE `tblkit_monitoring`
  ADD PRIMARY KEY (`ID_MON`);

--
-- Indexes for table `tblkit_rapat`
--
ALTER TABLE `tblkit_rapat`
  ADD PRIMARY KEY (`id_rapat`);

--
-- Indexes for table `tblkit_role`
--
ALTER TABLE `tblkit_role`
  ADD PRIMARY KEY (`id_role`);

--
-- Indexes for table `tblkit_subrapat`
--
ALTER TABLE `tblkit_subrapat`
  ADD PRIMARY KEY (`id_subrapat`);

--
-- Indexes for table `tblkit_unit`
--
ALTER TABLE `tblkit_unit`
  ADD PRIMARY KEY (`id_unit`);

--
-- Indexes for table `tblkit_user`
--
ALTER TABLE `tblkit_user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblkit_agenda`
--
ALTER TABLE `tblkit_agenda`
  MODIFY `id_agenda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `tblkit_jabatan`
--
ALTER TABLE `tblkit_jabatan`
  MODIFY `id_jabatan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tblkit_log`
--
ALTER TABLE `tblkit_log`
  MODIFY `id_log` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=236;

--
-- AUTO_INCREMENT for table `tblkit_monitoring`
--
ALTER TABLE `tblkit_monitoring`
  MODIFY `ID_MON` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT for table `tblkit_rapat`
--
ALTER TABLE `tblkit_rapat`
  MODIFY `id_rapat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tblkit_role`
--
ALTER TABLE `tblkit_role`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `tblkit_subrapat`
--
ALTER TABLE `tblkit_subrapat`
  MODIFY `id_subrapat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT for table `tblkit_unit`
--
ALTER TABLE `tblkit_unit`
  MODIFY `id_unit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `tblkit_user`
--
ALTER TABLE `tblkit_user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
