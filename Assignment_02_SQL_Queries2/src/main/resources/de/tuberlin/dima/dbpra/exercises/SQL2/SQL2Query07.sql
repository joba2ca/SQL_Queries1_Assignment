-- Queryergebnis:
-- Finden Sie für jede Versandart die Marke, deren Bestellposten am häufigsten mit dieser Versandart versandt worden sind.
--
-- Ergebnisschema:
-- [Versandart (↑) | Marke | Anzahl]
--
-- Punkte:
-- 1.0
--
SELECT x.VERSANDART, x.MARKE, x.anzahl
FROM
    (SELECT VERSANDART, MARKE, count(*) as anzahl FROM ARTIKEL A, BESTELLPOSTEN BP
     WHERE A.ARTIKEL_NR = BP.ARTIKEL
     GROUP BY VERSANDART, MARKE
     ORDER BY VERSANDART, anzahl DESC) AS X,

    (SELECT max(anzahl) as max
     FROM (SELECT VERSANDART, MARKE, count(*) as anzahl FROM ARTIKEL A, BESTELLPOSTEN BP
           WHERE A.ARTIKEL_NR = BP.ARTIKEL
           GROUP BY VERSANDART, MARKE
           ORDER BY VERSANDART, anzahl DESC)
     GROUP BY Versandart) AS Y
WHERE X.anzahl = y.max

