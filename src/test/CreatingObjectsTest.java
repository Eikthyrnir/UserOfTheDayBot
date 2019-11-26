import java.lang.reflect.Constructor;

public class CreatingObjectsTest {


    private static interface MyClassInterface {
        void foo();
    }

    private static class MyClassParentClass {

        private int a;

        public MyClassParentClass() {
            System.out.println("parent ctor");
        }

        public void bar() {
            System.out.println("parent bar");
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getA() {
            return a;
        }
    }

    private static class MyClass
            extends MyClassParentClass
            implements MyClassInterface {


        private int a;


        public MyClass() {
            System.out.println("ctor without args");
        }

        public MyClass(int value) {
            this.a = value;
            super.setA(this.a + 1);

            System.out.println("ctor with my int: " + a + " and parent value = " + super.getA());
        }

        @Override
        public void foo() {
            System.out.println("derived foo");
        }

        @Override
        public void bar() {
            System.out.println("derived bar");
        }
    }


    public static void main(String[] args) {

        try {

            Class<?> c = MyClass.class;
            Constructor<?> instance3ctor = c.getConstructor(int.class);
            MyClassParentClass myClassParentClass = (MyClassParentClass) instance3ctor.newInstance(1);

            myClassParentClass.bar();
            System.out.println(myClassParentClass.getA());

            System.out.println();

            MyClassInterface myClassInterface = (MyClassInterface) instance3ctor.newInstance(2);
            myClassInterface.foo();
        } catch(Exception e) {
            //...
        }

    }

}