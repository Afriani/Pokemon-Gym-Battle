package com.pokemon.backend.battle;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/battle")
@CrossOrigin(origins = {"http://localhost:5173", "https://pokemon-gym-battle.vercel.app/"})
public class BattleController {

    private final BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @PostMapping("/start")
    public BattleState startBattle(@RequestBody Map<String, Object> payload) throws Exception {
        return battleService.startBattle(payload);
    }


    @PostMapping("/attack")
    public BattleState attack(@RequestBody Map<String, Object> payload) {
        String battleId = (String) payload.get("battleId");
        String moveName = (String) payload.get("move");
        String difficulty = (String) payload.getOrDefault("difficulty", "normal");

        return battleService.playerAttack(battleId, moveName, difficulty);
    }
}
