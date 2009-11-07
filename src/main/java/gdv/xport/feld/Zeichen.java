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
 * (c)reated 25.10.2009 by Oli B. (oliver.boehm@agentes.de)
 */

package gdv.xport.feld;

/**
 * Ein Zeichen ist ein Feld der Laenge 1.
 *
 * @author oliver
 * @since 0.0.2
 */
public class Zeichen extends AlphaNumFeld {

    public Zeichen(int start, char c) {
        super(1, start);
        super.setInhalt(c);
    }

    public Zeichen(String name, int start) {
        super(name, 1, start);
    }

    public Zeichen(String name, int start, char c) {
        super(name, 1, start, c);
    }

}
