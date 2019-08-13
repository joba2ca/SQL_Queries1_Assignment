-- Queryergebnis:
-- Geben sie die Namen aller Artikel aus, deren Preis größer als 20 ist und deren Typ „POLISHED“ enthält.
-- Ordnen sie die Namen alphabetisch absteigend.
-- <p/>
-- Ergebnisschema:
-- [Name (↓)]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 4
SELECT NAME FROM ARTIKEL WHERE PREIS > 20 AND TYP LIKE '%POLISHED%'
ORDER BY NAME DESC