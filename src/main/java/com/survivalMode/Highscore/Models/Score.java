package com.survivalMode.Highscore.Models;

import lombok.Getter;
import java.util.Objects;

@Getter
public final class Score {
    public final String username;
    private final int combatLevel;
    private final int totalLevel;
    private final int totalTimePlayed;

    public Score(String user, int combatLevel, int totalLevel, int timePlayed) {
        this.username = user;
        this.combatLevel = combatLevel;
        this.totalLevel = totalLevel;
        this.totalTimePlayed = timePlayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score)) return false;
        Score score = (Score) o;
        return username.equals(score.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Username: "   + username +
           ", Combat Level: " + combatLevel +
           ", Total Level: "  + totalLevel;
    }
}
