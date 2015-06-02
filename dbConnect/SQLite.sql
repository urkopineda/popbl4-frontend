CREATE TABLE IF NOT EXISTS Playlist
(
	PlaylistID INTEGER PRIMARY KEY AUTOINCREMENT,
	Nombre TEXT NOT NULL,
	Descripcion TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Autor
(
	AutorID INTEGER PRIMARY KEY AUTOINCREMENT,
	Nombre TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Album
(
	AlbumID INTEGER PRIMARY KEY AUTOINCREMENT,
	AutorID INT,
	Nombre TEXT NOT NULL,
	CONSTRAINT fk_album_autor FOREIGN KEY(AutorID) REFERENCES Autor (AutorID)
);

CREATE TABLE IF NOT EXISTS Cancion
(
	CancionID INTEGER PRIMARY KEY AUTOINCREMENT,
	AlbumID INT DEFAULT 0,
	AutorID INT DEFAULT 0,
	Duracion INT NOT NULL,
	Nombre TEXT NOT NULL,
	BPM INT,
	Ruta TEXT,
	CONSTRAINT fk_cancion_autor FOREIGN KEY(AutorID) REFERENCES Autor (AutorID),
	CONSTRAINT fk_cancion_album FOREIGN KEY(AlbumID) REFERENCES Album (AlbumID)
);

CREATE TABLE IF NOT EXISTS CancionPlaylist
(
	PlaylistID INT,
	CancionID INT,
	CONSTRAINT pk_cancionplaylist PRIMARY KEY (PlaylistID, CancionID),
	CONSTRAINT fk_cancionplaylist_cancion FOREIGN KEY(CancionID) REFERENCES Cancion (CancionID),
	CONSTRAINT fk_cancionplaylist_playlist FOREIGN KEY(PlaylistID) REFERENCES Playlist (PlaylistID)
);

CREATE TABLE IF NOT EXISTS Intervalo
(
	IntervaloID INTEGER PRIMARY KEY AUTOINCREMENT,
	CancionID INT,
	FechaHoraComienzo TEXT NOT NULL,
	PeriodoMuestra INT NOT NULL,
	CONSTRAINT fk_intervalo_cancion FOREIGN KEY(CancionID) REFERENCES Cancion (CancionID)
);

CREATE TABLE IF NOT EXISTS Muestra
(
	MuestraID INTEGER PRIMARY KEY AUTOINCREMENT,
	IntervaloID INT,
	Valor INT NOT NULL,
	CONSTRAINT fk_muestra_intervalo FOREIGN KEY(IntervaloID) REFERENCES Intervalo (IntervaloID)
);

CREATE VIEW IF NOT EXISTS vCanciones AS
	SELECT 		C.CancionID,
				C.Nombre,
				C.Ruta,
				C.Duracion,
				Al.Nombre AS Album,
				Al.AlbumID,
				Au.Nombre AS Autor,
				Au.AutorID
	FROM 		Cancion C
	LEFT OUTER JOIN Autor Au
	ON			Au.AutorID=C.AutorID
	LEFT OUTER JOIN Album Al
	ON 			Al.AlbumID=C.AlbumID