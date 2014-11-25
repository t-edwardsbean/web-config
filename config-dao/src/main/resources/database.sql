DROP DATABASE  IF EXISTS web_config;
CREATE DATABASE web_config;
USE web_config;
CREATE TABLE `method_param` (
  `method_param_id` int(10) NOT NULL AUTO_INCREMENT,
  `param_class` varchar(50),
  `param_value` varchar(50) NOT NULL,
  `method_id` int(10) NOT NULL,
  PRIMARY KEY (`method_param_id`)
);

CREATE TABLE `config` (
  `config_id` int(10) NOT NULL AUTO_INCREMENT,
  `service_id` varchar(50) NOT NULL,
  `service_name` varchar(50) NOT NULL,
  `description` varchar(100) NULL,
  `group_name` varchar(50) NOT NULL,
  `request_delay` int UNSIGNED NOT NULL,
  `fail_delay` int UNSIGNED NOT NULL,
  `server_num` int UNSIGNED NOT NULL,
  `thread_num` int UNSIGNED NOT NULL,
  PRIMARY KEY (`config_id`)
);

CREATE TABLE `method` (
  `method_id` int(10) NOT NULL AUTO_INCREMENT,
  `method_name` varchar(50) NOT NULL,
  `config_id` int NOT NULL,
  PRIMARY KEY (`method_id`)
);


ALTER TABLE `method` ADD FOREIGN KEY (`config_id`) REFERENCES `config` (`config_id`) ON DELETE CASCADE;
ALTER TABLE `method_param` ADD FOREIGN KEY (`method_id`) REFERENCES `method` (`method_id`) ON DELETE CASCADE;

