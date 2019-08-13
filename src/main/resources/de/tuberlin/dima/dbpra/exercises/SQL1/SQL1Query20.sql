-- Queryergebnis:
-- Geben Sie die Anzahl der Bestellungen und die Kundennamen aus, die mehr als 30 Bestellungen getätigt haben,
-- absteigend sortiert nach Anzahl der Bestellungen.
-- <p/>
-- Ergebnisschema:
-- [Name | Bestell_Anzahl (↓)]
-- <p/>
-- Punkte:
-- 4.0
--
-- @return SQL Query für Aufgabe 20
SELECT KUNDE.NAME, count(*) as Bestell_Anzahl FROM BESTELLUNG, KUNDE
WHERE BESTELLUNG.KUNDE = KUNDE.KUNDEN_NR
GROUP BY KUNDE.NAME
HAVING count(*) > 30
ORDER BY Bestell_Anzahl DESC