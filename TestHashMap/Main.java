import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static class User {
        String username;
        String password;
        boolean isFirstPurchase;

        User(String username, String password) {
            this.username = username;
            this.password = password;
            this.isFirstPurchase = true;
        }
    }

    static class Drink {
        String name;
        double price;
        int purchaseCount;
        HashMap<String, Integer> toppings;

        Drink(String name, double price) {
            this.name = name;
            this.price = price;
            this.purchaseCount = 0;
            this.toppings = new HashMap<>();
        }

        void addTopping(String topping) {
            toppings.putIfAbsent(topping, 0);
        }

        void addPurchase(String topping) {
            purchaseCount++;
            toppings.put(topping, toppings.getOrDefault(topping, 0) + 1);
        }
    }

    static class CartItem {
        Drink drink;
        String topping;
        double finalPrice;

        CartItem(Drink drink, String topping, double finalPrice) {
            this.drink = drink;
            this.topping = topping;
            this.finalPrice = finalPrice;
        }
    }

    private static HashMap<String, User> usersMap = new HashMap<>();
    private static HashMap<String, Drink> drinksMap = new HashMap<>();
    private static HashMap<Integer, CartItem> cartMap = new HashMap<>();
    private static int cartId = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize drinks menu
        Drink coke = new Drink("Coke", 1.50);
        coke.addTopping("Lemon");
        coke.addTopping("Ice");

        Drink pepsi = new Drink("Pepsi", 1.45);
        pepsi.addTopping("Lemon");
        pepsi.addTopping("Ice");

        Drink sprite = new Drink("Sprite", 1.40);
        sprite.addTopping("Mint");
        sprite.addTopping("Ice");

        Drink fanta = new Drink("Fanta", 1.55);
        fanta.addTopping("Orange Slice");
        fanta.addTopping("Ice");

        drinksMap.put(coke.name, coke);
        drinksMap.put(pepsi.name, pepsi);
        drinksMap.put(sprite.name, sprite);
        drinksMap.put(fanta.name, fanta);

        // Add 1000 users and 1000 cart items for memory calculation
        for (int i = 0; i < 1000; i++) {
            usersMap.put("user" + i, new User("user" + i, "password" + i));
            Drink drink = drinksMap.values().toArray(new Drink[0])[i % drinksMap.size()];
            cartMap.put(cartId++, new CartItem(drink, "Topping" + (i % 2), drink.price + 1.0));
        }

        // Measure memory usage
        long userMemory = calculateUserMemory(usersMap);
        long drinkMemory = calculateDrinkMemory(drinksMap);
        long cartItemMemory = calculateCartItemMemory(cartMap);
        long staticFieldMemory = calculateStaticFieldMemory();

        System.out.println("User HashMap Memory Usage: " + userMemory + " bytes");
        System.out.println("Drink HashMap Memory Usage: " + drinkMemory + " bytes");
        System.out.println("CartItem HashMap Memory Usage: " + cartItemMemory + " bytes");
        System.out.println("Static Field Memory Usage: " + staticFieldMemory + " bytes");
    }

    private static long calculateUserMemory(HashMap<String, User> usersMap) {
        long userMemory = 0;
        for (Map.Entry<String, User> entry : usersMap.entrySet()) {
            String username = entry.getKey();
            User user = entry.getValue();
            userMemory += 24 + 2 * username.length() + 2 * user.username.length() + 2 * user.password.length() + 1; // Object overhead, strings, and boolean
        }
        return userMemory;
    }

    private static long calculateDrinkMemory(HashMap<String, Drink> drinksMap) {
        long drinkMemory = 0;
        for (Drink drink : drinksMap.values()) {
            drinkMemory += 24 + 2 * drink.name.length() + 8 + 4; // Object overhead, string, double, and int
            drinkMemory += 24; // HashMap overhead
            for (Map.Entry<String, Integer> entry : drink.toppings.entrySet()) {
                String topping = entry.getKey();
                drinkMemory += 24 + 2 * topping.length() + 4; // Entry object overhead, string, and integer
            }
        }
        return drinkMemory;
    }

    private static long calculateCartItemMemory(HashMap<Integer, CartItem> cartMap) {
        long cartItemMemory = 0;
        for (CartItem item : cartMap.values()) {
            cartItemMemory += 24 + 8 + 24 + 2 * item.topping.length() + 8; // Object overhead, references, string, and double
        }
        return cartItemMemory;
    }

    private static long calculateStaticFieldMemory() {
        // Memory for static fields
        long staticFieldMemory = 0;
        staticFieldMemory += 24 + 4 * 24; // Object overhead and references to usersMap, drinksMap, cartMap, and scanner
        return staticFieldMemory;
    }
}