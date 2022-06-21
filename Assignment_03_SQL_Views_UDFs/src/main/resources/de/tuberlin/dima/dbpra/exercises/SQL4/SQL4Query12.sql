-- Aufgabe 1 (Teil 2):
-- Schreiben Sie zwei UDFs die (2D) kartesische Koordinaten
-- in Polarkoordinaten umrechnen.
-- (siehe Folien)
--
-- Punkte:
-- 1.0
--
create function theta(x DOUBLE, y DOUBLE)
    RETURNS DOUBLE
BEGIN
    IF x = 0 AND y = 0 THEN
        RETURN 0;
    ELSE
        RETURN atan2(x,y);
    END IF;
END;
