public class Stringmani {
    void main (){
        String s = "  Hello Java  ";
        System.out.println(s.trim());
        int len =  s.length();
        char ch = s.charAt(len-1);
        String sub1 = s.substring(2);
        String sub2 = s.substring(2,7);
        boolean same = "java".equals("java");
        int i1 = s.indexOf("Java");
        int i2 = s.lastIndexOf("a");
        boolean same2 = "java".equalsIgnoreCase("JAVA");
        int cmp = "apple".compareTo("banana");
        String upper = s.toUpperCase();
        boolean hasJava = s.contains("Java");
        String r = "banana".replace('a', 'o');
    }

}
