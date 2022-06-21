-- Queryergebnis:
-- Geben sie den durchschnittlichen Preis von Artikeln aus, deren Typ „POLISHED“ enthält.
-- <p/>
-- Ergebnisschema:
-- [durchschnitt]
-- <p/>
-- Punkte:
-- 1.0
--
-- @return SQL Query für Aufgabe 9
SELECT AVG(PREIS) as durchschnitt FROM  ARTIKEL
WHERE TYP LIKE '%POLISHED%'