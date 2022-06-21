-- Queryergebnis:
-- Geben sie für alle Branchen den jeweils geringsten und größten Kontostand aller Kunden aus. Ordnen Sie nach Branchen alphabetisch.
-- <p/>
-- Ergebnisschema:
-- [Branche (↑) | MinKonto | MaxKonto]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 13
SELECT BRANCHE, min(KONTOSTAND) as MinKonto, max(KONTOSTAND) as MaxKonto FROM KUNDE
GROUP BY BRANCHE
ORDER BY BRANCHE ASC