package interview.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicReference;

public class ReviewAtomicReference {
    public static void main(String[] args) {
        User z3 = new User("z3",18);
        User li4 = new User("li4",28);

        AtomicReference<Object> atomicreference = new AtomicReference<>();
        atomicreference.set(z3);

        System.out.println(atomicreference.compareAndSet(z3, li4)+"\t"+atomicreference.get());
        System.out.println(atomicreference.compareAndSet(z3, li4)+"\t"+atomicreference.toString());



    }
}
@Setter
@Getter
@NoArgsConstructor
class User{
    String userName;
    int age;
    public User(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
