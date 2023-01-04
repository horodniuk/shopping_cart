package runner;

import cart.Cart;

/*
 * Интерфейс для работы с разными режимами вывода данных в консоль
 * метод start() - запуск считывания и выполнения комманд в зависимости от режима
 * Например:
 * если это Interactive mode - считываем команды с консоли
 * если это File mode - считываем команды с файла
 *
 * метод excecuteCommand() - на основе считываемых данных выполняет комманды (объекта корзины Cart)
 */
public interface ModeRunner {
    void start();
    void excecuteCommand(String line, Cart cart);
}
