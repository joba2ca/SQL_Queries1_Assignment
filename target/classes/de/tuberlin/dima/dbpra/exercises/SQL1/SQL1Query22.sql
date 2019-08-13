-- Queryergebnis:
-- Finden Sie Kunden (mit Kundennummer, Name, Telefonnummer und deren Landesname) und erzeugtem Verlust,
-- den sie durch zurückgegebene Artikel erzeugt haben.
-- Geben Sie nur die Top 20 Kunden aus, die den meisten Verlust erzeugt haben.
-- Hinweis, Retourstatus 'R' bedeutet zurückgegeben.
-- <p/>
-- Ergebnisschema:
-- [Kunden_Nr | Name | Telefon | Land | Verlust (↓)]
-- <p/>
-- Punkte:
-- 8.0
--
-- @return SQL Query für Aufgabe 22
SELECT K.KUNDEN_NR, K.NAME, K.TELEFON, L.NAME as land, sum(BP.PREIS) as Verlust
FROM KUNDE K, BESTELLUNG B, LAND L, BESTELLPOSTEN BP
WHERE BP.RETOURSTATUS = 'R'
  AND BP.BESTELL_NR = B.BESTELL_NR
  AND B.KUNDE = K.KUNDEN_NR
  AND K.LAND = L.LAND_ID
GROUP BY K.KUNDEN_NR, K.NAME, K.TELEFON, L.NAME
ORDER BY Verlust DESC
FETCH FIRST 20 ROWS ONLY