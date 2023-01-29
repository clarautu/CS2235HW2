import java.util.Random;

//Autumn Clark
//CS 2235
//Dr. Kirby
//Ecosystem_HW2_Clark

public abstract class Animal {
    //Instance variables
    private int _index;
    //Methods
    public int GetIndex() {
        return _index;
    }
    public void SetIndex(int index){
        if (index >= 0 && index < Ecosystem._river.length){ //Checks if index is within the bounds of _river
        _index = index;
        }
    }

    //Method to place new Animal in a random nearby null cell ; Uses Animal's index to find a nearby null cell
    public int FindNewCell(int index){
        for (int i = 0; i < Ecosystem._river.length; i++){
            if (Ecosystem._river[i] == null){
                return i;
            }
        }
        //All Fish should be gone before all empty cells are gone, so this should be unreachable code
        return -1;
    }

    //Method to create a new animal in a random nearby null cell based on what kind of Animal is reproducing
    // Calls FindRandomCell ; Takes Animal's index to find a nearby null cell
    public Animal Reproduce(int index) {
        if (this.getClass() == Bear.class){
            return new Bear(FindNewCell(index));
        } else {
            return new Fish(FindNewCell(index));
        }
    }

    //Method to check if the Animal will collide with another Animal
    public boolean DidICollide(boolean isGoingRight) {
        if(isGoingRight){
            if (Ecosystem._river[_index + 1] != null){
                return true;
            }
        } else {
            if (Ecosystem._river[_index - 1] != null){
                return true;
            }
        }
        return false;
    }

    //Method to move the animal ; Calls DidICollide, Reproduce, and Eat
    public void Move() {
        boolean didICollide; //Boolean to track if the Animal has collided with another Animal
        boolean didIMoveRight; //Boolean to track the direction the Animal moves
        //Check to see if the Animal is on the either edge of the river
        if (this._index == 0){
            didICollide = DidICollide(true); //On left edge, go right
            didIMoveRight = true;
        } else if (this._index == 499){
            didICollide = DidICollide(false); //On right edge, go left
            didIMoveRight = false;
        } else {
            //If not on a edge, randomly assign a direction
            Random rand = new Random(System.currentTimeMillis());
            int x = rand.nextInt(2);
            //Half the time go right, half the time go left
            if (x == 0){
                didICollide = DidICollide(true);
                didIMoveRight = true;
                } else {
                didICollide = DidICollide(false);
                didIMoveRight = false;
            }
        }
        if (didICollide){
            //Check if the Animal collided with is the same type
            boolean isSameType = false; //Boolean to track if the Animal has collided with another Animal of the same type
            if (didIMoveRight){
                if (Ecosystem._river[this.GetIndex() + 1] != null) { //Check if cell to the right is null first
                    if (Ecosystem._river[this.GetIndex() + 1].getClass() == this.getClass()) {
                        isSameType = true;
                    }
                }
            } else if (!didIMoveRight){
                if (Ecosystem._river[this.GetIndex() - 1] != null) {
                    if (Ecosystem._river[this.GetIndex() - 1].getClass() == this.getClass()) {
                        isSameType = true;
                    }
                }
            }
            //If the collided Animal is the same type, call Reproduce
            if (isSameType) {
                Animal animal = this.Reproduce(this.GetIndex());
            } else {
                //Call Eat method to remove the Fish
                if (this.getClass() == Bear.class){ //Check if this Animal is a Bear
                    if (didIMoveRight){ //Fish is to the right
                        ((Bear) this).Eat((Fish)Ecosystem._river[this.GetIndex() + 1]);
                    } else { //Fish is to the left
                        ((Bear) this).Eat((Fish)Ecosystem._river[this.GetIndex() - 1]);
                    }
                } else { //Am Fish, so call Bear's Eat method
                    if (didIMoveRight){ //Bear is to the right
                        ((Bear)Ecosystem._river[this.GetIndex() + 1]).Eat((Fish)this);
                    } else { //Bear is to the left
                        ((Bear)Ecosystem._river[this.GetIndex() - 1]).Eat((Fish)this);
                    }
                }
            }
        } else { //No collision, actually move the Animal
            if (didIMoveRight){
                Ecosystem._river[this.GetIndex() + 1] = Ecosystem._river[this.GetIndex()];
                Ecosystem._river[this.GetIndex()] = null;
                this.SetIndex(this.GetIndex() + 1);
            } else {
                Ecosystem._river[this.GetIndex() - 1] = Ecosystem._river[this.GetIndex()];
                Ecosystem._river[this.GetIndex()] = null;
                this.SetIndex(this.GetIndex() - 1);
            }
        }
    }
}
