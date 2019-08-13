-- Queryergebnis:
-- Geben sie die minimale Steuer aus, die auf Artikel mit Preis kleiner 2000 erhoben wurde.
-- <p/>
-- Ergebnisschema:
-- [MinSteuer]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 16
SELECT min(Steuer) as MinSteuer FROM ARTIKEL, BESTELLPOSTEN
WHERE BESTELLPOSTEN.ARTIKEL = ARTIKEL.ARTIKEL_NR
    AND ARTIKEL.PREIS < 2000