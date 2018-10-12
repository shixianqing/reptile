CREATE TABLE `medicine_spider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `medicineName` varchar(100) NOT NULL,
  `medicineNameCommon` varchar(100) DEFAULT NULL,
  `productEnterprise` char(100) DEFAULT NULL,
  `medicineStandard` char(100) DEFAULT NULL,
  `convertRatio` varchar(12) DEFAULT NULL,
  `packagingMaterial` varchar(100) DEFAULT NULL,
  `unit` varchar(100) DEFAULT NULL,
  `medicineForm` varchar(50) DEFAULT NULL,
  `selfPaymentRatio` varchar(10) DEFAULT NULL,
  `childRatio` varchar(10) DEFAULT NULL,
  `bearRatio` varchar(10) DEFAULT NULL,
  `injuryRatio` varchar(10) DEFAULT NULL,
  `prescriptionFlag` varchar(12) DEFAULT NULL,
  `cancleFlag` varchar(12) DEFAULT NULL,
  `medicineClassify` varchar(30) DEFAULT NULL,
  `payRange` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5482 DEFAULT CHARSET=utf8;


