package mainB.Entities;

public class SomeEntity {
    int IP = 123;

    @Override
    public String toString() {
        return "SomeEntity{" +
                "IP=" + IP +
                ", value='" + value + '\'' +
                '}';
    }

    String value = "asd";
}
