package com.pokemon.backend.battle;

import com.pokemon.backend.Move;
import com.pokemon.backend.Pokemon;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DamageCalculator {

    private final TypeChart typeChart;
    private final Random random = new Random();

    public DamageCalculator(TypeChart typeChart) {
        this.typeChart = typeChart;
    }

    public DamageResult calculateDamage(Pokemon attacker, Pokemon defender, Move move) {

        // -------------------------
        // Accuracy check
        // -------------------------
        boolean hit = random.nextDouble() <= move.getAccuracy();
        if (!hit) {
            return new DamageResult(0, 1.0, false, false, false);
        }

        // -------------------------
        // STAB
        // -------------------------
        boolean stab = move.getType().equalsIgnoreCase(attacker.getPrimaryType());
        double stabMultiplier = stab ? 1.5 : 1.0;

        // -------------------------
        // Type effectiveness (dual-type)
        // -------------------------
        double effectiveness = 1.0;
        for (String t : defender.getTypes()) {
            effectiveness *= typeChart.effectiveness(move.getType(), t);
        }

        // -------------------------
        // Critical hit
        // -------------------------
        boolean critical = random.nextDouble() < 0.0625; // 6.25%
        double critMultiplier = critical ? 1.5 : 1.0;

        // -------------------------
        // Base damage formula
        // -------------------------
        int attack = attacker.getAttack();
        int defense = defender.getDefense();
        int power = move.getPower();

        double baseDamage =
                (((2.0 * attacker.getLevel()) / 5.0 + 2) * power * (attack / (double) defense)) / 50.0 + 2;

        double finalDamage = baseDamage * stabMultiplier * effectiveness * critMultiplier;

        int dmg = Math.max(1, (int) finalDamage);

        return new DamageResult(dmg, effectiveness, stab, critical, true);
    }

    public static class DamageResult {
        public final int damage;
        public final double effectiveness;
        public final boolean stab;
        public final boolean critical;
        public final boolean hit;

        public DamageResult(int damage, double effectiveness, boolean stab, boolean critical, boolean hit) {
            this.damage = damage;
            this.effectiveness = effectiveness;
            this.stab = stab;
            this.critical = critical;
            this.hit = hit;
        }
    }
}


