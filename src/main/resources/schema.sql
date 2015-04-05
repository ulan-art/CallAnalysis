CREATE TABLE calls (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  lat float,
  lon float,
  notes varchar(255) DEFAULT NULL,
  phone varchar(15) DEFAULT NULL,
  groupn int,
  PRIMARY KEY (id)
);

CREATE TABLE district (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  lat float,
  lon float,
  name varchar(32) DEFAULT NULL,
  notes varchar(255) DEFAULT NULL,
  weight float,
  marker_type varchar(24) DEFAULT NULL,
  groupn int,
  PRIMARY KEY (id)
);
