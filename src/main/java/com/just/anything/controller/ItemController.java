package com.just.anything.controller;

import com.just.anything.item.Book;
import com.just.anything.item.Item;
import com.just.anything.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Item book = Book.createBook(form.getName(),form.getPrice(),form.getStockQuantity(),form.getAuthor(),form.getIsbn());
        itemService.saveItem(book);
        return "redirect:/";
    }
}
