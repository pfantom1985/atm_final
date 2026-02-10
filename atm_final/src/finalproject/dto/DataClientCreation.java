package finalproject.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DataClientCreation {

    private final Map<String, Client> clients = new HashMap<>();

    /**
     * Возвращаем "read-only" view, чтобы внешний код не мог случайно сломать внутреннее хранилище.
     * Изменения выполняются через методы add/put/remove. [web:37]
     */
    public Map<String, Client> getDataClient() {
        return Collections.unmodifiableMap(clients);
    }

    public boolean containsCard(String cardNumber) {
        return clients.containsKey(cardNumber);
    }

    public Client getClient(String cardNumber) {
        return clients.get(cardNumber);
    }

    public void addDataClient(String keyClient, Client client) {
        clients.put(keyClient, client);
    }

    public void putAll(Map<String, Client> other) {
        if (other == null || other.isEmpty()) {
            return;
        }
        clients.putAll(other);
    }

    public Client removeClient(String cardNumber) {
        return clients.remove(cardNumber);
    }
}
