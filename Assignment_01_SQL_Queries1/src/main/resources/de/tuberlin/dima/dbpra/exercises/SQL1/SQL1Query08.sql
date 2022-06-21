-- Queryergebnis:
-- Geben Sie die Anzahl und die Summe der Kontostände (Bilanz) aller Kunden aus der Branche AUTOMOBILE aus.
-- <p/>
-- Ergebnisschema:
-- [Anzahl|Bilanz]
-- <p/>
-- Punkte:
-- 1.0
--
-- @return SQL Query für Aufgabe 8
SELECT COUNT(*) as Anzahl, SUM(KONTOSTAND) as Bilanz
FROM KUNDE
WHERE BRANCHE = 'AUTOMOBILE'