DROP TABLE IF EXISTS Imprumuturi;
DROP TABLE IF EXISTS Carti;
DROP TABLE IF EXISTS Sectiuni;
DROP TABLE IF EXISTS Cititori;

CREATE TABLE Sectiuni (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          nume TEXT NOT NULL UNIQUE
);

CREATE TABLE Cititori (
                          id INTEGER PRIMARY KEY,
                          nume TEXT NOT NULL,
                          email TEXT NOT NULL
);

CREATE TABLE Carti (
                       isbn TEXT PRIMARY KEY,
                       titlu TEXT NOT NULL,
                       autor TEXT NOT NULL,
                       disponibila BOOLEAN NOT NULL CHECK (disponibila IN (0, 1)),
                       id_sectiune INTEGER,
                       FOREIGN KEY (id_sectiune) REFERENCES Sectiuni(id) ON DELETE SET NULL
);

CREATE TABLE Imprumuturi (
                             id INTEGER PRIMARY KEY AUTOINCREMENT,
                             id_cititor INTEGER NOT NULL,
                             isbn_carte TEXT NOT NULL,
                             data_imprumut TEXT NOT NULL,
                             FOREIGN KEY (id_cititor) REFERENCES Cititori(id) ON DELETE CASCADE,
                             FOREIGN KEY (isbn_carte) REFERENCES Carti(isbn) ON DELETE CASCADE
);