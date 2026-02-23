package com.pokemon.backend.battle;

import com.pokemon.backend.Move;
import com.pokemon.backend.Pokemon;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Random;

@Component
public class BattleAI {

    private final DamageCalculator damageCalculator;
    private final Random random = new Random();

    public BattleAI(DamageCalculator damageCalculator) {
        this.damageCalculator = damageCalculator;
    }

    public Move chooseMove(Pokemon ai, Pokemon opponent, String difficulty) {

        return switch (difficulty.toLowerCase()) {

            case "easy" -> ai.getMoves().get(random.nextInt(ai.getMoves().size()));

            case "hard" -> ai.getMoves().stream()
                    .max(Comparator.comparingInt(m ->
                            damageCalculator.calculateDamage(ai, opponent, m).damage
                    ))
                    .orElse(ai.getMoves().getFirst());

            default -> random.nextBoolean()
                    ? ai.getMoves().get(random.nextInt(ai.getMoves().size()))
                    : ai.getMoves().stream()
                    .max(Comparator.comparingInt(m ->
                            damageCalculator.calculateDamage(ai, opponent, m).damage
                    ))
                    .orElse(ai.getMoves().getFirst());
        };
    }

}
