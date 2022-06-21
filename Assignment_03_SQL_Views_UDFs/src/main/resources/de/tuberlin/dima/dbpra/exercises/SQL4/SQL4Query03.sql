-- Aufgabe 3:
-- MessungStatistik
-- (siehe Folien)
--
-- Punkte:
-- 1.5
--
CREATE VIEW messungStatistik AS
    (SELECT series, ( max(theta(x,y)) - min(theta(x,y)) ) angularSpread, avg(r(x,y)) avgLength
    FROM MESSUNGEN
    GROUP BY series);
