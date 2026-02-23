package com.pokemon.backend.battle;

import com.pokemon.backend.Move;
import com.pokemon.backend.Pokemon;
import org.springframework.stereotype.Component;

@Component
public class BattleEngine {

    private final DamageCalculator damageCalculator;
    private final BattleAI battleAI;
    private final TypeChart typeChart;

    public BattleEngine(DamageCalculator damageCalculator, BattleAI battleAI, TypeChart typeChart) {
        this.damageCalculator = damageCalculator;
        this.battleAI = battleAI;
        this.typeChart = typeChart;
    }

    public void executeTurn(BattleState state, String playerMoveName, String difficulty) {
        Pokemon player = state.getPlayer();
        Pokemon opponent = state.getOpponent();

        // -------------------------
        // Player Turn
        // -------------------------
        Move playerMove = player.getMoves().stream()
                .filter(m -> m.getName().equalsIgnoreCase(playerMoveName))
                .findFirst()
                .orElse(player.getMoves().getFirst());

        DamageCalculator.DamageResult result =
                damageCalculator.calculateDamage(player, opponent, playerMove);

        if (!result.hit) {
            state.addLog(player.getName() + " used " + playerMove.getName() + " but it missed!");
        } else {
            opponent.setCurrentHp(opponent.getCurrentHp() - result.damage);
            state.addLog(player.getName() + " used " + playerMove.getName() + "! (" + result.damage + " dmg)");

            if (result.critical) state.addLog("A critical hit!");
            addEffectivenessMessage(state, result.effectiveness);
            if (result.stab) state.addLog("STAB bonus applied!");
        }

        if (opponent.getCurrentHp() <= 0) {
            state.addLog(opponent.getName() + " fainted! You win!");
            state.setFinished(true);
            return;
        }

        // -------------------------
        // Opponent Turn
        // -------------------------
        Move aiMove = battleAI.chooseMove(opponent, player, difficulty);

        DamageCalculator.DamageResult aiResult =
                damageCalculator.calculateDamage(opponent, player, aiMove);

        if (!aiResult.hit) {
            state.addLog(opponent.getName() + " used " + aiMove.getName() + " but it missed!");
        } else {
            player.setCurrentHp(player.getCurrentHp() - aiResult.damage);
            state.addLog(opponent.getName() + " used " + aiMove.getName() + "! (" + aiResult.damage + " dmg)");

            if (aiResult.critical) state.addLog("A critical hit!");
            addEffectivenessMessage(state, aiResult.effectiveness);
            if (aiResult.stab) state.addLog("STAB bonus applied!");
        }

        if (player.getCurrentHp() <= 0) {
            state.addLog(player.getName() + " fainted! You lose!");
            state.setFinished(true);
        }
    }

    // -------------------------
    // Helpers
    // -------------------------
    private void addEffectivenessMessage(BattleState state, double effectiveness) {
        if (effectiveness == 0.0) {
            state.addLog("It had no effect...");
        } else if (effectiveness >= 2.0) {
            state.addLog("It's super effective!");
        } else if (effectiveness <= 0.5) {
            state.addLog("It's not very effective...");
        }
    }
}
