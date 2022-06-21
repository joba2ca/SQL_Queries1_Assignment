-- Funktion um der Tabelle KundenKontaktDaten einen Trigger hinzuzufügen.
--	 * Die Funktion soll zunächst die Tabelle TelefonnummerAenderungen
--	 * (Definition siehe Aufgabenstellung) anlegen. Falls die Tabelle bereits
--	 * existiert, soll der Inhalt der Tabelle gelöscht werden.

CREATE TABLE TelefonnummerAenderungen (
    GEAENDERT_AM TIMESTAMP NOT NULL,
    KUNDEN_NR INTEGER NOT NULL,
    ALTE_NUMMER VARCHAR(50),
    FOREIGN KEY (KUNDEN_NR)REFERENCES KUNDE(KUNDEN_NR)
        ON DELETE CASCADE
        ON UPDATE RESTRICT,
    PRIMARY KEY(GEAENDERT_AM, KUNDEN_NR) )