-- 4.2a / 4.2b)
-- Tabelle für Genre
CREATE TABLE Genre (
    GenreID BIGINT PRIMARY KEY,
    Genre VARCHAR(255) NOT NULL,
    CONSTRAINT unique_genre UNIQUE (Genre)
);

-- Tabelle für Person
CREATE TABLE Person (
    PersonID BIGSERIAL PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Sex CHAR(1),
    CONSTRAINT unique_name UNIQUE (Name)
);

-- Tabelle für Movie
CREATE TABLE Movie (
    movieid BIGSERIAL PRIMARY KEY, -- BIGSERIAL erzeugt automatisch IDs, BIGINT nicht.
    title VARCHAR(255) NOT NULL,
    year INT,
    type CHAR(1)
);

-- Zwischentabelle für die N:M-Beziehung zwischen Movie und Genre
CREATE TABLE MovieGenre (
    MovieID BIGINT,
    GenreID BIGINT,
    PRIMARY KEY (MovieID, GenreID),
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID),
    FOREIGN KEY (GenreID) REFERENCES Genre(GenreID)
);

-- Tabelle für MovieCharacter
CREATE TABLE MovieCharacter (
    MovCharID BIGSERIAL PRIMARY KEY,
    Character VARCHAR(255) NOT NULL,
    Alias VARCHAR(255),
    Position INT,
    MovieID BIGINT,
    PersonID BIGINT,
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID),
    FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);


-- 4.2c)
INSERT INTO Genre (GenreID, Genre)
VALUES 
    (1, 'Action'), 
    (2, 'Comedy'), 
    (3, 'Drama'), 
    (4, 'Horror');

   
INSERT INTO Person (PersonID, Name, Sex)
VALUES 
    (1, 'Fatjon Popaj', 'M'), 
    (2, 'Jennifer Müller', 'F'), 
    (3, 'Ege Coskun', 'M'),
    (4, 'Alexis Jackson', 'F');
    
INSERT INTO Movie (MovieID, Title, Year, Type)
VALUES 
    (1001, 'Star Wars', 1977, 'F'),
    (1002, 'Spongebob Schwammkopf', 2020, 'T'),
    (1003, 'Minecraft', 2009, 'G'),
    (1004, 'Avengers', 2022, 'C');

   
INSERT INTO MovieGenre (MovieID, GenreID)
VALUES 
    (1001, 3),  -- 'Star Wars' ist ein Drama
    (1002, 2),  -- 'Spongebob Schwammkopf' ist eine Komödie
    (1003, 1),  -- 'Minecraft' ist ein Spiel
    (1004, 2);  -- 'Avengers' ist ein Action-Cinema
    
INSERT INTO MovieCharacter (MovCharID, Character, Alias, Position, MovieID, PersonID)
VALUES 
    (2001, 'Luke Skywalker', 'Jedi-Ritter', 1, 1001, 1),  -- Star Wars - Fatjon Popaj    
    (2002, 'Spongebob Schwammkopf', NULL, 1, 1002, 2),        -- Spongebob Schwammkopf - Jennifer Müller
    (2003, 'Stella', 'ST', 1, 1003, 4),  -- Minecraft - Alexis Jackson
    (2004, 'Tony Stark', 'Iron-Man', 1, 1004, 3);            -- Avengers - Ege Coskun

-- 4.2d)
DROP TABLE IF EXISTS MovieCharacter CASCADE;
DROP TABLE IF EXISTS MovieGenre CASCADE;
DROP TABLE IF EXISTS Movie CASCADE;
DROP TABLE IF EXISTS Genre CASCADE;
DROP TABLE IF EXISTS Person CASCADE;

--4.2e)
/* 
1. Nicht-Null-Bedingungen:

- Movie.Title sollte nicht NULL sein, da jeder Film einen Titel haben muss
- Person.Name sollte nicht NULL sein, da jede Person in der Datenbank einen Namen benötigt
- MovieCharacter.Character sollte nicht NULL sein, um sicherzustellen, dass jede Filmrolle einen Namen hat

2. Referentielle Integrität

- Jeder MovieID-Eintrag in MovieGenre und MovieCharacter muss in Movie existieren
- Jeder GenreID-Eintrag in MovieGenre muss in Genre existieren
- Jeder PersonID-Eintrag in MovieCharacter muss in Person existieren

3. Eindeutige Einträge

- Person.Name und Genre.Genre sollten eindeutig sein, um doppelte Einträge zu vermeiden

4. Logische Bedingung

- Movie.Year sollte ein gültiges Jahr sein und beispielsweise >=  als 1895 sein
- Movie.Type sollte nur gültige Werte enthalten, z.B. F für Film, C für Cinema usw.

5. Fremdschlüssel mit ON DELETE CASCADE

- Wenn ein Eintrag in Movie oder Person gelöscht wird, sollten alle zugehörigen Einträge
  in MovieGenre und MovieCharacter automatisch gelöscht werden, um verwaiste Einträge zu vermeiden
**/

//aufrufen test
SELECT * FROM movie;
