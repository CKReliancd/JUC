package proxy;

public class CoalBossProxy implements Boss {

    private CoalBoss coalBoss;

    public CoalBossProxy(){
        coalBoss = new CoalBoss();
    }

    @Override
    public void meet() {
        System.out.println("代理开始");
        coalBoss.meet();
        System.out.println("代理结束");
    }

    @Override
    public void consume() {

        System.out.println("代理开始");
        coalBoss.consume();
        System.out.println("代理结束");
    }
}
