-- Queryergebnis:
-- Geben sie für jede Bearbeitungsdauer (Differenz in Tagen zwischen Empfangs- und Versanddatum) die Anzahl der Bestellposten aus.
-- <p/>
-- Ergebnisschema:
-- [Bearbeitungsdauer | Bestellposten (↑)]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 12
SELECT (days(EMPFANGSDATUM) - days(VERSANDDATUM)) AS bearbeitungsdauer, count(*) AS bestellposten FROM BESTELLPOSTEN
GROUP BY (days(EMPFANGSDATUM) - days(VERSANDDATUM))
ORDER BY BESTELLPOSTEN ASC