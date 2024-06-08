package lib.managers;


/**менеджер для вывода результатов и ошибок*/
public class OutputManager {
    public OutputManager() {
    }
    public void print(String P) {
        System.out.print(P);
    }
    public void println(String P){
        System.out.println(P);
    }
    public void printerr(String P) {
        System.err.print(P);
    }
}
