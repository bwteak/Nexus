package com.juubes.nexus.logic;

import org.bukkit.Bukkit;

import com.juubes.nexus.Nexus;

public class CountdownHandler {
	private int startGame = -1;
	private int changeMap = 0;

	public CountdownHandler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Nexus.getPlugin(), () -> {
			if (Bukkit.getOnlinePlayers().size() == 0)
				return;
			if (changeMap > 0) {
				if (changeMap < 4 || changeMap == 10 || changeMap == 20 || changeMap == 30
						|| changeMap % 60 == 0)
					Bukkit.broadcastMessage("�eVaihdetaan mappia " + changeMap + " sekunnissa.");
			}
			if (startGame > 0) {
				if (startGame < 4 || startGame == 10 || startGame == 20 || startGame == 30
						|| startGame % 60 == 0)
					Bukkit.broadcastMessage("�ePeli alkaa " + startGame + " sekunnissa.");
			}
			if (changeMap > 0) {
				changeMap--;
			} else if (changeMap == 0) {
				GameLogic.loadNextGame();
				changeMap = -1;
			}

			if (startGame > 0) {
				startGame--;
			} else if (startGame == 0) {
				for (Team team : GameLogic.getCurrentGame().getTeams()) {
					if (team.getPlayers().size() == 0) {
						Bukkit.broadcastMessage("�ePeliss� ei ole tarpeeksi pelaajia.");
						startGame = 30;
						return;
					}
				}
				GameLogic.startGame();
				startGame = -1;
			}
		}, 0, 20);
	}

	public void startGameCountdown(int seconds) {
		startGame = seconds;
	}

	public void changeMapCountdown(int seconds) {
		changeMap = seconds;
	}

	public void stopStartGameCountdown() {
		startGame = -1;
	}

	public void stopChangeMapCountdown() {
		changeMap = -1;
	}
}
