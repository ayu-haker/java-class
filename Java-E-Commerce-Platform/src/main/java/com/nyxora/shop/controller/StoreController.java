package com.nyxora.shop.controller;

import com.nyxora.shop.model.Product;
import com.nyxora.shop.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class StoreController {
    private final ProductRepository products;

    public StoreController(ProductRepository products) {
        this.products = products;
    }

    @ModelAttribute
    public void shared(Model model, HttpSession session) {
        model.addAttribute("cartCount", cart(session).values()
            .stream().mapToInt(Integer::intValue).sum());
    }

    @SuppressWarnings("unchecked")
    private Map<Long, Integer> cart(HttpSession session) {
        Map<Long, Integer> cart =
            (Map<Long, Integer>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String q, Model model) {
        List<Product> all = products.findAll();
        if (q != null && !q.isBlank()) {
            String query = q.toLowerCase(Locale.ROOT);
            all = all.stream().filter(p ->
                p.getName().toLowerCase(Locale.ROOT).contains(query)
                || p.getCategory().toLowerCase(Locale.ROOT).contains(query)
            ).toList();
        }
        model.addAttribute("products", all);
        model.addAttribute("query", q == null ? "" : q);
        return "index";
    }

    @PostMapping("/cart/add/{id}")
    public String add(@PathVariable Long id, HttpSession session,
                      RedirectAttributes flash) {
        Optional<Product> found = products.findById(id);
        if (found.isEmpty()) {
            flash.addFlashAttribute("notice", "Product not found.");
        } else {
            Product p = found.get();
            Map<Long, Integer> cart = cart(session);
            int quantity = cart.getOrDefault(id, 0);
            if (quantity < p.getStock()) {
                cart.put(id, quantity + 1);
                flash.addFlashAttribute("notice", p.getName() + " added to cart.");
            } else {
                flash.addFlashAttribute("notice", "No more stock available.");
            }
        }
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        List<Map<String, Object>> lines = new ArrayList<>();
        double total = 0;
        Map<Long, Integer> cart = cart(session);

        for (var entry : cart.entrySet()) {
            products.findById(entry.getKey()).ifPresent(p -> {
                Map<String, Object> line = new HashMap<>();
                line.put("product", p);
                line.put("quantity", entry.getValue());
                line.put("subtotal", p.getPrice() * entry.getValue());
                lines.add(line);
            });
        }
        for (Map<String, Object> line : lines) {
            total += (double) line.get("subtotal");
        }
        model.addAttribute("lines", lines);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {
        cart(session).remove(id);
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session, RedirectAttributes flash) {
        Map<Long, Integer> cart = cart(session);
        if (cart.isEmpty()) {
            flash.addFlashAttribute("notice", "Your cart is empty.");
            return "redirect:/cart";
        }

        List<Map<String, Object>> purchased = new ArrayList<>();
        double total = 0;

        for (var entry : cart.entrySet()) {
            Optional<Product> found = products.findById(entry.getKey());
            if (found.isEmpty() || entry.getValue() > found.get().getStock()) {
                flash.addFlashAttribute("notice",
                    "Stock changed. Please review your cart and try again.");
                return "redirect:/cart";
            }
        }

        for (var entry : cart.entrySet()) {
            Product p = products.findById(entry.getKey()).orElseThrow();
            int quantity = entry.getValue();
            p.setStock(p.getStock() - quantity);
            products.save(p);

            Map<String, Object> line = new HashMap<>();
            line.put("name", p.getName());
            line.put("quantity", quantity);
            line.put("subtotal", p.getPrice() * quantity);
            purchased.add(line);
            total += p.getPrice() * quantity;
        }

        List<Map<String, Object>> orders = orderHistory(session);
        Map<String, Object> order = new HashMap<>();
        order.put("number", UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.put("items", purchased);
        order.put("total", total);
        order.put("date", new Date());
        orders.add(0, order);

        cart.clear();
        return "redirect:/orders";
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> orderHistory(HttpSession session) {
        List<Map<String, Object>> orders =
            (List<Map<String, Object>>) session.getAttribute("orders");
        if (orders == null) {
            orders = new ArrayList<>();
            session.setAttribute("orders", orders);
        }
        return orders;
    }

    @GetMapping("/orders")
    public String orders(HttpSession session, Model model) {
        model.addAttribute("orders", orderHistory(session));
        return "orders";
    }
}
