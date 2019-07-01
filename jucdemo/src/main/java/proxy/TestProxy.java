package proxy;

public class TestProxy {

    public static void main(String[] args) {
      /*  CoalBossProxy coalBossProxy = new CoalBossProxy();
        coalBossProxy.meet();

        coalBossProxy.consume();

        SteelBossProxy steelBossProxy = new SteelBossProxy();

        steelBossProxy.meet();
        steelBossProxy.consume();*/

        DynaProxyHandler handler = new DynaProxyHandler();

        Boss cb = (Boss) handler.newProxyInstance(new CoalBoss());
        cb.consume();
        cb.meet();

        System.out.println(

        );
        System.out.println();


        Boss sb = (Boss)handler.newProxyInstance(new SteelBoss());
        sb.meet();
        sb.consume();
    }

}
