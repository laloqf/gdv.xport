/*
 * Copyright (c) 2009 - 2012 by Oli B. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express orimplied. See the License for the specific language
 * governing permissions and limitations under the License. (c)reated 12.10.2009
 * by Oli B. (ob@aosd.de)
 */

package gdv.xport.satz;

import static gdv.xport.feld.Bezeichner.LEERSTELLEN;
import static gdv.xport.feld.Bezeichner.TEILDATENSATZNUMMER;
import static gdv.xport.feld.Bezeichner.VERMITTLER;
import static gdv.xport.feld.Bezeichner.WAGNISART;
import gdv.xport.config.Config;
import gdv.xport.feld.AlphaNumFeld;
import gdv.xport.feld.Bezeichner;
import gdv.xport.feld.Feld;
import gdv.xport.feld.NumFeld;
import gdv.xport.io.ImportException;
import gdv.xport.io.PushbackLineNumberReader;
import gdv.xport.satz.feld.common.Feld1bis7;
import gdv.xport.satz.feld.common.TeildatensatzNummer;
import gdv.xport.satz.feld.common.WagnisartLeben;
import gdv.xport.util.SatzNummer;

import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Datensatz ist von {@link Satz} abgeleitet, enthaelt aber zusaetzlich noch
 * die Sparte.
 *
 * @author oliver
 * @since 12.10.2009
 */
public class Datensatz extends Satz {

	private static final Log LOG = LogFactory.getLog(Datensatz.class);
	/** 3 Zeichen, Byte 11 - 13. */
    private final NumFeld sparte = new NumFeld(Feld1bis7.SPARTE);
	/** 3 Zeichen, Byte 59 - 60. */
	private final AlphaNumFeld wagnisart = new AlphaNumFeld(new Bezeichner(WAGNISART), 1, 59);
	/** 3 Zeichen, Byte 255 - 256. */
    private final AlphaNumFeld teildatensatzNummer = new AlphaNumFeld(new Bezeichner(TEILDATENSATZNUMMER), 1, 255);
	/** Zum Abspeichern der Wagnisart oder Art (Unter-Sparte). */
	private int art;

	/**
	 * Default-Konstruktor (wird zur Registrierung bei der.
	 * {@link gdv.xport.util.SatzFactory} benoetigt).
	 *
	 * @since 0.6
	 */
	public Datensatz() {
		this(0, getTeildatensaetzeFor(0, Feld1bis7.values()));
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzart z.B. "0100"
	 */
	public Datensatz(final String satzart) {
		super(satzart);
		this.setUpTeildatensaetze();
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzart z.B. 100
	 */
	public Datensatz(final int satzart) {
		super(satzart, 1);
		this.setUpTeildatensaetze();
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzart z.B. 100
	 * @param n Anzahl der Teildatensaetze
	 */
	public Datensatz(final String satzart, final int n) {
		super(satzart, n);
		this.setUpTeildatensaetze();
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzart z.B. 100
	 * @param tdsList Liste mit den Teildatensaetzen
	 */
	public Datensatz(final int satzart, final List<? extends Teildatensatz> tdsList) {
		super(satzart, tdsList);
		if (tdsList.get(0).hasSparte()) {
		    this.sparte.setInhalt(tdsList.get(0).getSparte());
		}
		this.completeTeildatensaetze();
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzart z.B. 100
	 * @param sparte z.B. 70 (Rechtsschutz)
	 */
	public Datensatz(final int satzart, final int sparte) {
		this(satzart, sparte, 1);
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzart z.B. 100
	 * @param sparte z.B. 70 (Rechtsschutz)
	 * @param n Anzahl der Teildatensaetze
	 */
	public Datensatz(final int satzart, final int sparte, final int n) {
		super(satzart, n);
		this.setSparte(sparte);
		this.setUpTeildatensaetze();
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzart z.B. 100
	 * @param sparte z.B. 70 (Rechtsschutz)
	 * @param tdsList Liste mit den Teildatensaetzen
	 */
	public Datensatz(final int satzart, final int sparte, final List<Teildatensatz> tdsList) {
		this(satzart, tdsList);
		this.setSparte(sparte);
		this.completeTeildatensaetze();
	}

	/**
	 * Instantiiert einen neuen Datensatz.
	 *
	 * @param satzNr die SatzNummer
	 * @param tdsList Liste mit den Teildatensaetzen
	 * @since 0.9
	 */
	public Datensatz(final SatzNummer satzNr, final List<Teildatensatz> tdsList) {
		this(satzNr.getSatzart(), tdsList);
		if (satzNr.hasSparte()) {
			this.setSparte(satzNr.getSparte());
		}
		if (satzNr.hasWagnisart()) {
			this.setWagnisart("" + satzNr.getWagnisart());
		}
		if (satzNr.hasTeildatensatzNummer()) {
			this.setTeildatensatzNummer("" + satzNr.getTeildatensatzNummer());
		}
		this.completeTeildatensaetze();
	}

	/**
	 * Kann von Unterklassen verwendet werden, um die Teildatensaetze
	 * aufzusetzen.
	 */
	protected void setUpTeildatensaetze() {
		for (Teildatensatz tds : this.getTeildatensaetze()) {
			setUpTeildatensatz(tds);
		}
	}

	/**
	 * Hiermit kann ein einzelner Teildatensatz aufgesetzt werden.
	 * <p>
	 * Wenn alle Datensaetze nur noch ueber Enums (Soplets) initialisiert
	 * werden, duerfte die Inialisierung hier hinfaellig sein.
	 * </p>
	 *
	 * @param tds der (leere) Teildatensatz
	 * @since 0.4
	 */
	protected void setUpTeildatensatz(final Teildatensatz tds) {
	    this.setUp(tds, Feld1bis7.VU_NUMMER, Config.getVUNummer());
        this.setUp(tds, Feld1bis7.BUENDELUNGSKENNZEICHEN, new AlphaNumFeld(Feld1bis7.BUENDELUNGSKENNZEICHEN));
        this.setUp(tds, Feld1bis7.SPARTE, this.sparte);
        this.setUp(tds, Feld1bis7.VERSICHERUNGSSCHEINNUMMER, new AlphaNumFeld(Feld1bis7.VERSICHERUNGSSCHEINNUMMER));
        this.setUp(tds, Feld1bis7.FOLGENUMMER, new NumFeld(Feld1bis7.FOLGENUMMER));
        this.setUp(tds, Feld1bis7.VERMITTLER, new AlphaNumFeld(Feld1bis7.VERMITTLER));
	}

    private void setUp(final Teildatensatz tds, final Enum<?> feldX, final Feld value) {
        if (!tds.hasFeld(feldX)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Init " + tds + " with " + value + ".");
            }
            tds.add(value);
        }
    }

	/**
	 * Kann von Unterklassen verwendet werden, um fehlende Felder in den
	 * Teildatensaetze zu vervollstaendigen. Kann aber seit 1.0 nicht mehr
	 * ueberschrieben werden, da diese Methode vom Konstruktor waehrend der
	 * Objekt-Kreierung benoetigt wird.
	 *
	 * @since 0.6
	 */
	protected final void completeTeildatensaetze() {
		for (Teildatensatz tds : this.getTeildatensaetze()) {
			setUpTeildatensatz(tds);
		}
	}

	/**
	 * Hiermit kann ein einzelner Teildatensatz aufgesetzt werden.
	 *
	 * @param n Nummer des Teildatensatzes (beginnend bei 1)
	 * @since 0.5
	 */
	protected final void setUpTeildatensatz(final int n) {
		this.setUpTeildatensatz(this.getTeildatensatz(n));
	}

	/*
	 * (non-Javadoc)
	 * @see gdv.xport.satz.Satz#addFiller()
	 */
	@Override
	public void addFiller() {
		for (Teildatensatz tds : this.getTeildatensaetze()) {
			tds.add(new AlphaNumFeld(new Bezeichner(LEERSTELLEN), 213, 43));
		}
	}

	/**
	 * Sets the sparte.
	 *
	 * @param x z.B. 70 (Rechtsschutz)
	 */
	public void setSparte(final int x) {
		this.sparte.setInhalt(x);
	}

	/**
	 * Setzt die Sparte. Der uebergebene String kann dabei auch die Art der
	 * Sparte enthalten.
	 *
	 * @param x z.B. "580.01" fuer Sparte 580, Art 01
	 */
	public void setSparte(final String x) {
	    String[] parts = x.split("\\.");
	    this.setSparte(Integer.parseInt(parts[0]));
	    if (parts.length > 1) {
	        this.art = Integer.parseInt(parts[1]);
	    }
	}

	/**
	 * Gets the sparte.
	 *
	 * @return die Sparte als int
	 */
	@Override
    public int getSparte() {
		return this.sparte.toInt();
	}

	/**
     * Manche Satzarten wie Bausparen haben eine Element fuer die Untersparte,
     * im Feld Wagnisart oder Art abgespeichert. Dies ist z.B. fuer Satz
     * 220.580.1 (Bausparen) der Fall.
     *
     * @return 0 oder Untersparte / Art
     */
	public int getArt() {
	    return this.art;
	}

	/**
	 * Wenn der Datensatz ein Element fuer eine Untersparte hat, wird 'true'
	 * zurueckgegeben. Dies ist z.B. fuer Satz 220.580.1 (Bausparen) der Fall.
	 *
	 * @return true, falls der Datensatz eine Untersparte hat.
	 */
	public boolean hasArt() {
	    return this.art > 0;
	}

	/**
	 * Ueberprueft, ob der Datensatz ueberhaupt eine Sparte gesetzt hat.
	 *
	 * @return true, if successful
	 * @since 0.6
	 */
	@Override
    public boolean hasSparte() {
		if (this.sparte.isEmpty()) {
			return false;
		}
		return this.getSparte() > 0;
	}

	/**
	 * Gets the sparte feld.
	 *
	 * @return die Sparte als Feld
	 */
	public NumFeld getSparteFeld() {
		return this.sparte;
	}

	/**
	 * Sets the vu nummer.
	 *
	 * @param s VU-Nummer (max. 5 Stellen)
	 */
	public void setVuNummer(final String s) {
	    this.getFeld(Feld1bis7.VU_NUMMER).setInhalt(s);
	}

	/**
	 * Gets the vu nummer.
	 *
	 * @return die VU-Nummer
	 */
	public String getVuNummer() {
		return this.getFeld(Feld1bis7.VU_NUMMER).getInhalt().trim();
	}

	/**
	 * Sets the versicherungsschein nummer.
	 *
	 * @param nr die Versicherungsschein-Nummer
	 * @since 0.3
	 */
	public void setVersicherungsscheinNummer(final String nr) {
	    this.getFeld(Feld1bis7.VERSICHERUNGSSCHEINNUMMER).setInhalt(nr);
	}

	/**
	 * Gets the versicherungsschein nummer.
	 *
	 * @return die Versicherungsschein-Nummer
	 * @since 0.3
	 */
	public String getVersicherungsscheinNummer() {
		return this.getFeld(Feld1bis7.VERSICHERUNGSSCHEINNUMMER).getInhalt().trim();
	}

	/**
	 * Gets the wagnisart.
	 *
	 * @return the wagnisart
	 */
	public String getWagnisart() {
		return wagnisart.getInhalt().trim();
	}

	/**
	 * Sets the wagnisart.
	 *
	 * @param wagnisart the new wagnisart
	 */
	public void setWagnisart(final String wagnisart) {
		this.wagnisart.setInhalt(wagnisart);
	}

	/**
	 * Gets the teildatensatz nummer.
	 *
	 * @return the teildatensatz nummer
	 */
	public String getTeildatensatzNummer() {
		return teildatensatzNummer.getInhalt().trim();
	}

	/**
	 * Sets the teildatensatz nummer.
	 *
	 * @param teildatensatzNummer the new teildatensatz nummer
	 */
	public void setTeildatensatzNummer(final String teildatensatzNummer) {
		this.teildatensatzNummer.setInhalt(teildatensatzNummer);
	}

	/**
	 * Hiermit kann die Folgenummer gesetzt werden.
	 *
	 * @param nr man sollte hier bei 1 anfangen mit zaehlen
	 * @since 0.3
	 */
	public void setFolgenummer(final int nr) {
	    this.getFeld(Feld1bis7.FOLGENUMMER).setInhalt(nr);
	}

	/**
	 * Gets the folgenummer.
	 *
	 * @return die Folgenummer
	 * @since 0.3
	 */
	public int getFolgenummer() {
	    NumFeld folgenummer = (NumFeld) this.getFeld(Feld1bis7.FOLGENUMMER);
		return folgenummer.toInt();
	}

	/**
	 * Gets the vermittler.
	 *
	 * @return the vermittler
	 * @since 0.6
	 */
	public String getVermittler() {
		return this.getFeldInhalt(VERMITTLER);
	}

	/**
	 * Liest 14 Bytes, um die Satzart zu bestimmen und stellt die Bytes
	 * anschliessend wieder zurueck in den Reader.
	 *
	 * @param reader muss mind. einen Pushback-Puffer von 14 Zeichen
	 * bereitstellen
	 * @return Satzart
	 * @throws IOException falls was schief gegangen ist
	 */
	public static int readSparte(final PushbackReader reader) throws IOException {
		char[] cbuf = new char[14];
		if (reader.read(cbuf) == -1) {
			throw new IOException("can't read 14 bytes (" + new String(cbuf) + ") from " + reader);
		}
		reader.unread(cbuf);
		String intro = new String(cbuf);
		try {
            return Integer.parseInt(intro.substring(10, 13));
        } catch (NumberFormatException ex) {
            throw new ImportException("cannot read sparte from first 14 bytes (\"" + intro + "\")");
        }
	}

	/**
	 * Liest 1 Byte, um die Wagnisart zu bestimmen und stellt das Byte
	 * anschliessend wieder zurueck in den Reader.
	 *
	 * @param reader muss mind. einen Pushback-Puffer von 1 Zeichen
	 * bereitstellen
	 * @return Wagnisart
	 * @throws IOException falls was schief gegangen ist
	 */
	public static WagnisartLeben readWagnisart(final PushbackReader reader) throws IOException {
		char[] cbuf = new char[60];
		if (reader.read(cbuf) == -1) {
			throw new IOException("can't read 1 bytes (" + new String(cbuf) + ") from " + reader);
		}
		reader.unread(cbuf);
		String wagnisart = new String(cbuf).substring(59, 60);

		if (wagnisart.trim().length() == 0) {
			return WagnisartLeben.NULL;
		} else {
			try {
				return WagnisartLeben.isIn(Integer.parseInt(wagnisart));
			} catch (NumberFormatException e) {
				LOG.warn("Not allowed value for wagnisart found. Type Number is required but was \""
				        + wagnisart + "\".");
				return WagnisartLeben.NULL;
			}
		}
	}

	/**
	 * Unterklassen (wie Datensatz) sind dafuer verantwortlich, dass auch noch
	 * die Sparte ueberprueft wird, ob sie noch richtig ist oder ob da schon der
	 * naechste Satz beginnt.
	 *
	 * @param reader the reader
	 * @return true (Default-Implementierung)
	 * @throws IOException bei I/O-Fehlern
	 * @since 0.5.1
	 * @see Satz#matchesNextTeildatensatz(PushbackLineNumberReader)
	 */
	@Override
	protected boolean matchesNextTeildatensatz(final PushbackLineNumberReader reader) throws IOException {
		if (super.matchesNextTeildatensatz(reader)) {
		    if (this.hasSparte()) {
    			int sparteRead = readSparte(reader);
    			return sparteRead == this.getSparte();
		    } else {
		        return true;
		    }
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see gdv.xport.satz.Satz#toShortString()
	 */
	@Override
	public String toShortString() {
	    StringBuilder buf = new StringBuilder(super.toShortString());
	    if (this.hasSparte()) {
	        buf.append(".");
	        buf.append(this.sparte.getInhalt());
	        if (this.hasArt()) {
	            buf.append(".");
	            buf.append(this.getArt());
	        }
        }
		return buf.toString();
	}

	/**
	 * Read teildatensatz nummer.
	 *
	 * @param reader the reader
	 * @return the teildatensatz nummer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public static TeildatensatzNummer readTeildatensatzNummer(final PushbackReader reader) throws IOException {
        char[] cbuf = new char[256];
        if (reader.read(cbuf) == -1) {
            throw new EOFException("can't read 1 bytes (" + new String(cbuf) + ") from " + reader);
        }
        reader.unread(cbuf);
        String teildatenSatz = new String(cbuf).substring(cbuf.length - 1, cbuf.length);
        if (teildatenSatz.trim().length() == 0) {
            return TeildatensatzNummer.NULL;
        } else {
            try {
                return TeildatensatzNummer.isIn(Integer.parseInt(teildatenSatz));
            } catch (NumberFormatException e) {
                LOG.warn("Value \"" + teildatenSatz + "\" for TeildatensatzNummer found, but Number expected.");
                return TeildatensatzNummer.NULL;
            }
        }
    }

}
