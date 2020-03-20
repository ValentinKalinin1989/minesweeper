package ru.kalinin.rules;

import ru.kalinin.field.Field;

public class WinIfOpenAllField implements RulesOfGame {
    @Override
    public boolean checkCondition(Field field) {
        boolean result = false;
        if (field.getNumberAllCells() - field.getOpenedCells() == field.getAllMines()) {
            result = true;
        }
        return result;
    }
}
