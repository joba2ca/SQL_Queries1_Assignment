-- Queryergebnis:
-- Finden Sie die Marken deren durchschnittlicher Produktpreis höher als der Durchschnittspreis
-- aller Produkte ist.
--
-- Ergebnisschema
-- [Marke(↑) | d_preis]
--
-- Punkte:
-- 0.5
--
SELECT MARKE, avg(Preis) as d_preis FROM ARTIKEL, (SELECT avg(Preis) FROM ARTIKEL) as t
GROUP BY MARKE
HAVING avg(Preis) > (SELECT avg(Preis) FROM ARTIKEL)
