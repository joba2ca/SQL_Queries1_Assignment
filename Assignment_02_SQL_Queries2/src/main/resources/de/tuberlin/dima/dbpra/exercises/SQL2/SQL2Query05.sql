-- Queryergebnis:
-- Finden Sie Bestellungen, die zwischen dem 15.07.1996 und dem 15.10.1996 (jeweils inkl.) aufgegeben wurden,
-- und bei denen mindestens ein Bestellposten später angeliefert wurde, als zugesichert.
-- Geben sie pro Bestellpriorität aus, für wie viele Bestellungen dies der Fall war.
-- Hinweis: Das Attribut "Empfangsdatum" beschreibt, wann ein Bestellposten angekommen ist.
-- Das Attribute "Bestätigungsdatum" beschreibt, für wann die Ankunft des Bestellpostens zugesichert wurde.
--
-- Ergebnisschema:
-- [Prioritaet (↑) | Anzahl]
--
-- Punkte:
-- 1.0
--
SELECT BESTELLPRIORITAET prioritaet, count(*) anzahl
FROM BESTELLUNG B,
                  (SELECT DISTINCT BESTELLPOSTEN.BESTELL_NR nummer FROM BESTELLPOSTEN, BESTELLUNG
                   WHERE EMPFANGSDATUM > BESTAETIGUNGSDATUM
                     AND BESTELLUNG.BESTELL_NR = BESTELLPOSTEN.BESTELL_NR
                     AND BESTELLDATUM BETWEEN '1996-07-15' AND '1996-10-15') as nummern
WHERE nummern.nummer = BESTELL_NR
GROUP BY BESTELLPRIORITAET
ORDER BY prioritaet ASC