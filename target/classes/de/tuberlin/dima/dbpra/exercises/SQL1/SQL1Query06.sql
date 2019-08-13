-- Queryergebnis:
-- Geben sie die Bestellposten (Bestellnummer) aus, bei denen die Lieferanten ID 785 ist und der Rabatt
-- mindestens doppelt so groß wie die Steuer ist. Ordnen sie aufsteigend nach Preis.
-- <p/>
-- Ergebnisschema:
-- [Bestell_Nr]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 6
SELECT BESTELL_NR FROM BESTELLPOSTEN
WHERE LIEFERANT = 785
  AND RABATT >= 2*STEUER
ORDER BY PREIS ASC