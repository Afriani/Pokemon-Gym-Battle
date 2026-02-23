package com.pokemon.backend;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PokemonFactory {

    private final Random random = new Random();

    // -------------------------------
    // 1. Create Pokémon from frontend
    // -------------------------------
    public Pokemon fromFrontend(String name, List<String> types, int maxHp) {
        int level = 50;

        int attack = 80;
        int defense = 80;

        Pokemon p = new GeneralPokemon(
                name,
                types,
                level,
                maxHp,     // ✔ correct max HP
                attack,
                defense,
                "berries",
                "sound",
                "Player",
                "cry"
        );

        p.setMoves(defaultMovesForType(types.get(0)));
        return p;
    }


    // -------------------------------
    // 2. Create Pokémon from PokeAPI
    // -------------------------------
    public Pokemon fromPokeApi(JsonNode json) {

        String name = json.get("name").asText();

        // Extract ALL types (1 or 2)
        List<String> types = new ArrayList<>();
        json.get("types").forEach(t ->
                types.add(t.get("type").get("name").asText())
        );

        int baseHp = json.get("stats").get(0).get("base_stat").asInt();
        int baseAtk = json.get("stats").get(1).get("base_stat").asInt();
        int baseDef = json.get("stats").get(2).get("base_stat").asInt();

        int level = 50;
        int maxHp = calcHp(baseHp, level);
        int attack = calcStat(baseAtk, level);
        int defense = calcStat(baseDef, level);

        Pokemon p = new GeneralPokemon(
                name, types, level, maxHp, attack, defense,
                "berries", "sound", "Wild", "cry"
        );

        // Assign moves based on PRIMARY type
        String primaryType = types.get(0);
        p.setMoves(defaultMovesForType(primaryType));

        return p;
    }

    // -------------------------------
    // 3. Random wild Pokémon generator
    // -------------------------------
    public Pokemon randomWildPokemon() throws Exception {
        int randomId = random.nextInt(151) + 1; // Gen 1 for now
        String url = "https://pokeapi.co/api/v2/pokemon/" + randomId;

        JsonNode json = new com.fasterxml.jackson.databind.ObjectMapper()
                .readTree(new java.net.URL(url));

        return fromPokeApi(json);
    }

    // -------------------------------
    // 4. Stat formulas
    // -------------------------------
    private int calcHp(int base, int level) {
        return (int) (((2.0 * base) * level) / 100.0) + level + 10;
    }

    private int calcStat(int base, int level) {
        return (int) (((2.0 * base) * level) / 100.0) + 5;
    }

    // -------------------------------
    // 5. Full move list for all types
    // -------------------------------
    private List<Move> defaultMovesForType(String type) {
        List<Move> moves = new ArrayList<>();

        switch (type.toLowerCase()) {

            case "fire" -> {
                moves.add(new Move("Flamethrower", "fire", 90, 1.0, false));
                moves.add(new Move("Fire Blast", "fire", 110, 0.85, false));
            }

            case "water" -> {
                moves.add(new Move("Surf", "water", 90, 1.0, false));
                moves.add(new Move("Hydro Pump", "water", 110, 0.8, false));
            }

            case "grass" -> {
                moves.add(new Move("Leaf Blade", "grass", 90, 1.0, true));
                moves.add(new Move("Leaf Storm", "grass", 130, 0.9, false));
            }

            case "electric" -> {
                moves.add(new Move("Thunderbolt", "electric", 90, 1.0, false));
                moves.add(new Move("Thunder", "electric", 110, 0.7, false));
            }

            case "rock" -> {
                moves.add(new Move("Rock Slide", "rock", 75, 0.9, true));
                moves.add(new Move("Stone Edge", "rock", 100, 0.8, true));
            }

            case "ground" -> {
                moves.add(new Move("Earthquake", "ground", 100, 1.0, true));
                moves.add(new Move("Bulldoze", "ground", 60, 1.0, true));
            }

            case "ice" -> {
                moves.add(new Move("Ice Beam", "ice", 90, 1.0, false));
                moves.add(new Move("Blizzard", "ice", 110, 0.7, false));
            }

            case "dragon" -> {
                moves.add(new Move("Dragon Claw", "dragon", 80, 1.0, true));
                moves.add(new Move("Outrage", "dragon", 120, 1.0, true));
            }

            case "fairy" -> {
                moves.add(new Move("Moonblast", "fairy", 95, 1.0, false));
                moves.add(new Move("Dazzling Gleam", "fairy", 80, 1.0, false));
            }

            case "flying" -> {
                moves.add(new Move("Air Slash", "flying", 75, 0.95, false));
                moves.add(new Move("Hurricane", "flying", 110, 0.7, false));
            }

            case "bug" -> {
                moves.add(new Move("X-Scissor", "bug", 80, 1.0, true));
                moves.add(new Move("Bug Buzz", "bug", 90, 1.0, false));
            }

            case "poison" -> {
                moves.add(new Move("Sludge Bomb", "poison", 90, 1.0, false));
                moves.add(new Move("Gunk Shot", "poison", 120, 0.8, true));
            }

            case "psychic" -> {
                moves.add(new Move("Psychic", "psychic", 90, 1.0, false));
                moves.add(new Move("Psyshock", "psychic", 80, 1.0, false));
            }

            case "ghost" -> {
                moves.add(new Move("Shadow Ball", "ghost", 80, 1.0, false));
                moves.add(new Move("Shadow Claw", "ghost", 70, 1.0, true));
            }

            case "steel" -> {
                moves.add(new Move("Iron Head", "steel", 80, 1.0, true));
                moves.add(new Move("Flash Cannon", "steel", 80, 1.0, false));
            }

            case "dark" -> {
                moves.add(new Move("Crunch", "dark", 80, 1.0, true));
                moves.add(new Move("Dark Pulse", "dark", 80, 1.0, false));
            }

            case "fighting" -> {
                moves.add(new Move("Close Combat", "fighting", 120, 1.0, true));
                moves.add(new Move("Brick Break", "fighting", 75, 1.0, true));
            }

            default -> {
                moves.add(new Move("Body Slam", "normal", 85, 1.0, true));
                moves.add(new Move("Hyper Beam", "normal", 150, 0.9, false));
            }
        }

        return moves;
    }
}


