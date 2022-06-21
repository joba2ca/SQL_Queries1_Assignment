-- Queryergebnis:
-- Finden Sie alle Lieferanten, die keine Artikel der Marke "Brand#53" führen.
--
-- Ergebnisschema:
-- [Name(↑) | Adresse]
--
-- Punkte:
-- 0.5
--
SELECT NAME, ADRESSE FROM LIEFERANT
WHERE LIEFERANTEN_NR NOT IN
      (SELECT DISTINCT LIEFERANT FROM LIEFERT
       WHERE ARTIKEL IN
             (SELECT ARTIKEL_NR FROM ARTIKEL
              WHERE MARKE LIKE 'Brand#53%'))
ORDER BY NAME
