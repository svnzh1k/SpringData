package com.sanzhar.spring.controllers;




import com.sanzhar.spring.models.Person;
import com.sanzhar.spring.services.BooksService;
import com.sanzhar.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public PeopleController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String indexPage(Model model){
        model.addAttribute("people",peopleService.getPeople());
        return "/people/index";
    }

    @GetMapping("/add")
    public String addPage(Model model, Person person){
        model.addAttribute("person", person);
        return "/people/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Person person){
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable ("id") int id){
        peopleService.remove(id);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String personPage(@PathVariable ("id") int id, Model model){
        model.addAttribute("boolean", booksService.hasBooks(id));
        model.addAttribute("personsbook", booksService.getPersonsBooks(id));
        model.addAttribute("person", peopleService.getPerson(id));
       return "/people/person";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable ("id") int id, Model model){
        model.addAttribute("person", peopleService.getPerson(id));
        return "/people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable ("id") int id, @ModelAttribute Person person){
        peopleService.update(id,person);
        return "redirect:/people";
    }

}
