-- Queryergebnis:
-- Finden Sie Name und Kontostand von Kunden, die sich in einer Branche mit der Endung „E“ befinden.
-- Ordnen Sie das Ergebnis alphabetisch nach Name.
-- <p/>
-- Ergebnisschema:
-- [Name (↑) | Kontostand]
-- <p/>
-- Punkte:
-- 1.0
--
-- @return SQL Query für Aufgabe 7
SELECT NAME, KONTOSTAND FROM KUNDE
WHERE RTRIM(BRANCHE) LIKE ('%E')
ORDER BY NAME ASC