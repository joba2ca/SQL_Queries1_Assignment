-- Queryergebnis:
-- Bestimmen Sie für alle Lieferanten aus "FRANCE" jeweils die Artikel, bei denen der verfügbare Lieferwert mehr ist,
-- als 1/500 des verfügbaren Lieferwertes aller Artikel dieses Lieferanten.
-- Der verfügbare Lieferwert eines Artikels ist das Produkt aus der verfügbaren Anzahl und dem Lieferpreis.
--
-- Ergebnisschema:
-- [Lieferanten_Nr (↑1) | Artikel (ID) | Wert (↓2)]
--
-- Punkte:
-- 1.5
--
SELECT LIEFERANT lieferanten_nr, ARTIKEL, (ANZAHL_VERFUEGB * LIEFERPREIS) wert
FROM LIEFERT,
     (SELECT LI.LIEFERANT lieferer, sum(ANZAHL_VERFUEGB * LIEFERPREIS) gesamtwert
      FROM LIEFERT LI, LAND LA, LIEFERANT L
      WHERE LI.LIEFERANT = L.LIEFERANTEN_NR
        AND L.LAND = LA.LAND_ID
        AND LA.NAME = 'FRANCE'
      GROUP BY LI.LIEFERANT) francegesamt
WHERE LIEFERT.LIEFERANT = francegesamt.lieferer
  AND (ANZAHL_VERFUEGB * LIEFERPREIS) > francegesamt.gesamtwert / 500
ORDER BY lieferanten_nr ASC, wert DESC