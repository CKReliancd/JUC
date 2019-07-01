package proxy;

public class SteelBoss implements Boss {
    @Override
    public void meet() {
        System.out.println("让钢铁老板见面");
    }

    @Override
    public void consume() {
        System.out.println("让钢铁老板消费");
    }
}
