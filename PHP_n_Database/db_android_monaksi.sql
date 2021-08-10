-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 15, 2021 at 04:22 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_android_monaksi`
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
(1, '180.242.21.134', 'admin', 'Tambah Keputusan Test 123', '2020-12-17 08:35:23'),
(2, '180.242.21.134', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - Test 123', '2020-12-17 08:35:29'),
(3, '180.242.21.134', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - Test 123', '2020-12-17 08:35:59'),
(4, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Test 123', '2020-12-17 08:36:24'),
(5, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:36:44'),
(6, '180.242.21.134', 'admin', 'Send to Approval - 1', '2020-12-17 08:36:56'),
(7, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Test 123', '2020-12-17 08:37:00'),
(8, '180.242.21.134', 'admin', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Test 123', '2020-12-17 08:37:08'),
(9, '180.242.21.134', 'admin', 'Revised Task - 1', '2020-12-17 08:37:26'),
(10, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Test 123', '2020-12-17 08:37:34'),
(11, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:37:53'),
(12, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Test 123', '2020-12-17 08:38:54'),
(13, '180.242.21.134', 'admin', 'Hapus Keputusan Test 123', '2020-12-17 08:39:38'),
(14, '180.242.21.134', 'admin', 'Tambah Keputusan test', '2020-12-17 08:44:30'),
(15, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - test', '2020-12-17 08:44:36'),
(16, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:44:48'),
(17, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:45:04'),
(18, '180.242.21.134', 'admin', 'Hapus Keputusan test', '2020-12-17 08:45:41'),
(19, '180.242.21.134', 'admin', 'Tambah Keputusan gfccc', '2020-12-17 08:45:51'),
(20, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - gfccc', '2020-12-17 08:45:55'),
(21, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:46:12'),
(22, '180.242.21.134', 'admin', 'Hapus Keputusan gfccc', '2020-12-17 08:51:05'),
(23, '180.242.21.134', 'admin', 'Tambah Keputusan chhh', '2020-12-17 08:51:21'),
(24, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - chhh', '2020-12-17 08:51:28'),
(25, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:51:42'),
(26, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:51:58'),
(27, '180.242.21.134', 'admin', 'Hapus Keputusan chhh', '2020-12-17 08:53:35'),
(28, '180.242.21.134', 'admin', 'Tambah Keputusan vccf', '2020-12-17 08:53:43'),
(29, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - vccf', '2020-12-17 08:53:47'),
(30, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:54:04'),
(31, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - vccf', '2020-12-17 08:55:45'),
(32, '180.242.21.134', 'admin', 'Hapus Keputusan vccf', '2020-12-17 08:56:49'),
(33, '180.242.21.134', 'admin', 'Tambah Keputusan hhhhgg', '2020-12-17 08:57:09'),
(34, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - hhhhgg', '2020-12-17 08:57:13'),
(35, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 08:57:31'),
(36, '180.242.21.134', 'admin', 'Hapus Keputusan hhhhgg', '2020-12-17 08:59:55'),
(37, '180.242.21.134', 'admin', 'Tambah Keputusan gff', '2020-12-17 09:00:04'),
(38, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - gff', '2020-12-17 09:00:08'),
(39, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:00:18'),
(40, '180.242.21.134', 'admin', 'Hapus Keputusan gff', '2020-12-17 09:02:25'),
(41, '180.242.21.134', 'admin', 'Tambah Keputusan jjj', '2020-12-17 09:02:33'),
(42, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - jjj', '2020-12-17 09:02:37'),
(43, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:02:47'),
(44, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:02:59'),
(45, '180.242.21.134', 'admin', 'Hapus Keputusan jjj', '2020-12-17 09:03:30'),
(46, '180.242.21.134', 'admin', 'Tambah Keputusan h', '2020-12-17 09:03:38'),
(47, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - h', '2020-12-17 09:03:42'),
(48, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:03:52'),
(49, '180.242.21.134', 'admin', 'Hapus Keputusan h', '2020-12-17 09:05:33'),
(50, '180.242.21.134', 'admin', 'Tambah Keputusan cc', '2020-12-17 09:05:43'),
(51, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - cc', '2020-12-17 09:05:47'),
(52, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:05:58'),
(53, '180.242.21.134', 'admin', 'Hapus Keputusan cc', '2020-12-17 09:09:52'),
(54, '180.242.21.134', 'admin', 'Tambah Keputusan ffdd', '2020-12-17 09:10:00'),
(55, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - ffdd', '2020-12-17 09:10:09'),
(56, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:10:28'),
(57, '180.242.21.134', 'admin', 'Hapus Keputusan ffdd', '2020-12-17 09:12:36'),
(58, '180.242.21.134', 'admin', 'Tambah Keputusan ffc', '2020-12-17 09:12:44'),
(59, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - ffc', '2020-12-17 09:12:47'),
(60, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:12:58'),
(61, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - ffc', '2020-12-17 09:13:30'),
(62, '180.242.21.134', 'admin', 'Hapus Keputusan ffc', '2020-12-17 09:14:40'),
(63, '180.242.21.134', 'admin', 'Tambah Keputusan cddd', '2020-12-17 09:14:49'),
(64, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - cddd', '2020-12-17 09:14:53'),
(65, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:15:03'),
(66, '180.242.21.134', 'admin', 'Tambah Keputusan  vb', '2020-12-17 09:16:49'),
(67, '180.242.21.134', 'admin', 'Hapus Keputusan cddd', '2020-12-17 09:16:53'),
(68, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) -  vb', '2020-12-17 09:16:58'),
(69, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:17:07'),
(70, '180.242.21.134', 'admin', 'Update Task', '2020-12-17 09:17:42'),
(71, '180.242.21.134', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) -  vb', '2020-12-17 09:17:54'),
(72, '182.1.235.65', 'admin', 'Hapus Keputusan  vb', '2020-12-17 10:44:11'),
(73, '182.1.235.65', 'admin', 'Tambah Keputusan uyyy', '2020-12-17 10:44:19'),
(74, '182.1.235.65', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - uyyy', '2020-12-17 10:44:23'),
(75, '182.1.235.65', 'admin', 'Update Task', '2020-12-17 10:44:36'),
(76, '182.1.235.65', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - uyyy', '2020-12-17 10:45:08'),
(77, '182.1.235.65', 'admin', 'Hapus Keputusan uyyy', '2020-12-17 10:47:35'),
(78, '182.1.235.65', 'admin', 'Tambah Keputusan hhg', '2020-12-17 10:48:07'),
(79, '182.1.235.65', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - hhg', '2020-12-17 10:48:12'),
(80, '182.1.235.65', 'admin', 'Update Task', '2020-12-17 10:48:23'),
(81, '182.1.235.65', 'admin', 'Update Task', '2020-12-17 10:48:36'),
(82, '182.1.235.65', 'admin', 'Hapus Keputusan hhg', '2020-12-17 10:53:22'),
(83, '182.1.235.65', 'admin', 'Tambah Keputusan dvfv', '2020-12-17 10:53:30'),
(84, '182.1.235.65', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - dvfv', '2020-12-17 10:53:35'),
(85, '182.1.235.65', 'admin', 'Update Task', '2020-12-17 10:53:45'),
(86, '182.1.235.65', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - dvfv', '2020-12-17 10:54:36'),
(87, '182.1.235.65', 'admin', 'Hapus Keputusan dvfv', '2020-12-17 10:54:40'),
(88, '182.1.235.65', 'admin', 'Tambah Keputusan vdvd', '2020-12-17 10:54:53'),
(89, '182.1.235.65', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - vdvd', '2020-12-17 10:54:58'),
(90, '182.1.235.65', 'admin', 'Update Task', '2020-12-17 10:55:10'),
(91, '125.165.207.144', 'admin', 'Hapus Keputusan vdvd', '2020-12-22 18:22:38'),
(92, '125.165.207.144', 'admin', 'Tambah Keputusan Keputusan Rakor 1', '2020-12-22 18:24:33'),
(93, '125.165.207.144', 'admin', 'Tambah Keputusan Keputusan Rakor 2', '2020-12-22 18:25:11'),
(94, '125.165.207.144', 'admin', 'Tambah Keputusan Keputusan Rakor 3', '2020-12-22 18:25:31'),
(95, '125.165.207.144', 'admin', 'Tambah Keputusan Keputusan Rakor 4', '2020-12-22 18:26:00'),
(96, '125.165.207.144', 'admin', 'Update Keputusan Keputusan Rakor 1', '2020-12-22 18:26:21'),
(97, '210.210.130.10', 'admin', 'Lihat Detail Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-06 10:43:05'),
(98, '125.162.99.122', 'admin', 'Logout', '2021-01-11 13:01:31'),
(99, '125.162.99.122', 'admin', 'Login', '2021-01-11 13:07:08'),
(100, '125.162.99.122', 'admin', 'Lihat Detail Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 13:16:43'),
(101, '125.162.99.122', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 13:16:58'),
(102, '125.162.99.122', 'admin', 'Lihat Detail Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 14:04:09'),
(103, '125.162.99.122', 'admin', 'Lihat Detail Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 14:32:33'),
(104, '125.162.99.122', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 14:44:00'),
(105, '125.162.99.122', 'admin', 'Update Task', '2021-01-11 14:53:21'),
(106, '125.162.99.122', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 15:02:52'),
(107, '125.162.99.122', 'admin', 'Send to Approval - 22', '2021-01-11 15:02:58'),
(108, '125.162.99.122', 'admin', 'Lihat Detail Approval Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 15:03:06'),
(109, '125.162.99.122', 'admin', 'Approve Task - 22', '2021-01-11 15:04:08'),
(110, '125.162.99.122', 'admin', 'Lihat Detail Verifikasi Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 15:04:15'),
(111, '125.162.99.122', 'admin', 'Lihat Detail Verifikasi Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 15:09:21'),
(112, '125.162.99.122', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan Rakor 3', '2021-01-11 15:12:38'),
(113, '125.162.99.122', 'admin', 'Update Task', '2021-01-11 15:12:43'),
(114, '125.162.99.122', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-11 15:12:46'),
(115, '125.162.99.122', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan Rakor 3', '2021-01-11 15:12:49'),
(116, '125.162.99.122', 'admin', 'Send to Approval - 21', '2021-01-11 15:12:54'),
(117, '125.162.99.122', 'admin', 'Lihat Detail Approval Rapat SEMESTER 2 (2019) - Keputusan Rakor 3', '2021-01-11 15:13:00'),
(118, '125.162.99.122', 'admin', 'Logout', '2021-01-11 23:45:15'),
(119, '125.162.99.122', 'admin', 'Login - gagal', '2021-01-14 17:31:05'),
(120, '125.162.99.122', 'admin', 'Login', '2021-01-14 17:31:13'),
(121, '125.162.99.122', 'admin', 'Logout', '2021-01-14 17:33:54'),
(122, '192.168.1.4', 'admin', 'Login', '2021-01-14 17:45:33'),
(123, '192.168.1.4', 'admin', 'Tambah Keputusan Test', '2021-01-14 20:38:56'),
(124, '192.168.1.4', 'admin', 'Lihat Detail Rapat null - Test', '2021-01-14 20:38:59'),
(125, '192.168.1.4', 'admin', 'Tambah Keputusan Tes', '2021-01-14 21:33:28'),
(126, '192.168.1.4', 'admin', 'Hapus Keputusan Tes', '2021-01-14 21:33:33'),
(127, '192.168.1.4', 'admin', 'Update Keputusan Rapim 1 2019', '2021-01-14 21:38:22'),
(128, '192.168.1.4', 'admin', 'Lihat Detail Rapat SEMESTER 2 (2019) - Keputusan Rakor 4', '2021-01-14 23:52:33'),
(129, '192.168.1.4', 'admin', 'Lihat Detail Rapat null - Rapim 1 2019', '2021-01-14 23:52:54'),
(130, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan Rakor 1 2019', '2021-01-14 23:55:53'),
(131, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 2 Rakor 2019', '2021-01-14 23:56:41'),
(132, '192.168.1.4', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - Keputusan Rakor 1 2019', '2021-01-14 23:57:10'),
(133, '192.168.1.4', 'admin', 'Update Keputusan Keputusan 1 Rakor 2019', '2021-01-14 23:57:27'),
(134, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 3 Rakor 2019', '2021-01-14 23:57:49'),
(135, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 4 Rakor 2019', '2021-01-14 23:58:07'),
(136, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 5 Rakor 2019', '2021-01-14 23:58:32'),
(137, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 6 Rakor 2019', '2021-01-14 23:59:03'),
(138, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 7 Rakor 2019', '2021-01-15 00:00:09'),
(139, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 8 Rakor 2019', '2021-01-15 00:01:01'),
(140, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 9 Rakor 2019', '2021-01-15 00:01:49'),
(141, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 10 Rakor 2019', '2021-01-15 00:02:47'),
(142, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1 Rakor 2020', '2021-01-15 00:03:07'),
(143, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 2 Rakor 2020', '2021-01-15 00:03:28'),
(144, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 3 Rakor 2020', '2021-01-15 00:03:48'),
(145, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 4 Rakor 2020', '2021-01-15 00:04:14'),
(146, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 5 Rakor 2020', '2021-01-15 00:04:35'),
(147, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1 Rapim 2019', '2021-01-15 00:05:00'),
(148, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 2 Rakor 2019', '2021-01-15 00:05:20'),
(149, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1 Rapim 2020', '2021-01-15 00:05:38'),
(150, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1 ENJ', '2021-01-15 00:06:07'),
(151, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan PRO', '2021-01-15 00:06:26'),
(152, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1 EAM ', '2021-01-15 00:07:07'),
(153, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 2 EAM', '2021-01-15 00:07:24'),
(154, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1 KPKU', '2021-01-15 00:07:40'),
(155, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1 CFB ', '2021-01-15 00:07:55'),
(156, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1', '2021-01-15 00:10:00'),
(157, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1', '2021-01-15 00:10:28'),
(158, '192.168.1.4', 'admin', 'Tambah Keputusan Keputusan 1', '2021-01-15 00:11:03'),
(159, '192.168.1.4', 'admin', 'Download Chart - PROGRAM KERJA KI STAFF AHLI_1610644288418', '2021-01-15 00:11:28'),
(160, '192.168.1.4', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - Keputusan 1 Rakor 2019', '2021-01-15 00:12:15'),
(161, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Keputusan 1 Rakor 2019', '2021-01-15 00:12:32'),
(162, '192.168.1.4', 'admin', 'Update Task', '2021-01-15 00:13:01'),
(163, '192.168.1.4', 'admin', 'Send to Approval - 1', '2021-01-15 00:13:41'),
(164, '192.168.1.4', 'admin', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Keputusan 1 Rakor 2019', '2021-01-15 00:13:59'),
(165, '192.168.1.4', 'admin', 'Lihat Detail Rapat SEMESTER I (2019) - Keputusan 6 Rakor 2019', '2021-01-15 00:14:33'),
(166, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan 9 Rakor 2019', '2021-01-15 00:14:40'),
(167, '192.168.1.4', 'admin', 'Update Task', '2021-01-15 00:14:48'),
(168, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan 5 Rakor 2019', '2021-01-15 00:14:57'),
(169, '192.168.1.4', 'admin', 'Update Task', '2021-01-15 00:15:11'),
(170, '192.168.1.4', 'admin', 'Send to Approval - 5', '2021-01-15 00:15:27'),
(171, '192.168.1.4', 'admin', 'Lihat Detail Approval Rapat SEMESTER 2 (2019) - Keputusan 5 Rakor 2019', '2021-01-15 00:15:31'),
(172, '192.168.1.4', 'admin', 'Revised Task - 5', '2021-01-15 00:15:35'),
(173, '192.168.1.4', 'admin', 'Lihat Detail Rapat SEMESTER 2 (2019) - Keputusan 4 Rakor 2019', '2021-01-15 00:15:58'),
(174, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Keputusan 10 Rakor 2019', '2021-01-15 00:16:09'),
(175, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Keputusan 10 Rakor 2019', '2021-01-15 00:16:09'),
(176, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER I (2019) - Keputusan 10 Rakor 2019', '2021-01-15 00:16:11'),
(177, '192.168.1.4', 'admin', 'Update Task', '2021-01-15 00:16:19'),
(178, '192.168.1.4', 'admin', 'Update Task', '2021-01-15 00:16:29'),
(179, '192.168.1.4', 'admin', 'Send to Approval - 10', '2021-01-15 00:16:32'),
(180, '192.168.1.4', 'admin', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Keputusan 10 Rakor 2019', '2021-01-15 00:16:39'),
(181, '192.168.1.4', 'admin', 'Revised Task - 10', '2021-01-15 00:16:51'),
(182, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan 8 Rakor 2019', '2021-01-15 00:17:02'),
(183, '192.168.1.4', 'admin', 'Update Task', '2021-01-15 00:17:12'),
(184, '192.168.1.4', 'admin', 'Send to Approval - 8', '2021-01-15 00:17:25'),
(185, '192.168.1.4', 'admin', 'Lihat Detail Approval Rapat SEMESTER I (2019) - Keputusan 1 Rakor 2019', '2021-01-15 00:17:31'),
(186, '192.168.1.4', 'admin', 'Approve Task - 1', '2021-01-15 00:17:35'),
(187, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan 3 Rakor 2019', '2021-01-15 00:18:23'),
(188, '192.168.1.4', 'admin', 'Update Task', '2021-01-15 00:18:31'),
(189, '192.168.1.4', 'admin', 'Send to Approval - 3', '2021-01-15 00:18:35'),
(190, '192.168.1.4', 'admin', 'Lihat Detail Approval Rapat SEMESTER 2 (2019) - Keputusan 8 Rakor 2019', '2021-01-15 00:18:39'),
(191, '192.168.1.4', 'admin', 'Approve Task - 8', '2021-01-15 00:18:43'),
(192, '192.168.1.4', 'admin', 'Lihat Detail Verifikasi Rapat SEMESTER 2 (2019) - Keputusan 8 Rakor 2019', '2021-01-15 00:18:48'),
(193, '192.168.1.4', 'admin', 'Lihat Detail Verifikasi Rapat SEMESTER I (2019) - Keputusan 1 Rakor 2019', '2021-01-15 00:18:52'),
(194, '192.168.1.4', 'admin', 'Verified Task - 1', '2021-01-15 00:18:56'),
(195, '192.168.1.4', 'admin', 'Lihat Detail Task Rapat SEMESTER 2 (2019) - Keputusan 8 Rakor 2019', '2021-01-15 00:19:06'),
(196, '192.168.1.4', 'admin', 'Update Keputusan Keputusan 4 Rakor 2019', '2021-01-15 00:20:59'),
(197, '192.168.1.3', 'admin', 'Tambah Keputusan Keputusan 6 Rakor 2020', '2021-01-15 09:49:52'),
(198, '192.168.1.3', 'admin', 'Update Keputusan Keputusan 6 Rakor 2020', '2021-01-15 09:50:11'),
(199, '192.168.1.3', 'admin', 'Update Keputusan Keputusan 4 Rakor 2020', '2021-01-15 09:50:32');

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
(1, 3, 4, 'RKR2019.1', '2021-01-14', '2021-02-28', 'Keputusan 1 Rakor 2019', 'Test 123', 'admin', '2021-01-15', 'admin', '2021-01-15', 'admin', '2021-01-15', 'Tidak ada komentar', 5, '2021-01-14 11:57:26', 'Monitoring_1_RKR2019.1.pdf'),
(2, 3, 4, 'RKR2019.2', '2021-01-14', '2021-01-14', 'Keputusan 2 Rakor 2019', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(3, 3, 13, 'RKR2019.3', '2021-01-14', '2021-01-13', 'Keputusan 3 Rakor 2019', 'test', 'admin', '2021-01-15', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 3, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(4, 3, 13, 'RKR2019.4', '2021-01-14', '2021-01-01', 'Keputusan 4 Rakor 2019', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 2, '2021-01-15 12:20:59', 'Tidak ada lampiran'),
(5, 3, 13, 'RKR2019.5', '2021-01-14', '2021-01-08', 'Keputusan 5 Rakor 2019', 'Test', 'admin', '2021-01-15', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 0, '0000-00-00 00:00:00', 'Monitoring_5_RKR2019.5.pdf'),
(6, 3, 4, 'RKR2019.6', '2021-01-14', '2021-02-28', 'Keputusan 6 Rakor 2019', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(7, 3, 4, 'RKR2019.7', '2021-01-15', '2021-02-28', 'Keputusan 7 Rakor 2019', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(8, 3, 13, 'RKR2019.8', '2021-01-15', '2021-02-28', 'Keputusan 8 Rakor 2019', 'test', 'admin', '2021-01-15', 'admin', '2021-01-15', 'admin', '0000-00-00', 'Tidak ada komentar', 4, '0000-00-00 00:00:00', 'Monitoring_8_RKR2019.8.pdf'),
(9, 3, 13, 'RKR2019.9', '2021-01-15', '2021-01-31', 'Keputusan 9 Rakor 2019', 'Test', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 2, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(10, 3, 4, 'RKR2019.10', '2021-01-15', '2021-01-31', 'Keputusan 10 Rakor 2019', 'test', 'admin', '2021-01-15', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada file lampiran', 0, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(11, 4, 6, 'RKR2020.1', '2021-01-15', '2021-02-16', 'Keputusan 1 Rakor 2020', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(12, 4, 17, 'RKR2020.2', '2021-01-15', '2021-01-01', 'Keputusan 2 Rakor 2020', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(13, 4, 6, 'RKR2020.3', '2021-01-15', '2021-01-16', 'Keputusan 3 Rakor 2020', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(14, 4, 17, 'RKR2020.4', '2021-01-15', '2021-02-19', 'Keputusan 4 Rakor 2020', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 2, '2021-01-15 09:50:31', 'Tidak ada lampiran'),
(15, 4, 6, 'RKR2020.5', '2021-01-15', '2021-02-23', 'Keputusan 5 Rakor 2020', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(16, 51, 0, 'RPM2019.1', '2021-01-15', '2021-01-15', 'Keputusan 1 Rapim 2019', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(17, 51, 0, 'RPM2019.2', '2021-01-15', '2021-02-22', 'Keputusan 2 Rakor 2019', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(18, 52, 0, 'RPM2020.1', '2021-01-15', '2021-01-31', 'Keputusan 1 Rapim 2020', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(19, 29, 0, 'PROKI ENJINIRING.1', '2021-01-15', '2021-01-15', 'Keputusan 1 ENJ', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(20, 30, 0, 'PROKI PRODUKSI.1', '2021-01-15', '2021-01-31', 'Keputusan PRO', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(21, 9, 7, 'OFIEAM.1', '2021-01-15', '2021-01-27', 'Keputusan 1 EAM ', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(22, 9, 7, 'OFIEAM.2', '2021-01-15', '2021-01-15', 'Keputusan 2 EAM', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(23, 10, 8, 'OFIKPKU.1', '2021-01-15', '2021-01-30', 'Keputusan 1 KPKU', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(24, 49, 11, 'OFICFB.1', '2021-01-15', '2021-01-31', 'Keputusan 1 CFB ', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(25, 48, 0, 'RAKOR REGSUM.1', '2021-01-15', '2021-01-15', 'Keputusan 1', 'Belum ada rencana aksi', 'user1', '0000-00-00', 'user6', '0000-00-00', 'user5', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(26, 47, 0, 'FORUM PEMBANGKITAN.1', '2021-01-15', '2021-01-27', 'Keputusan 1', 'Belum ada rencana aksi', 'user4', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(27, 46, 0, 'PROKI STAFF AHLI.1', '2021-01-15', '2021-01-28', 'Keputusan 1', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 1, '0000-00-00 00:00:00', 'Tidak ada lampiran'),
(28, 4, 17, 'RKR2020.6', '2021-01-15', '2021-02-24', 'Keputusan 6 Rakor 2020', 'Belum ada rencana aksi', 'admin', '0000-00-00', 'admin', '0000-00-00', 'admin', '0000-00-00', 'Tidak ada komentar', 0, '2021-01-15 09:50:10', 'Tidak ada lampiran');

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
(1, 'admin', 'a5e8eba644fdfcfdb4b9000693550c40', 'Admin', 'admin@gmail.com', 'off', '', '2021-01-15 09:51:09', 1),
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
  MODIFY `id_log` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=200;

--
-- AUTO_INCREMENT for table `tblkit_monitoring`
--
ALTER TABLE `tblkit_monitoring`
  MODIFY `ID_MON` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

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
