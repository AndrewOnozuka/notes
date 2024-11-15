interface Iface {
    public boolean m();
    public int n();
  }
  
  class A implements Iface {
    public String s;
    public String x;
  
    public boolean m() {
      return true;
    }
  
    public int n() {
      return 12;
    }
  }
  
  class B implements Iface {
    public String s;
    public String y;
  
    public boolean m() {
      return false;
    }
  
    public int n() {
      return 2;
    }
    // Lecture Quiz 1 - Q3.1
    Iface i1 = new A();
    // B b = new Iface();
    // A a1 = new B();
    // Iface i2 = new Iface();
    A a2 = new A();

    // Lecture Quiz 1 - Q3.2
    // Iface i1 = new A(); String val1 = i1.s;
    // Iface i2 = new B(); String val2 = i2.x;
    A a1 = new A(); String val3 = a1.s;
    // A a2 = new A(); String val4 = a2.y;
  }

