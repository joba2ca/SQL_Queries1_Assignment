-- Queryergebnis:
-- Ermitteln Sie für alle Versandarten die Gesamtanzahl der versendeten
-- Bestellposten, bei denen der Rabatt kleiner als 0.08 ist, sowie den
-- durchschnittlichen Preis dieser Bestellposten. Sortieren Sie absteigend nach
-- der Versandart. Beachten Sie, dass ein Bestellposten u.u. aus mehreren
-- Artikeln bestehlt, die im Feld ANZAHL gespeichert sind.
--
-- <p/>
-- Ergebnisschema:
-- [Versandart ↓ | Summe | Durchschnitt]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 10
SELECT VERSANDART, SUM(ANZAHL) as Summe, AVG(PREIS) as durchschnitt FROM BESTELLPOSTEN
WHERE RABATT < 0.08
GROUP BY VERSANDART
ORDER BY VERSANDART DESC
