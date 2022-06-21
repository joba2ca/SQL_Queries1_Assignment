-- Queryergebnis:
-- Finden sie für jeden Artikel des Typs "MEDIUM ANODIZED TIN" den Lieferanten aus der Region 'ASIA',
-- der für den Artikel den niedrigsten Lieferpreis hat.
-- Der Lieferpreis eines Lieferanten für einen Artikel ist das gleichnamige Attribut auf der "Liefert" Relation.
--
-- Ergebnisschema:
-- [Artikel_Nr (↑) | Lieferant_Name (↑) | Lieferpreis]
--
-- Punkte:
-- 1.0
--
SELECT asiamin.artikelnummer as artikel_nr, L.NAME as lieferant_name, asiamin.lieferpreis as lieferpreis
FROM LIEFERT LI,
     LIEFERANT L,
     (SELECT asiatin.ARTIKEL as artikelnummer, min(asiatin.Lieferpreis) as lieferpreis
      FROM (SELECT LIE.ARTIKEL, LIEFERPREIS
            FROM (SELECT LIEFERANTEN_NR FROM LIEFERANT, LAND, REGION
                  WHERE REGION.NAME = 'ASIA'
                    AND REGION_ID = LAND.REGION
                    AND LAND_ID = LIEFERANT.LAND) as asialieferanten,
                 LIEFERT LIE,
                 ARTIKEL A
            WHERE asialieferanten.LIEFERANTEN_NR = LIE.LIEFERANT
              AND A.ARTIKEL_NR = LIE.ARTIKEL
              AND A.TYP = 'MEDIUM ANODIZED TIN') as asiatin
      GROUP BY  asiatin.ARTIKEL) asiamin
WHERE asiamin.artikelnummer = LI.ARTIKEL
  AND LI.LIEFERANT = L.LIEFERANTEN_NR
  AND asiamin.lieferpreis = LI.LIEFERPREIS
ORDER BY artikelnummer ASC, lieferant_name ASC




