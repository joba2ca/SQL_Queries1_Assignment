-- Aufgabe 6:
-- Funktion um der Tabelle KundenKontaktDaten ein check constraint
-- mit dem Namen MindestensEinKontakt_CHECK hinzuzufügen.
--
-- Das Check Constraint soll sicher stellen dass mindestens eines der Attribute
-- TWITTER_ID, GOOGLE_ID, FACEBOOK_ID, SKYPE_ID, TELEFONNUMMER nicht NULL ist.
--
-- Punkte:
-- 1
--
ALTER TABLE KundenKontaktDaten
    ADD CONSTRAINT MindestensEinKontakt_CHECK
        CHECK (  TWITTER_ID IS NOT NULL OR
                GOOGLE_ID IS NOT NULL OR
                FACEBOOK_ID IS NOT NULL OR
                SKYPE_ID IS NOT NULL OR
                TELEFONNUMMER IS NOT NULL )
