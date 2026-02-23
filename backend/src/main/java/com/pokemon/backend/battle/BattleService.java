package com.pokemon.backend.battle;

import com.pokemon.backend.Pokemon;
import com.pokemon.backend.PokemonFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BattleService {

    private final Map<String, BattleState> battles = new ConcurrentHashMap<>();
    private final BattleEngine battleEngine;
    private final PokemonFactory pokemonFactory;

    public BattleService(BattleEngine battleEngine, PokemonFactory pokemonFactory) {
        this.battleEngine = battleEngine;
        this.pokemonFactory = pokemonFactory;
    }

    public BattleState startBattle(Map<String, Object> playerData) throws Exception {
        String name = (String) playerData.getOrDefault("name", "Unknown");
        String type = (String) playerData.getOrDefault("type", "normal");

        Object hpObj = playerData.get("hp");
        int hp = 50;
        if (hpObj instanceof Integer) hp = (Integer) hpObj;
        else if (hpObj instanceof Double) hp = ((Double) hpObj).intValue();

        // Pass type as a List<String>
        Pokemon player = pokemonFactory.fromFrontend(name, Collections.singletonList(type), hp);
        Pokemon opponent = pokemonFactory.randomWildPokemon();

        String battleId = UUID.randomUUID().toString();
        BattleState state = new BattleState();
        state.setBattleId(battleId);
        state.setPlayer(player);
        state.setOpponent(opponent);
        state.addLog("Battle started! " + player.getName() + " vs " + opponent.getName());

        battles.put(battleId, state);
        return state;
    }


    public BattleState playerAttack(String battleId, String moveName, String difficulty) {
        BattleState state = battles.get(battleId);
        if (state == null || state.isFinished()) {
            throw new IllegalStateException("Battle not found or already finished");
        }

        battleEngine.executeTurn(state, moveName, difficulty);

        if (state.isFinished()) {
            battles.remove(battleId);
        }

        return state;
    }
}


