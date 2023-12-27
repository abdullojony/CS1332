public class fibonacciDP {
    
    public static void main(String[] args) {
        System.out.println(fibonacci(Integer.parseInt(args[0])));
    }

    public static int fibonacci(int n) {
        int[] array = new int[n + 1];
        array[0] = 0;
        array[1] = 1;
        for (int i = 2; i <= n; i++) {
            array[i] = array[i - 1] + array[i - 2];
        }
        return array[n];
    }

}
