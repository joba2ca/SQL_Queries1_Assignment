-- Queryergebnis:
-- Ermitteln Sie für alle Kombinationen aus Versandart und Versandanweisung den Gesamtumsatz ohne Steuern aller Bestellposten,
-- die zurückgesendet wurden (Status R). (Das Feld Preis beinhaltet bereits die Steuern, das Feld Steuer beinhaltet den Steuersatz.
-- Ein Steuersatz von 19% hat hier beispielsweise den Wert 0,19).
-- <p/>
-- Ergebnisschema:
-- [Versandart↑2|Versandanweisung↑1|Gesamtumsatz]
-- <p/>
-- Punkte:
-- 2.0
--
-- @return SQL Query für Aufgabe 11
SELECT VERSANDART, VERSANDANWEISUNG, sum(Preis/(1+STEUER)) as Gesamtumsatz FROM BESTELLPOSTEN
WHERE RETOURSTATUS = 'R'
GROUP BY VERSANDART, VERSANDANWEISUNG
ORDER BY VERSANDANWEISUNG, VERSANDART ASC
