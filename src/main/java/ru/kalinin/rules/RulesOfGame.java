package ru.kalinin.rules;

import ru.kalinin.field.Field;
import ru.kalinin.logic.GameStatus;

public interface RulesOfGame {
    boolean checkCondition(Field field, GameStatus gameStatus);
}
