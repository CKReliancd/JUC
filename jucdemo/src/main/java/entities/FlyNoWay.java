package entities;

public class FlyNoWay implements FlyBehavior {

    @Override
    public void Fly() {
        System.out.println("I can't fly!!");
    }
}
