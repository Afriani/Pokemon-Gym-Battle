package com.pokemon.backend.battle;

import com.pokemon.backend.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class BattleState {
    private String battleId;
    private Pokemon player;
    private Pokemon opponent;
    private boolean finished = false;

    private List<TurnLog> logs = new ArrayList<>();

    public void addLog(String message) {
        TurnLog log = new TurnLog();
        log.setMessage(message);
        logs.add(log); }

    // Getters and setters
    public String getBattleId() {
        return battleId;
    }

    public void setBattleId(String battleId) {
        this.battleId = battleId;
    }

    public Pokemon getPlayer() {
        return player;
    }

    public void setPlayer(Pokemon player) {
        this.player = player;
    }

    public Pokemon getOpponent() {
        return opponent;
    }

    public void setOpponent(Pokemon opponent) {
        this.opponent = opponent;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<TurnLog> getLogs() {
        return logs;
    }

    public void setLogs(List<TurnLog> logs) {
        this.logs = logs;
    }

}


