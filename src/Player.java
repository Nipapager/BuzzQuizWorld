public class Player {
    private double gamePoints;

    public Player(){
        gamePoints=0;
    }
    public void addGamePoints(double gamePoints){
        this.gamePoints += gamePoints;
    }
    public double getGamePoints(){
        return gamePoints;
    }
}
