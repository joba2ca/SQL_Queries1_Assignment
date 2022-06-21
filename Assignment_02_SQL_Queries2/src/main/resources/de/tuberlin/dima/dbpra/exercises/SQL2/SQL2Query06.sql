-- Queryergebnis:
-- Bestimmen die Sie Marke(n), die von den meisten Lieferanten angeboten wird.
--
-- Ergebnisschema:
-- [Marke] (↑)
--
-- Punkte:
-- 1.0
--
SELECT marke
FROM (SELECT A.MARKE as marke, count(DISTINCT LI.LIEFERANT) as anzahlLieferanten
      FROM LIEFERT LI, ARTIKEL A
      WHERE A.ARTIKEL_NR = LI.ARTIKEL
      GROUP BY A.Marke
      ORDER BY anzahlLieferanten DESC) AS X,

     (SELECT max(anzahlLieferanten) as max
      FROM (SELECT A.MARKE as marke, count(DISTINCT LI.LIEFERANT) as anzahlLieferanten
            FROM LIEFERT LI, ARTIKEL A
            WHERE A.ARTIKEL_NR = LI.ARTIKEL
            GROUP BY A.Marke
            ORDER BY anzahlLieferanten DESC)) AS Y
WHERE Y.max = X.anzahlLieferanten
ORDER BY marke ASC




