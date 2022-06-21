-- Queryergebnis:
-- Geben sie für jeden Hersteller aus, wie viel Rabatt im Durchschnitt beim Verkaufen ihrer Artikel gewährt wird.
-- Hinweis: Das Attribut "Rabatt" der Tabelle "Bestellposten" enthält den prozentualen Rabatt, bezogen auf den Gesamtpreis.
-- Geben Sie im Ergebnis den absoluten Rabatt aus.
-- <p/>
-- Ergebnisschema:
-- [Hersteller | Rabatt]
-- <p/>
-- Punkte:
-- 4.0
--
-- @return SQL Query für Aufgabe 19
SELECT ARTIKEL.HERSTELLER, avg((BESTELLPOSTEN.PREIS * BESTELLPOSTEN.RABATT)) as rabatt FROM BESTELLPOSTEN, ARTIKEL
WHERE BESTELLPOSTEN.ARTIKEL = ARTIKEL.ARTIKEL_NR
GROUP BY HERSTELLER