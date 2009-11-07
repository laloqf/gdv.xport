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
 * (c)reated 11.10.2009 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.feld;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author oliver
 * @since 11.10.2009
 * @version $Revision$
 *
 */
public class BetragTest {

	private Betrag betrag = new Betrag(5, 1);

	/**
	 * Test method for {@link gdv.xport.feld.Betrag#Betrag(int, int)}.
	 */
	@Test
	public void testBetrag() {
		assertEquals("00000", betrag.getInhalt());
		assertEquals(0.0, betrag.toDouble(), 0.001);
	}
	
	@Test
	public void testSetInhaltInt() {
		betrag.setInhalt(50);
		assertEquals("05000", betrag.getInhalt());
		assertEquals(50, betrag.toInt());
	}
	
	@Test
	public void testSetInhaltDouble() {
		betrag.setInhalt(1.23);
		assertEquals("00123", betrag.getInhalt());
		assertEquals(1.23, betrag.toDouble(), 0.001);
	}

}

