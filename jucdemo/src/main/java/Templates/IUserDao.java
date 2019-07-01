package Templates;

public interface IUserDao {

    void save();
}
class UserDao implements IUserDao{

    @Override
    public void save() {
        System.out.println("--------已经保存数据！------");
    }
}
class UserDaoProxy implements IUserDao{

    //接受保存目标对象
    private IUserDao target;
    public UserDaoProxy(IUserDao target){
        this.target = target;
    }
    public void save() {
        System.out.println("开始事务。。。");
        target.save();//执行目标对象方法
        System.out.println("提交事务。。。");
    }
}
class App{
    public static void main(String[] args) {
        //目标对象
        UserDao target = new UserDao();

        //代理对象，把目标对象传给代理对象，建立代理关系
        UserDaoProxy proxy = new UserDaoProxy(target);

        proxy.save();

    }
}
