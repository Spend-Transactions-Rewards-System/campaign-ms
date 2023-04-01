CREATE TABLE `custom_category_mcc_list` (
  `custom_category_id` bigint(20) NOT NULL,
  `mcc_list_mcc` int(11) NOT NULL,
  KEY `FK1du32kiayhi2gdgcqcmnk58ix` (`custom_category_id`),
  CONSTRAINT `FK1du32kiayhi2gdgcqcmnk58ix` FOREIGN KEY (`custom_category_id`) REFERENCES `custom_category` (`id`),
  CONSTRAINT `FKgeq4ehp0uyim0b57yidih88gn` FOREIGN KEY (`mcc_list_mcc`) REFERENCES `mcc` (`mcc`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
