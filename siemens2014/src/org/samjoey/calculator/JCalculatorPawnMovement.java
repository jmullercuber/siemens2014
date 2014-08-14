/*
 * Copyright 2014 Sam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.samjoey.calculator;

import org.samjoey.model.Board;

/**
 *
 * @author Sam
 */
public class JCalculatorPawnMovement
        extends JCalculator {

    public String name = "PawnMovement";

    @Override
    public Double evaluate(Board b) {
        try {
            String m = b.getMove().substring(0, 1);
            boolean t = !(m.contains("Q") || m.contains("K") || m.contains("R") || m.contains("N") || m.contains("B"));
            if (t) {
                return 1.0;
            }
            return 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
}
