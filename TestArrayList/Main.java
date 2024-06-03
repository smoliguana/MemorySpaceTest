import java.util.ArrayList;
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
        ArrayList<String> toppings;
        ArrayList<Integer> toppingCounts;

        Drink(String name, double price) {
            this.name = name;
            this.price = price;
            this.purchaseCount = 0;
            this.toppings = new ArrayList<>();
            this.toppingCounts = new ArrayList<>();
        }

        void addTopping(String topping) {
            if (!toppings.contains(topping)) {
                toppings.add(topping);
                toppingCounts.add(0);
            }
        }

        void addPurchase(String topping) {
            purchaseCount++;
            int index = toppings.indexOf(topping);
            if (index != -1) {
                toppingCounts.set(index, toppingCounts.get(index) + 1);
            }
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

    private static ArrayList<User> usersList = new ArrayList<>();
    private static ArrayList<Drink> drinksList = new ArrayList<>();
    private static ArrayList<CartItem> cartList = new ArrayList<>();
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

        drinksList.add(coke);
        drinksList.add(pepsi);
        drinksList.add(sprite);
        drinksList.add(fanta);

        // Add 1000 users and 1000 cart items for memory calculation
        for (int i = 0; i < 1000; i++) {
            usersList.add(new User("user" + i, "password" + i));
            Drink drink = drinksList.get(i % drinksList.size());
            cartList.add(new CartItem(drink, "Topping" + (i % 2), drink.price + 1.0));
        }

        // Measure memory usage
        long userMemory = calculateUserMemory(usersList);
        long drinkMemory = calculateDrinkMemory(drinksList);
        long cartItemMemory = calculateCartItemMemory(cartList);
        long staticFieldMemory = calculateStaticFieldMemory();

        System.out.println("User ArrayList Memory Usage: " + userMemory + " bytes");
        System.out.println("Drink ArrayList Memory Usage: " + drinkMemory + " bytes");
        System.out.println("CartItem ArrayList Memory Usage: " + cartItemMemory + " bytes");
        System.out.println("Static Field Memory Usage: " + staticFieldMemory + " bytes");
    }

    private static long calculateUserMemory(ArrayList<User> usersList) {
        long userMemory = 0;
        for (User user : usersList) {
            userMemory += 24 + user.username.length() * 2 + user.password.length() * 2 + 1; // Object overhead, strings, and boolean
        }
        return userMemory;
    }

    private static long calculateDrinkMemory(ArrayList<Drink> drinksList) {
        long drinkMemory = 0;
        for (Drink drink : drinksList) {
            drinkMemory += 24 + drink.name.length() * 2 + 8 + 4; // Object overhead, string, double, and int
            drinkMemory += 24 + drink.toppings.size() * 16; // ArrayList overhead and elements
            drinkMemory += 24 + drink.toppingCounts.size() * 4; // ArrayList overhead and elements
        }
        return drinkMemory;
    }

    private static long calculateCartItemMemory(ArrayList<CartItem> cartList) {
        long cartItemMemory = 0;
        for (CartItem item : cartList) {
            cartItemMemory += 24 + 8 + 24 + item.topping.length() * 2 + 8; // Object overhead, references, string, and double
        }
        return cartItemMemory;
    }

    private static long calculateStaticFieldMemory() {
        // Memory for static fields
        long staticFieldMemory = 0;
        staticFieldMemory += 24 + 4 * 24; // Object overhead and references to usersList, drinksList, cartList, and scanner
        return staticFieldMemory;
    }
}