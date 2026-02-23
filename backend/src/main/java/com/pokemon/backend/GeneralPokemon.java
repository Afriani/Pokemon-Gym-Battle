package com.pokemon.backend;

import java.util.List;

public class GeneralPokemon extends Pokemon {

    public GeneralPokemon(
            String name,
            List<String> types,
            int level,
            int maxHp,
            int attack,
            int defense,
            String food,
            String sound,
            String owner,
            String cry
    ) {
        super(name, types, level, maxHp, attack, defense, food, sound, owner, cry);
    }

    @Override
    public void setMoves(List<Move> moves) {
        super.setMoves(moves);
    }
}


