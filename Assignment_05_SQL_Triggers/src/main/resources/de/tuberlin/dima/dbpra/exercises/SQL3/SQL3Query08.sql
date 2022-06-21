--Anschließend soll die Funktion Trigger für die Tabelle Kundenkontakdaten
--	 * anlegen, so dass:
--	 * - Eine Änderung der Telefonnummer für einen Kunden kann frühestens
--	 * 15 Sekunden nach der letzten Änderungen vorgenommen werden.
--	 * o Ansonsten wird ein SIGNAL SQLSTATE '70001' Statement geworfen.
--	 * - Bei einer erfolgreichen Änderung wird ein entsprechender Log-Eintrag
--	 * in die Tabelle TelefonnummerAenderungen vorgenommen.
--	 * - Sind alte und neue Telefonnummer identisch, wird keine Aktion
--	 * vorgenommen.

CREATE or replace TRIGGER TelefonnummerUpdateTrigger
    AFTER UPDATE OF Telefonnummer ON KundenKontaktDaten
    REFERENCING
        OLD AS old_row
        NEW AS new_row
    FOR EACH ROW
    WHEN (old_row.Telefonnummer <> new_row.Telefonnummer OR
          (old_row.TELEFONNUMMER IS NULL AND new_row.TELEFONNUMMER IS NOT NULL) OR
          (new_row.TELEFONNUMMER IS NULL AND old_row.TELEFONNUMMER IS NOT NULL) )
    IF(NOT EXISTS(SELECT * FROM TelefonnummerAenderungen t
                            WHERE old_row.Kunden_Nr = t.Kunden_Nr AND
                                CURRENT TIMESTAMP - 15 SECONDS < t.GEAENDERT_AM))
    THEN INSERT INTO TelefonnummerAenderungen
            VALUES (CURRENT TIMESTAMP, old_row.Kunden_Nr, old_row.Telefonnummer);
    ELSE SIGNAL SQLSTATE '70001';
    END IF;