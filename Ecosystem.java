import java.util.Random;

//Autumn Clark
//CS 2235
//Dr. Kirby
//Ecosystem_HW2_Clark
public class Ecosystem {
    //Instance variables
    public static Animal[] _river;

    //Method to populate _river ; 33% Fish, 33% Bear, and 33% null
    public static void PopulateRiver(){
        _river = new Animal[500];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < _river.length; i++){
            int x = rand.nextInt(3);
            if (x == 0){
                //Add a Fish
                _river[i] = new Fish(i);
            } else if (x == 1){
                //Add a Bear
                _river[i] = new Bear(i);
            } else {
                //Leave the cell null
            }
        }
    }

    //Method to check if there are any Fish in _river
    public static boolean IsFishInRiver(){
        //Loop through every Animal in _river
        for (Animal nemo:_river) {
            if (nemo != null) { //Ensure null cells don't get checked
                if (nemo.getClass() == Fish.class) {
                    //Then there is a Fish in _river, then the program needs to continue
                    return true;
                }
            }
        }
        //There are no Fish
        return false;
    }

    //Method to get the current count of Animals and null cells
    public static void DisplayCounts(){
        int fishCount = 0;
        int bearCount = 0;
        int emptyCount = 0;
        for (int i = 0; i < _river.length; i++) {
            if (_river[i] != null) { //Exclude null cells to look for Animals
                if (_river[i].getClass() == Fish.class) {
                    fishCount++;
                } else if (_river[i].getClass() == Bear.class && _river[i] != null) {
                    bearCount++;
                }
            } else { //Null get counted here
                emptyCount++;
            }
        }
        System.out.println("Theres " + fishCount + " fish, " + bearCount + " bears, and "
                + emptyCount + " empty cells in the river.");
    }

    //Main code
    public static void main(String[] args){
        int count = 0; //Int to use as a counter
        PopulateRiver();
        DisplayCounts();
        while (IsFishInRiver()){
            for (Animal animal: _river){
                if (animal != null) {
                    animal.Move();
                }
                count++;
                if (count == 100){ //Counts are displayed every 3rd time the foreach loop is completed to shorten output frequency
                DisplayCounts();
                count = 0;
                }
            }
        }
        System.out.println("There are no more Fish. This ecosystem is dead.");
    }
}
