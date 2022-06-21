-- Queryergebnis:
-- Berechnen sie für jeden Kunden, wie viele seiner Bestellungen vollständig mit Artikeln von lokalen Lieferanten erfüllt worden sind.
-- Lokaler Lieferant bedeutet, dass der Lieferant in dem gleichen Land wie der Kunde ansässig ist.
-- Geben sie alle Kunden aus, die mindestens eine solche lokale Bestellung hatten, absteigend nach der Anzahl der lokalen Bestellungen.
--
-- Ergebnisschema:
-- [KundenName(↑2) | Anzahl (↓1)]
--
-- Punkte:
-- 1.5
--
--ANzahl rein lokaler bestellungen pro kunde

SELECT K.NAME kundenname, count(DISTINCT B.BESTELL_NR) anzahl
FROM KUNDE K,
     BESTELLUNG B,
     BESTELLPOSTEN BP,
     LIEFERANT L,
     (SELECT B.BESTELL_NR nummer
      FROM BESTELLUNG B, BESTELLPOSTEN BP, LIEFERANT L
      WHERE B.BESTELL_NR = BP.BESTELL_NR
        AND BP.LIEFERANT = L.LIEFERANTEN_NR
      GROUP BY B.BESTELL_NR
      HAVING count(DISTINCT L.LAND) = 1
      ORDER BY B.BESTELL_NR) onelandorder
WHERE K.KUNDEN_NR = B.KUNDE
  AND B.BESTELL_NR = onelandorder.nummer
  AND B.BESTELL_NR = BP.BESTELL_NR
  AND BP.LIEFERANT = L.LIEFERANTEN_NR
  AND L.LAND = K.LAND
GROUP BY K.NAME
ORDER BY anzahl DESC, kundenname ASC


