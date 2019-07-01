package proxy;

public class SteelBossProxy implements Boss {
    private SteelBoss steelBoss;
    public SteelBossProxy(){
        steelBoss = new SteelBoss();
    }

    @Override
    public void meet() {
        System.out.println("代理开始");

        steelBoss.meet();
        System.out.println("代理结束");
    }

    @Override
    public void consume() {
        System.out.println("代理开始");

        steelBoss.consume();
        System.out.println("代理结束");
    }
}
