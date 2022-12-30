package runner;

import cart.Cart;

/**
 *
 * Созали интерфейс для работы с разными режимами вывода данных в консоль
 * метод start() - запускает работу для получения данных,
 * если это Interactive mode - даем возможность вводить данные в консоль,
 * если это File mode - парсим файл с командами,
 *
 * метод parseCommandLine() - на основе данных вызываем необходимые комманды объекта корзины (Cart)
 */
public interface ModeRunner {
    void start();
    void parseCommandLine(String line, Cart cart);
}
