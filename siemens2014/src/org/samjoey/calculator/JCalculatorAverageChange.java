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

import java.util.ArrayList;
import java.util.Arrays;
import org.samjoey.model.Board;

/**
 *
 * @author Sam
 */
public class JCalculatorAverageChange extends JCalculator {

    JCalculator calc;
    Double last1;
    Double last2;
    Double last3;
    Double last4;
    Double last5;
    int count;

    public JCalculatorAverageChange(JCalculator c) {
        super(c.name + "AvgChange");
        calc = c;
        count = 0;
    }

    @Override
    public Double evaluate(Board b) {
        Double data = calc.evaluate(b);
        if (Arrays.equals(b.getAll(), Board.INITIAL_BOARD)) {
            last1 = data;
            last2 = null;
            last3 = null;
            last4 = null;
            last5 = null;
            return 0.0;
        }
        if (last2 == null) {
            last2 = data;
            return last2 - last1;
        }
        if (last3 == null) {
            last3 = data;
            return ((last3 - last2) + (last2 - last1)) / 2;
        }
        if (last4 == null) {
            last4 = data;
            return ((last4 - last3) + (last3 - last2) + (last2 - last1)) / 3;
        }
        if (last5 == null) {
            last5 = data;
            return ((last5 - last4) + (last4 - last3) + (last3 - last2) + (last2 - last1)) / 4;
        }
        last1 = last2;
        last2 = last3;
        last3 = last4;
        last4 = last5;
        last5 = data;
        return ((last5 - last4) + (last4 - last3) + (last3 - last2) + (last2 - last1)) / 4;
    }
}
