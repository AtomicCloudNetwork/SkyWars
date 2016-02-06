package net.atomiccloud.skywars.game;

public enum GameState
{
    PRE_GAME( "Pre Game" ),
    LOBBY_COUNTDOWN( "Lobby" ),
    START_COUNTDOWN( "Warm Up" ),
    IN_GAME( "In Game" ),
    DEATH_MATCH( "Death Match" ),
    POST_GAME( "End Game" ),
    RESTART( "Server Restart" );

    private String name;

    GameState(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public boolean isPvp()
    {
        switch ( this )
        {
            case IN_GAME:
            case DEATH_MATCH:
                return true;
            default:
                return false;
        }
    }
}