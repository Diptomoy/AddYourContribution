class Main {
  public static void main(String[] args) {

    int n = 10, first = 0, second = 1;
    System.out.println("Fibonacci Series till " + n + " terms:");

    for (int i = 1;i<=n; ++i) {
      System.out.print(first + ", ");

      
      int nextterm = first + second;
      first = second;
      second = nextterm;
    }
  }
}
