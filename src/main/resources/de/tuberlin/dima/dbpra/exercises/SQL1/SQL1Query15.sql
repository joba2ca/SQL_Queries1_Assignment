-- Queryergebnis:
-- Geben sie alle Kundennamen zusammen mit ihren Ländern aus. Ordnen sie das Ergebnis nach dem Kundennamen alphabetisch.
-- <p/>
-- Ergebnisschema:
-- [Kunde↑ | Land]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 15
SELECT kunde.NAME as KUNDE, land.NAME as LAND FROM KUNDE, LAND
WHERE kunde.LAND = land.LAND_ID
ORDER BY KUNDE ASC