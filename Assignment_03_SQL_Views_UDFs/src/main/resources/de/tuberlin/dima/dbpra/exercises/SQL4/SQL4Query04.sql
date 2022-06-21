-- Aufgabe 4:
-- (siehe Folien)
--
-- Punkte:
-- 2.0
--
CREATE OR REPLACE TRIGGER polarMessungen_Update
    INSTEAD OF UPDATE ON polarMessungen
    REFERENCING
        OLD AS o
        NEW AS n
    FOR EACH ROW
        UPDATE MESSUNGEN m
        SET m.X = n.r * cos(n.theta) ,
            m.Y = n.r * sin(n.theta)
        WHERE n.series = m.series AND
            n.id = m.id;
