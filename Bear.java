//Autumn Clark
//CS 2235
//Dr. Kirby
//Ecosystem_HW2_Clark
public class Bear extends Animal {
    //Constructors
    public Bear(){}
    public Bear(int index){
        SetIndex(index);
    }
    //Methods
    //Method to eat a Fish
    public void Eat(Fish fish){
        Ecosystem._river[fish.GetIndex()] = null;
    }
}
