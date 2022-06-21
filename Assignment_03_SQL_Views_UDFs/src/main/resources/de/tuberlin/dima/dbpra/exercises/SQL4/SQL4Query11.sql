-- Aufgabe 1 (Teil 1):
-- Schreiben Sie zwei UDFs die (2D) kartesische Koordinaten
-- in Polarkoordinaten umrechnen.
-- (siehe Folien)
--
-- Punkte:
-- 1.0
--
CREATE FUNCTION r(x DOUBLE, y DOUBLE)
    RETURNS DOUBLE
RETURN sqrt(x*x + y*y);
