CREATE TABLE calls (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  lat float,
  lon float,
  notes varchar(255) DEFAULT NULL,
  phone varchar(15) DEFAULT NULL,
  cluster int,
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
  cluster int,
  PRIMARY KEY (id)
);

CREATE TABLE log (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  creation_date DATETIME,
  message VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

-- 26 apr 2015

# ALTER TABLE taxi.district CHANGE groupn cluster INT;
# ALTER TABLE taxi.calls CHANGE groupn cluster INT;