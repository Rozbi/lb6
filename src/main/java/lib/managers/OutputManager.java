package lib.managers;


import lib.utility.Message;

import java.util.ArrayList;
import java.util.List;

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
    public void prettyPrint(Message message) {
        switch (message.getName()) {
            case "help": {
                String[] help = message.getEntity().toString().split(",");
                for (int i = 0; i < help.length; i++) {
                    System.out.println(help[i]);
                }
                break;
            }
            case "info": {
                String[] info = message.getEntity().toString().split(" ");
                System.out.println("Тип коллекции: " + info[0] + "\n" + "Количество элементов: " + info[1] + "\n" + "Время загрузки " + info[2] + "\n" + "Время сохранения " + info[3]);
                break;
            }
            case "group_counting_by_health":{
                System.out.println("health: count");
                System.out.println(message.getEntity());
            }
            default: {
                System.out.println(message.getEntity().toString());
            }
        }
    }
}
