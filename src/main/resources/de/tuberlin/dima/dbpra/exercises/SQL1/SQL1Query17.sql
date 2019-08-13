-- Queryergebnis:
-- Geben Sie aus, wie viele Kunden aus jeder Region kommen, aufsteigend sortiert nach Namen der Region.
-- <p/>
-- Ergebnisschema:
-- [Region (↑) | Anzahl]
-- <p/>
-- Punkte:
-- 3.0
--
-- @return SQL Query für Aufgabe 17
SELECT REGION.NAME as Region, count(Kunde.Name) as Anzahl FROM KUNDE, LAND, REGION
WHERE Kunde.LAND = LAND.LAND_ID
  AND LAND.REGION = REGION.REGION_ID
GROUP BY REGION.NAME
ORDER BY REGION ASC