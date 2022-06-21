-- Queryergebnis:
-- Geben sie die Namen und Telefonnumern aller Kunden aus, die einen negativen Kontostand haben.
-- Ordnen sie diese alphabetisch.
-- <p/>
-- Ergebnisschema:
-- [Name (↑) | Telefon]
-- <p/>
-- Punkte:
-- 1.0
--
-- @return SQL Query für Aufgabe 3
SELECT NAME, TELEFON FROM KUNDE WHERE KONTOSTAND < 0
ORDER BY NAME ASC