abstract class Items extends Sprite 
{
    
    /**
     * Method that creates and resets cars on the road strip.
     */
    abstract Sprite setObstacles(int stripYLoc);
    
    /**
     * Method to return random car color.
     */
//     abstract String randomObstacles(String dir);
     public String randomObstacles(String dir) 
     {
         return "Items";
     }
}

