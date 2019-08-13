-- Queryergebnis:
-- Finden Sie für jede mögliche Kombination aus Versandart und Retourstatus die maximale Bearbeitungszeit und
-- die mittlere Bearbeitungszeit (zwischen Empfang und Versand des Postens) absteigend sortiert
-- nach durchschnittlicher Bearbeitungszeit.
-- Betrachten sie nur Kombinationen deren Bestellposten einen Gesamt-Mindestumsatz (siehe Q11)
-- von 1000000000 erreichen.
-- <p/>
-- Ergebnisschema:
-- [versandart | retourstatus | max_zeit | durch_zeit↓]
-- <p/>
-- Punkte:
-- 4.0
--
-- @return SQL Query für Aufgabe 14
SELECT VERSANDART,
       RETOURSTATUS,
       max(days(EMPFANGSDATUM) - days(VERSANDDATUM)) as max_zeit,
       avg(days(EMPFANGSDATUM) - days(VERSANDDATUM)) as durch_zeit
FROM BESTELLPOSTEN
GROUP BY VERSANDART, RETOURSTATUS
HAVING sum(Preis/(1+STEUER)) >= 1000000000
ORDER BY durch_zeit DESC