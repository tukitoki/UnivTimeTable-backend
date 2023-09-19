public class Main {
    public static void main(String[] args) {
        System.out.println(method1() & method2());
    }

    static boolean method1() {
        System.out.print("method1 ");
        return false;
    }

    static boolean method2() {
        System.out.print("method2 ");
        return true;

    }
}