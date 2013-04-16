/*
 * Copyright (c) 2009 - 2012 by Oli B.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express orimplied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (c)reated 12.10.2009 by Oli B. (ob@aosd.de)
 */

package gdv.xport.satz;

import static gdv.xport.feld.Bezeichner.*;
import gdv.xport.feld.*;

/**
 * Adressteil.
 * <p>
 * Achtung: Diese Klasse wird es in 1.0 nicht mehr geben.
 * </p>
 *
 * @author oliver
 * @since 0.0.2 (12.10.2009)
 * @deprecated durch {@link gdv.xport.satz.model.Satz100} abgeloest
 */
@Deprecated
public final class Adressteil extends Datensatz {

    private void setUpDatenfelder() {
        // Teildatensatz 1
        add(new Zeichen(ANREDESCHLUESSEL, 43, '0'));
        add(new AlphaNumFeld(NAME1, 30, 44));
        add(new AlphaNumFeld(NAME2, 30, 74));
        add(new AlphaNumFeld(NAME3, 30, 104));
        add(new AlphaNumFeld(TITEL, 20, 134));
        add(new AlphaNumFeld(LAENDERKENNZEICHEN, 3, 154));
        add(new AlphaNumFeld(POSTLEITZAHL, 6, 157, Align.LEFT));
        add(new AlphaNumFeld(ORT, 25, 163));
        add(new AlphaNumFeld(STRASSE, 30, 188));
        add(new AlphaNumFeld(POSTFACH, 8, 218));
        add(new Datum(GEBURTSDATUM, 8, 226));
        add(new AlphaNumFeld(STAATSANGEHOERIGKEIT, 3, 234));
        add(new AlphaNumFeld(ADRESSKENNZEICHEN, 2, 237));
        add(new AlphaNumFeld(AKTENZEICHEN_SICHERUNGSGLAEUBIGER, 12, 239));
        add(new AlphaNumFeld(ZIELGRUPPENSCHLUESSEL, 2, 251));
        add(new Zeichen(GROSSRISIKEN, 253));
        add(new Zeichen(POSTALISCHES_KENNZEICHEN, 254));
        add(new Zeichen(GESCHLECHT, 255));
        // Teildatensatz 2
        add(new AlphaNumFeld(KUNDENNR_VERSICHERER, 17, 43, Align.RIGHT), 2);
        add(new AlphaNumFeld(KUNDENNR_VERMITTLER, 17, 60, Align.RIGHT), 2);
        add(new AlphaNumFeld(KUNDENGRUPPE, 30, 77), 2);
        add(new AlphaNumFeld(KONTONR1, 12, 107), 2);
        add(new AlphaNumFeld(BLZ1, 8, 119), 2);
        add(new AlphaNumFeld(ABWEICHENDER_KONTOINHABER1, 30, 127), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSTYP1, 2, 157), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSNR1, 20, 159), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSTYP2, 2, 179), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSNR2, 20, 181), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSTYP3, 2, 201), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSNR3, 20, 203), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSTYP4, 2, 223), 2);
        add(new AlphaNumFeld(KOMMUNIKATIONSNR4, 20, 225), 2);
        add(new Zeichen(ZAHLUNGSART, 245), 2);
        add(new Zeichen(FAMILIENSTAND, 246), 2);
        add(new NumFeld(LFD_PERSONENNR_GEVO, 6, 250), 2);
        // Teildatensatz 3
        add(new AlphaNumFeld(KOMMUNIKATIONSTYP5, 2, 43), 3);
        add(new AlphaNumFeld(KOMMUNIKATIONSNR5, 60, 45), 3);
        add(new AlphaNumFeld(KOMMUNIKATIONSTYP6, 2, 105), 3);
        add(new AlphaNumFeld(KOMMUNIKATIONSNR6, 60, 107), 3);
        add(new AlphaNumFeld(KOMMUNIKATIONSTYP7, 2, 167), 3);
        add(new AlphaNumFeld(KOMMUNIKATIONSNR7, 60, 169), 3);
        add(new AlphaNumFeld(RECHTSFORM, 2, 229), 3);
        // Teildatensatz 4
        add(new AlphaNumFeld(GEBURTSNAME, 30, 43), 4);
        add(new AlphaNumFeld(NAME_KREDITINSTITUT1, 30, 73), 4);
        add(new AlphaNumFeld(TYP_BANKVERBINDUNG1, 2, 103), 4);
        add(new AlphaNumFeld(TYP_BANKVERBINDUNG2, 2, 105), 4);
        add(new AlphaNumFeld(KONTONR2, 12, 107), 4);
        add(new AlphaNumFeld(BLZ2, 8, 119), 4);
        add(new AlphaNumFeld(ABWEICHENDER_KONTOINHABER2, 30, 127), 4);
        add(new AlphaNumFeld(NAME_KREDITINSTITUT2, 30, 157), 4);
        add(new AlphaNumFeld(BIC1, 11, 187), 4);
        add(new AlphaNumFeld(BIC2, 11, 198), 4);
        add(new AlphaNumFeld(IBAN1, 34, 209), 4);
        // Teildatensatz 5
        add(new AlphaNumFeld(IBAN2, 34, 43), 5);
        add(new AlphaNumFeld(GEBURTSORT, 30, 77), 5);
        add(new AlphaNumFeld(GEBURTSLAND, 3, 107), 5);
        add(new AlphaNumFeld(STEUERNR_JURISTISCHE_PERSON, 17, 110), 5);
        add(new AlphaNumFeld(UMSATZSTEUER_ID, 17, 127), 5);
        add(new Zeichen(VORZUGSSTEUERBERECHTIGUNG, 144), 5);
        add(new AlphaNumFeld(VORZUGSSEUERBERECHTIGUNG_PROZENT, 5, 145), 5);
    }

    /**
     * Default Constructor.
     */
    public Adressteil() {
        super("0100", 5);
        this.setUpDatenfelder();
    }

    /**
     * Setzt den Vor- und Nachname. Der mittlere Name bleibt dabei unberuehrt.
     *
     * @param vorname
     *            z.B. "Max"
     * @param nachname
     *            z.B. "Mustermann"
     */
    public void setName(final String vorname, final String nachname) {
        this.set(NAME1, vorname);
        this.set(NAME3, nachname);
    }

    /**
     * Setzt den Vornamen, den mittleren Namen (zweiter Vorname) und den Nachnamen.
     *
     * @param vorname
     *            erster Vorname
     * @param mittelname
     *            zweiter Name
     * @param nachname
     *            Nachnahme
     */
    public void setName(final String vorname, final String mittelname, final String nachname) {
        this.set(NAME1, vorname);
        this.set(NAME2, mittelname);
        this.set(NAME3, nachname);
    }

    /**
     * Liefert den entsprechenden Namen zurueck.
     *
     * @param n
     *            1, 2 oder 3
     * @return Name1, Name2 oder Name3
     */
    public String getName(final int n) {
        assert n >= 1 && n <= 3 : "1 <= n <= 3 expected";
        switch (n) {
            case 1:
                return this.getFeldInhalt(NAME1);
            case 2:
                return this.getFeldInhalt(NAME2);
            case 3:
                return this.getFeldInhalt(NAME3);
            default:
                throw new IllegalArgumentException("1 <= n <= 3 expected");
        }
    }

}
