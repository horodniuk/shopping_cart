package runner;

import cart.Cart;
import storage.StorageWithJson;

import java.util.Scanner;

public class InteractiveModeRunner implements ModeRunner {
    private String pathToStorage;

    public InteractiveModeRunner(String pathToStorage) {
        this.pathToStorage = pathToStorage;
    }

    /**
     * Задача
     * Нужно реализовать метод parseCommandLine который в зависимости от комманды будет
     * работать с корзиной и выводить реузьтаты в консоль
     */
    public void start() {
        System.out.println("Запускаем Interactive mode");
        Cart cart = new Cart(new StorageWithJson(pathToStorage));
        while (true){
            String commandStr = new Scanner(System.in).nextLine();
            if(commandStr.equals("finish")) return;
            parseCommandLine(commandStr, cart);
        }
    }

    /**
     *
     * Сейчас прописан код в котором данные указаны напрямую
     * Это для демонстрации работы
     * Нужно прописать метод что он работал со всеми командами которые указаны в тех. задании
     * Например: Если навход подается строка "add bear 5" нужно распарсить для получения
     * названия продукта"bear" и кол-ва "5" и проверить
     * есть ли такой продукт,
     * есть ли его достаточное кол-во и после выполнить команду, например  cart.add("bear", 5)
     */
    @Override
    public void parseCommandLine(String line, Cart cart) {
        if (line.equals("add bear 5")){
            cart.add("bear", 5);
            return;
        }
        if (line.equals("price")){
            cart.price();
        } else{
            System.out.println("неизвесная команда, попробуйте еще раз, например \"add bear 5\"");
        }
    }

}
