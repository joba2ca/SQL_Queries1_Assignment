-- Aufgabe 5:
-- areaUnderCurveSP
-- (siehe Folien)
--
-- Punkte:
-- 3
--
CREATE or replace PROCEDURE ComputeAreaUnderCurve()
BEGIN
    DECLARE i INTEGER;
    DECLARE area DOUBLE;
    DECLARE loopLength INTEGER;
    SET loopLength = (select count(*)
                      from(
                              select SERIES
                              from MESSUNGEN
                              group by SERIES));
    SET i = 1;
    SET area = 0;
    WHILE i <= loopLength DO
    SET area = (select sum((((m1.x-m2.x)) * (m1.y+m2.y))/2) as result
                from
                    (select x, y, id, SERIES, ROW_NUMBER() OVER () rownumber
                     from MESSUNGEN
                     where SERIES=i
                     order by x asc) as m1
                        join
                    (select x, y, id, SERIES, ROW_NUMBER() OVER () rownumber
                     from MESSUNGEN
                     where SERIES=i
                     order by x asc) as m2
                    on m2.rownumber=m1.rownumber-1);

    IF ((select count(*)
         from
             (select SERIES
              from MESSUNGEN
              group by SERIES)
         WHERE SERIES = i) = 0) then
        INSERT INTO AREA_UNDER_CURVE (SERIES,AREA) values (i, area);
    ELSE
        DELETE FROM AREA_UNDER_CURVE WHERE SERIES = i;
        INSERT INTO AREA_UNDER_CURVE (SERIES,AREA) values (i, area);
        SET i = i + 1;
        SET area = 0;
    END IF;
    END WHILE;
END;