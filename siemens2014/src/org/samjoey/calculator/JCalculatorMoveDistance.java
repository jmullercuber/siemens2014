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

import java.util.Arrays;
import org.samjoey.model.Board;

/**
 *
 * @author Sam
 */
public class JCalculatorMoveDistance extends JCalculator {

    private Board last;

    public JCalculatorMoveDistance() {
        super("MoveDistance");
    }

    @Override
    public Double evaluate(Board b) {
        if (last == null) {
            last = b;
            return 0.0;
        } else if (Arrays.equals(b.getAll(), Board.INITIAL_BOARD)) {
            last = b;
            return 0.0;
        } else {
            int x1 = -1;
            int x2 = -1;
            int y1 = -1;
            int y2 = -1;
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    if (!last.getAll()[y][x].equals(b.getAll()[y][x])) {
                        if (x1 == -1) {
                            x1 = x;
                            y1 = y;
                        } else {
                            x2 = x;
                            y2 = y;
                        }
                    }
                }
            }
            last = b;
            return Math.pow(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2), .5);
        }
    }
}
