DROP TABLE IF EXISTS client CASCADE;
CREATE TABLE client(
idClient 	numeric(10) NOT NULL ,
prenom		varchar(64) NOT NULL,
nom		varchar(64) NOT NULL,
age		numeric(3) NOT NULL ,
PRIMARY KEY (idClient)
);


DROP TABLE IF EXISTS commodite CASCADE;
CREATE TABLE commodite(
idCommodite	numeric(10) NOT NULL,
description	varchar(20) NOT NULL,
surplus_prix	varchar(20) NOT NULL,
PRIMARY KEY (idCommodite)
);
DROP TABLE IF EXISTS chambre CASCADE;
CREATE TABLE chambre(
    idChambre	numeric(10) NOT NULL ,
    type_lit 	varchar(20) NOT NULL,
    nom_chambre	varchar(20) NOT NULL,
    prix_base 	float(2) NOT NULL,
    idCommodite numeric(10) NULL,
    PRIMARY KEY (idChambre),
    FOREIGN KEY (idCommodite) REFERENCES commodite
);
DROP TABLE IF EXISTS reservation CASCADE;
CREATE TABLE reservation(
idChambre numeric(10) NOT NULL,
idClient numeric(10) NOT NULL,
date_debut 	DATE NOT NULL,
date_fin	DATE NOT NULL,
FOREIGN KEY (idChambre) REFERENCES chambre,
FOREIGN KEY (idClient) REFERENCES client
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