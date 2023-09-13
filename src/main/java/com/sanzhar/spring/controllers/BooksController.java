package com.sanzhar.spring.controllers;


import com.sanzhar.spring.models.Book;

import com.sanzhar.spring.models.Person;
import com.sanzhar.spring.services.BooksService;

import com.sanzhar.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final PeopleService peopleService;
    private final BooksService booksService;


   @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
       this.peopleService = peopleService;
       this.booksService = booksService;
   }

    @GetMapping()
    public String indexPage(@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
                            @RequestParam(value = "book_per_page", required = false, defaultValue = "-1") int booksPerPage,
                            @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") boolean sort_by_year,
                            Model model){
       if(page == -1 && booksPerPage == -1){
           model.addAttribute("books",booksService.getBooks());
       }
       else if(page >= 0 && booksPerPage >= 0 && sort_by_year){
           model.addAttribute("books", booksService.sortAndPage(page,booksPerPage));
       }
       else{
           model.addAttribute("books", booksService.getBooksPageByPage(page,booksPerPage));
       }

        return "/books/index";
    }

    @GetMapping("/add")
    public String addPage(Model model, Book book){
        model.addAttribute("book", book);
        return "/books/add";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(value = "startString", required = false, defaultValue = "Zatichka") String startString, Model model){
       List <Book> theFound = booksService.findByName(startString);
        System.out.println(theFound);
       if(theFound == null){
           return "/books/search";
       }
       model.addAttribute("foundBooks", theFound);
       return "/books/search";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Book book){
        booksService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.remove(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable ("id") int id, Model model, @ModelAttribute ("person") Person person){
       Book book = booksService.getBook(id);
        model.addAttribute("book", book);
        if (book.getOwner() == null){
            model.addAttribute("people", peopleService.getPeople());
        }
        else{
            model.addAttribute("owner", book.getOwner());
        }
        return "/books/book";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable ("id") int id, Model model){
        model.addAttribute("book", booksService.getBook(id));
        return "/books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable ("id") int id, @ModelAttribute Book book){
        booksService.update(id,book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable ("id") int id){
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/assign")
    public String assign(@PathVariable ("id") int id, @ModelAttribute Person person){
        booksService.assign(person, id);
        return "redirect:/books/" + id;
    }
}
