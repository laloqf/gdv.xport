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
 * (c)reated 11.10.2009 by Oli B. (ob@aosd.de)
 */

package gdv.xport.feld;

import gdv.xport.annotation.FeldInfo;

/**
 * Standardmaessig hat das Beitrags-Feld 12,2 Stellen (12 Vorkommastellen +
 * 2 Nachkommastellen.
 *
 * @author oliver
 * @since 11.10.2009
 * @version $Revision$
 */
public class Betrag extends NumFeld {

    /**
     * @param name Name des Feldes
     */
    public Betrag(final String name) {
        super(name, "00000000000000");
    }

    /**
     * Legt ein neues Betrags-Feld an. Die Informationen dazu werden
     * aus der uebergebenen Enum bezogen.
     *
     * @param feldX Enum mit den Feldinformationen
     * @since 0.9
     */
    public Betrag(final Enum<?> feldX) {
        super(feldX);
    }

    /**
     * Instantiiert ein neuen Betrag.
     *
     * @param feldX Feld
     * @param info mit der Start-Adresse und weiteren Angaben
     * @since 0.6
     */
    public Betrag(final Enum<?> feldX, final FeldInfo info) {
        super(feldX, info);
    }

    /**
     * Instantiiert einen neuen Betrag.
     * <p>
     * TODO: bitte nicht mehr verwenden - wird in 1.2 entfernt!
     * </p>
     *
     * @param name Name des Feldes
     * @param length Laenge
     * @param start Start-Byte (beginnend bei 1)
     * @deprecated bitte {@link #Betrag(Bezeichner, int, int)} benutzen
     */
    @Deprecated
    public Betrag(final String name, final int length, final int start) {
        this(new Bezeichner(name), length, start);
    }

    /**
     * Instantiiert einen neuen Betrag.
     * <p>
     * TODO: bitte nicht mehr verwenden - wird in 1.2 entfernt!
     * </p>
     *
     * @param name Name des Feldes
     * @param length Laenge
     * @param start Start-Byte (beginnend bei 1)
     * @since 1.0
     */
    public Betrag(final Bezeichner name, final int length, final int start) {
        super(name, length, start, 0, 2);
    }

    /**
     * Instantiiert einen neuen Betrag.
     * <p>
     * TODO: bitte nicht mehr verwenden - wird in 1.2 entfernt!
     * </p>
     *
     * @param name Bezeichner
     * @param info mit der Start-Adresse und weiteren Angaben
     * @since 0.6
     * @deprecated bitte {@link #Betrag(Bezeichner, FeldInfo)} benutzen
     */
    @Deprecated
    public Betrag(final String name, final FeldInfo info) {
        this(new Bezeichner(name), info);
    }

    /**
     * Instantiiert einen neuen Betrag.
     *
     * @param name Bezeichner
     * @param info mit der Start-Adresse und weiteren Angaben
     * @since 1.0
     */
    public Betrag(final Bezeichner name, final FeldInfo info) {
        super(name, info);
    }

    /* (non-Javadoc)
     * @see gdv.xport.feld.NumFeld#setInhalt(int)
     */
    @Override
    public void setInhalt(final int n) {
        assert n >= 0 : "Betrag can't store negative number (" + n + ")";
        super.setInhalt(n * 100);
    }

    /**
     * Setzt den Inhalt eines Feldes neu.
     * @param x der neue Inhalt
     */
    public void setInhalt(final double x) {
        assert x >= 0 : "Betrag can't store negative number (" + x + ")";
        long n = Math.round(x * 100);
        super.setInhalt(n);
    }

    /* (non-Javadoc)
     * @see gdv.xport.feld.NumFeld#toInt()
     */
    @Override
    public int toInt() {
        return super.toInt() / 100;
    }

    @Override
    public double toDouble() {
        return super.toInt() / 100.0;
    }

}

