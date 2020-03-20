package ru.kalinin.rules;

import ru.kalinin.field.Field;
import ru.kalinin.logic.GameStatus;

public class LoseIfOpenBomb implements RulesOfGame {
    @Override
    public boolean checkCondition(Field field) {
        boolean result = false;
        if (field.getOpenedMines() > 0) {
            result = true;
        }
        return result;
    }
}
