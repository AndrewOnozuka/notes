class Q3 {
    A a1 = new A();
    boolean val1 = a1.m();
  
    Iface i1 = new B();
    int val2 = i1.n();
  
    Iface i2 = a1;
    int val3 = i2.n();
  
    public static void main(String[] args){
      Q3 q = new Q3();
      System.out.println(q.val1 + ", " + q.val2 + ", " + q.val3);
    }
  }