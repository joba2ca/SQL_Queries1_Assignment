-- Queryergebnis:
-- Finden sie alle Kunden (ohne Dublikate), dessen Gesamtpreis der Bestellung höher ist als der Gesamtpreis
-- der Bestellung von einem Kunden am gleichen Bestelldatum beim gleichen Bearbeiter.
-- <p/>
-- Ergebnisschema:
-- [Name]
-- <p/>
-- Punkte:
-- 6.0
--
-- @return SQL Query für Aufgabe 21
SELECT DISTINCT KUNDE.NAME FROM KUNDE, BESTELLUNG B1, BESTELLUNG B2
WHERE KUNDE.KUNDEN_NR = B1.Kunde
  AND B1.BESTELLDATUM = B2.BESTELLDATUM
  AND B1.BEARBEITER = B2.BEARBEITER
  AND B1.GESAMTPREIS > B2.GESAMTPREIS