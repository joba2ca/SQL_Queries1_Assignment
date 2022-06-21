-- Queryergebnis:
-- Finden Sie die Sachbearbeiter, deren durchschnittliche Bestellungsbearbeitung
-- (Versanddatum - Bestelldatum)
-- mehr als 5% über dem Gesamtdurchschnitt liegt.
--
-- Ergebnisschema:
-- [Bearbeiter(↑)]
--
-- Punkte:
-- 1.0
--
SELECT B.BEARBEITER as Bearbeiter FROM BESTELLUNG B, BESTELLPOSTEN BP
WHERE B.BESTELL_NR = BP.BESTELL_NR
GROUP BY B.BEARBEITER
HAVING avg(days(BP.VERSANDDATUM) - days(B.BESTELLDATUM)) > 1.05 *
                                                           (SELECT avg(days(VERSANDDATUM) - days(BESTELLDATUM)) FROM BESTELLPOSTEN BP, BESTELLUNG B
                                                            WHERE B.BESTELL_NR = BP.BESTELL_NR)
ORDER BY Bearbeiter ASC