DROP TABLE IF EXISTS clients CASCADE;
CREATE TABLE clients(
idClient 	numeric(10) NOT NULL ,
prenom		varchar(64) NOT NULL,
nom		varchar(64) NOT NULL,
age		numeric(3) NOT NULL ,
PRIMARY KEY (idClient)
);


DROP TABLE IF EXISTS commodites CASCADE;
CREATE TABLE commodites(
idCommodite	numeric(10) NOT NULL,
description	varchar(20) NOT NULL,
surplus_prix	varchar(20) NOT NULL,
PRIMARY KEY (idCommodite)
);
DROP TABLE IF EXISTS chambres CASCADE;
CREATE TABLE chambres(
    idChambre	numeric(10) NOT NULL ,
    type_lit 	varchar(20) NOT NULL,
    nom_chambre	varchar(20) NOT NULL,
    prix_base 	float(2) NOT NULL,
    idCommodite numeric(10) NULL,
    PRIMARY KEY (idChambre),
    FOREIGN KEY (idCommodite) REFERENCES commodites
);
DROP TABLE IF EXISTS reservation CASCADE;
CREATE TABLE reservation(
idChambre numeric(10) NOT NULL,
idClient numeric(10) NOT NULL,
date_debut 	DATE NOT NULL,
date_fin	DATE NOT NULL,
FOREIGN KEY (idChambre) REFERENCES chambres,
FOREIGN KEY (idClient) REFERENCES clients
);

DROP TABLE IF EXISTS Service CASCADE;
CREATE TABLE Service
(
    idCommodite INTEGER NOT NULL,
    idChambre INTEGER NOT NULL,
    FOREIGN KEY (idCommodite)
        REFERENCES Commodite,
    FOREIGN KEY (idChambre)
        REFERENCES Chambre
);