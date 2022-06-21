-- Aufgabe 2:
-- Messungen
-- (siehe Folien)
--
-- Punkte:
-- 1.5
--
CREATE VIEW polarMessungen AS
    (SELECT series, id, r(x,y) AS r, theta(x,y) AS theta FROM MESSUNGEN);

