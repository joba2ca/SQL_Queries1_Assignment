-- Queryergebnis:
-- Finden Sie für alle Marken diejenigen Artikel, die in den obersten 1% der Preisspanne der Marke sind.
-- Die Preisspanne ist die Differenz zwischen dem billigsten und dem teuersten Artikel.
--
-- Ergebnisschema:
-- [Marke (↑1) | Name (↑2) ]
--
-- Punkte:
-- 1.0
--
-- @return SQL Query für Aufgabe 8
--
SELECT A.MARKE, A.Name
FROM (SELECT Marke, max(PREIS) - min(PREIS) as preisspanne, ((max(PREIS) - min(PREIS)) / 100) as einprozent, max(Preis) as max
     FROM ARTIKEL
     GROUP BY Marke) as spanne,

    ARTIKEL A
WHERE A.PREIS >= spanne.max - spanne.einprozent
      AND spanne.MARKE = A.MARKE
ORDER BY A.MARKE ASC, A.NAME ASC