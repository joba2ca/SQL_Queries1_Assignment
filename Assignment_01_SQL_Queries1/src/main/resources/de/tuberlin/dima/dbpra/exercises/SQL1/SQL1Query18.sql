-- Queryergebnis:
-- Geben sie die Lieferantennummern aller Lieferanten aus, die entweder aus Deutschland kommen
-- oder einen Artikel teurer als 990 liefern. Jede Lieferantennummer darf nur einmal auftauchen.
-- Sie brauchen keine Sortierung zu berücksichtigen.
-- <p/>
-- Ergebnisschema:
-- [Lieferanten_Nr]
-- <p/>
-- Punkte:
-- 6.0
--
-- @return SQL Query für Aufgabe 18
SELECT DISTINCT LIEFERANT.LIEFERANTEN_NR FROM LIEFERANT, LIEFERT, LAND
WHERE (Lieferant.LIEFERANTEN_NR = LIEFERT.LIEFERANT
  AND LIEFERT.LIEFERPREIS > 990)
   OR (LIEFERANT.LAND = LAND.LAND_ID
  AND LAND.NAME = 'GERMANY')