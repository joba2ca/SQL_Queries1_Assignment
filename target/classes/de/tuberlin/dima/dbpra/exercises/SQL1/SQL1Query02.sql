-- Queryergebnis:
-- Geben sie alle möglichen Hersteller eines Artikels aus (ohne Duplikate), in alphabetischer Reihenfolge aus.
-- <p/>
-- Ergebnisschema:
-- [Hersteller (↑)]
-- <p/>
-- Punkte:
-- 1.0
--
-- @return SQL Query für Aufgabe 2

SELECT DISTINCT HERSTELLER FROM ARTIKEL
ORDER BY HERSTELLER ASC