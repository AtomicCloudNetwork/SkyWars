package net.atomiccloud.skywars.game;

public enum GameState
{
    PRE_GAME( "Pre Game", false ),
    LOBBY_COUNTDOWN( "Lobby", false ),
    START_COUNTDOWN( "Warm Up", false ),
    IN_GAME( "In Game", true ),
    DEATH_MATCH( "Death Match", true ),
    POST_GAME( "End Game", false ),
    RESTART( "Server Restart", false );

    private String name;
    private boolean pvp;

    GameState(String name, boolean pvp)
    {
        this.name = name;
        this.pvp = pvp;
    }

    public String getName()
    {
        return name;
    }

    public boolean isPvp()
    {
        return pvp;
    }
}