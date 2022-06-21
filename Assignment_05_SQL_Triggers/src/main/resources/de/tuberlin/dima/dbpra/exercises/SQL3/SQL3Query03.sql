-- Aufgabe 3:
-- Funktion um der Tabelle KundenKontaktDaten ein check constraint
-- mit dem Namen TwitterID_Check auf das Attribut Twitter_ID hinzuzufügen.
--
-- Das Check Constraint soll sicher stellen dass:
-- - Alle Twitter_IDs mit @ anfangen.
-- - Und nach dem @ eine Zeichenkette folgt, die
-- o Maximal 31 Zeichen lang ist
-- o Nur aus kleinen lateinische Buchstaben, Zahlen, und '_' besteht.
--
-- Punkte:
-- 2
--
ALTER TABLE KundenKontaktDaten
    ADD CONSTRAINT TwitterID_CHECK
        CHECK (Twitter_Id LIKE '@%'
            AND TRIM(LENGTH(Twitter_Id)) <= 32
            AND LENGTH(TRIM(TRANSLATE( SUBSTR(Twitter_Id,2,31),' ','abcdefghijklmnopqrstuvwxyz0123456789_'))) = 0)