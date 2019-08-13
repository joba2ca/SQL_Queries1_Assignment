-- Queryergebnis:
-- Geben Sie die Bestellnummer, das Datum und den Status von Bestellungen aus, deren Status "F" ist,
-- die in einem Oktober vor 1994 aufgegeben wurden und entweder eine Bestellnummer kleiner als 10000
-- oder größer als 500000 besitzen. Ordnen Sie nach Bestellnummern absteigend.
-- <p/>
-- Ergebnisschema:
-- [Bestell_Nr (↓) | Bestelldatum | Status]
-- <p/>
-- Punkte:
-- 3.0
--
-- @return SQL Query für Aufgabe 5
SELECT BESTELL_NR, BESTELLDATUM, STATUS FROM BESTELLUNG
WHERE Status = 'F'
  AND BESTELLDATUM LIKE '%-10-%'
  AND BESTELLDATUM < '1994-01-01'
  AND (BESTELL_NR > 500000 OR BESTELL_NR < 10000)
ORDER BY BESTELL_NR DESC;