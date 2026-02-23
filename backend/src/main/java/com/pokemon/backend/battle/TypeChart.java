package com.pokemon.backend.battle;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TypeChart {

    private final Map<String, Map<String, Double>> chart = new HashMap<>();

    public TypeChart() {
        // Normal
        Map<String, Double> normal = new HashMap<>();
        normal.put("rock", 0.5);
        normal.put("ghost", 0.0);
        normal.put("steel", 0.5);
        chart.put("normal", normal);

        // Fire
        Map<String, Double> fire = new HashMap<>();
        fire.put("grass", 2.0);
        fire.put("ice", 2.0);
        fire.put("bug", 2.0);
        fire.put("steel", 2.0);
        fire.put("fire", 0.5);
        fire.put("water", 0.5);
        fire.put("rock", 0.5);
        fire.put("dragon", 0.5);
        chart.put("fire", fire);

        // Water
        Map<String, Double> water = new HashMap<>();
        water.put("fire", 2.0);
        water.put("ground", 2.0);
        water.put("rock", 2.0);
        water.put("water", 0.5);
        water.put("grass", 0.5);
        water.put("dragon", 0.5);
        chart.put("water", water);

        // Electric
        Map<String, Double> electric = new HashMap<>();
        electric.put("water", 2.0);
        electric.put("flying", 2.0);
        electric.put("electric", 0.5);
        electric.put("grass", 0.5);
        electric.put("dragon", 0.5);
        electric.put("ground", 0.0);
        chart.put("electric", electric);

        // Grass
        Map<String, Double> grass = new HashMap<>();
        grass.put("water", 2.0);
        grass.put("ground", 2.0);
        grass.put("rock", 2.0);
        grass.put("fire", 0.5);
        grass.put("grass", 0.5);
        grass.put("poison", 0.5);
        grass.put("flying", 0.5);
        grass.put("bug", 0.5);
        grass.put("dragon", 0.5);
        grass.put("steel", 0.5);
        chart.put("grass", grass);

        // Ice
        Map<String, Double> ice = new HashMap<>();
        ice.put("grass", 2.0);
        ice.put("ground", 2.0);
        ice.put("flying", 2.0);
        ice.put("dragon", 2.0);
        ice.put("fire", 0.5);
        ice.put("water", 0.5);
        ice.put("ice", 0.5);
        ice.put("steel", 0.5);
        chart.put("ice", ice);

        // Fighting
        Map<String, Double> fighting = new HashMap<>();
        fighting.put("normal", 2.0);
        fighting.put("ice", 2.0);
        fighting.put("rock", 2.0);
        fighting.put("dark", 2.0);
        fighting.put("steel", 2.0);
        fighting.put("poison", 0.5);
        fighting.put("flying", 0.5);
        fighting.put("psychic", 0.5);
        fighting.put("bug", 0.5);
        fighting.put("fairy", 0.5);
        fighting.put("ghost", 0.0);
        chart.put("fighting", fighting);

        // Poison
        Map<String, Double> poison = new HashMap<>();
        poison.put("grass", 2.0);
        poison.put("fairy", 2.0);
        poison.put("poison", 0.5);
        poison.put("ground", 0.5);
        poison.put("rock", 0.5);
        poison.put("ghost", 0.5);
        poison.put("steel", 0.0);
        chart.put("poison", poison);

        // Ground
        Map<String, Double> ground = new HashMap<>();
        ground.put("fire", 2.0);
        ground.put("electric", 2.0);
        ground.put("poison", 2.0);
        ground.put("rock", 2.0);
        ground.put("steel", 2.0);
        ground.put("grass", 0.5);
        ground.put("bug", 0.5);
        ground.put("flying", 0.0);
        chart.put("ground", ground);

        // Flying
        Map<String, Double> flying = new HashMap<>();
        flying.put("grass", 2.0);
        flying.put("fighting", 2.0);
        flying.put("bug", 2.0);
        flying.put("electric", 0.5);
        flying.put("rock", 0.5);
        flying.put("steel", 0.5);
        chart.put("flying", flying);

        // Psychic
        Map<String, Double> psychic = new HashMap<>();
        psychic.put("fighting", 2.0);
        psychic.put("poison", 2.0);
        psychic.put("psychic", 0.5);
        psychic.put("steel", 0.5);
        psychic.put("dark", 0.0);
        chart.put("psychic", psychic);

        // Bug
        Map<String, Double> bug = new HashMap<>();
        bug.put("grass", 2.0);
        bug.put("psychic", 2.0);
        bug.put("dark", 2.0);
        bug.put("fire", 0.5);
        bug.put("fighting", 0.5);
        bug.put("poison", 0.5);
        bug.put("flying", 0.5);
        bug.put("ghost", 0.5);
        bug.put("steel", 0.5);
        bug.put("fairy", 0.5);
        chart.put("bug", bug);

        // Rock
        Map<String, Double> rock = new HashMap<>();
        rock.put("fire", 2.0);
        rock.put("ice", 2.0);
        rock.put("flying", 2.0);
        rock.put("bug", 2.0);
        rock.put("fighting", 0.5);
        rock.put("ground", 0.5);
        rock.put("steel", 0.5);
        chart.put("rock", rock);

        // Ghost
        Map<String, Double> ghost = new HashMap<>();
        ghost.put("psychic", 2.0);
        ghost.put("ghost", 2.0);
        ghost.put("dark", 0.5);
        ghost.put("normal", 0.0);
        chart.put("ghost", ghost);

        // Dragon
        Map<String, Double> dragon = new HashMap<>();
        dragon.put("dragon", 2.0);
        dragon.put("steel", 0.5);
        dragon.put("fairy", 0.0);
        chart.put("dragon", dragon);

        // Dark
        Map<String, Double> dark = new HashMap<>();
        dark.put("psychic", 2.0);
        dark.put("ghost", 2.0);
        dark.put("fighting", 0.5);
        dark.put("dark", 0.5);
        dark.put("fairy", 0.5);
        chart.put("dark", dark);

        // Steel
        Map<String, Double> steel = new HashMap<>();
        steel.put("ice", 2.0);
        steel.put("rock", 2.0);
        steel.put("fairy", 2.0);
        steel.put("fire", 0.5);
        steel.put("water", 0.5);
        steel.put("electric", 0.5);
        steel.put("steel", 0.5);
        chart.put("steel", steel);

        // Fairy
        Map<String, Double> fairy = new HashMap<>();
        fairy.put("fighting", 2.0);
        fairy.put("dragon", 2.0);
        fairy.put("dark", 2.0);
        fairy.put("fire", 0.5);
        fairy.put("poison", 0.5);
        fairy.put("steel", 0.5);
        chart.put("fairy", fairy);
    }

    public double effectiveness(String moveType, String targetType) {
        return chart.getOrDefault(moveType.toLowerCase(), Map.of())
                .getOrDefault(targetType.toLowerCase(), 1.0);
    }

    public double stab(String moveType, String attackerType) {
        return moveType.equalsIgnoreCase(attackerType) ? 1.5 : 1.0;
    }
}


