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
public class JCalculatorChecks extends JCalculator {

    int player;

    public JCalculatorChecks(String white_or_black) {
        super(white_or_black + "Checks");
        player = (white_or_black.equals("Black") ? 1 : 2);
    }

    @Override
    public Double evaluate(Board b) {
        if (b.getPlayer() == this.player) {
            if (b.isOpponentInCheck()) {
                return 1.0;
            }
        }
        return 0.0;
    }
}