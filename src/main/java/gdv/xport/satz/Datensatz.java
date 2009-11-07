/*
 * Copyright (c) 2009 by agentes
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
 * (c)reated 12.10.2009 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.satz;

import static gdv.xport.feld.Bezeichner.SPARTE;

import java.io.*;

import gdv.xport.config.Config;
import gdv.xport.feld.*;

/**
 * @author oliver
 * @since 12.10.2009
 */
public class Datensatz extends Satz {
	
	/** 5 Zeichen, Byte 5 - 9 */
	protected VUNummer vuNummer = new VUNummer(Config.getVUNummer(), 5);
	/** 1 Zeichen, Byte 10 */
	protected AlphaNumFeld buendelungsKennzeichen = new AlphaNumFeld(1, 10);
	/** 3 Zeichen, Byte 11 - 13 */
	protected NumFeld sparte = new NumFeld(SPARTE, 3, 11);
	/** 17 Zeichen, Byte 14 - 30 */
	protected AlphaNumFeld versicherungsscheinNr = new AlphaNumFeld(17, 14);
	/** 2 Zeichen, Byte 31 + 32 */
	protected NumFeld folgeNr = new NumFeld(2, 31);
	/** 10 Zeichen, Byte 33 - 42 */
	protected AlphaNumFeld vermittler = new AlphaNumFeld(10, 33);
	
	public Datensatz(String satzart) {
		super(satzart);
		this.setUpTeildatensaetze();
	}
	
	public Datensatz(int satzart) {
		this(satzart, 1);
	}
	
	/**
	 * @param satzart
	 * @param n Anzahl der Teildatensaetze
	 */
	public Datensatz(String satzart, int n) {
		super(satzart, n);
		this.setUpTeildatensaetze();
	}
	
	public Datensatz(int satzart, int n) {
		super(satzart, n);
		this.setUpTeildatensaetze();
	}

	private void setUpTeildatensaetze() {
		for (int i = 0; i < teildatensatz.length; i++) {
			teildatensatz[i].set("VU-Nummer", this.vuNummer);
			teildatensatz[i].set("Buendelungskennzeichen", this.buendelungsKennzeichen);
			teildatensatz[i].set("Sparte", this.sparte);
			teildatensatz[i].set("Versicherungschein-Nummer", this.versicherungsscheinNr);
			teildatensatz[i].set("Folgenummer", this.folgeNr);
			teildatensatz[i].set("Vermittler", this.vermittler);
		}
	}
	
	/* (non-Javadoc)
     * @see gdv.xport.satz.Satz#addFiller()
     */
    @Override
    public void addFiller() {
	    for (int i = 0; i < teildatensatz.length; i++) {
	    	teildatensatz[i].add(new AlphaNumFeld("unknown-" + i, 213, 43));
	    }
    }
   
    public void setSparte(int x) {
    	this.sparte.setInhalt(x);
    }

	public int getSparte() {
		return this.sparte.toInt();
	}
	
	public void setVuNummer(String s) {
		this.vuNummer.setInhalt(s);
	}
	
	public String getVuNummer() {
		return this.vuNummer.getInhalt();
	}
	
	/**
	 * Liest 14 Bytes, um die Satzart zu bestimmen und stellt die Bytes
	 * anschliessend wieder zurueck in den Reader.
	 *
	 * @param reader
	 * @return Satzart
	 * @throws IOException
	 */
	public static int readSparte(PushbackReader reader) throws IOException {
		char[] cbuf = new char[14];
		reader.read(cbuf);
		reader.unread(cbuf);
		return Integer.parseInt(new String(cbuf).substring(10, 13));
	}

}

