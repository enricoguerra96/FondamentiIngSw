package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private PersonRepository repository;

    @RequestMapping("/")
    public String index(){
        return "list";
    }

    @RequestMapping("/list")
    public String list(Model model){
        List<Person> data = new LinkedList<>();
        for (Person p: repository.findAll()){
            data.add(p);
        }
        model.addAttribute("people", data);
        return "list";
    }

    @RequestMapping("/input")
    public String input(){
        return "input";
    }

    @RequestMapping("/create")
    public String create(
            @RequestParam(name="firstname", required=true) String firstname,
            @RequestParam(name="lastname", required=true) String lastname) {
        repository.save(new Person(firstname,lastname));
        return "redirect:/list";
    }



    @RequestMapping("/show")
    public String read(
            @RequestParam(name="id", required=true) Long id,
            Model model) {
        Optional<Person> result = repository.findById(id);
        //TODO: check if the result is found
        if(!result.isPresent()){
            return "notfound";
        }

        //TODO: put data in the model field to be displayed in the page
        model.addAttribute("id", result.get().getId());
        model.addAttribute("name", result.get().getFirstName());
        model.addAttribute("surname", result.get().getLastName());
        return "show";
        //TODO: in case no data is found, display the "notfound" page
    }



    @RequestMapping("/edit")
    public String edit(
            @RequestParam(name="id", required=true) Long id,
            Model model) {
        Optional<Person> result = repository.findById(id);
        //TODO: check if the result is found
        if(!result.isPresent()){
            return "notfound";
        }
        //TODO: put data in the model field to be displayed in the next page to edit them
        model.addAttribute("id", result.get().getId());
        model.addAttribute("name", result.get().getFirstName());
        model.addAttribute("surname", result.get().getLastName());
        return "edit";
        //TODO: in case no data is found, display the "notfound" page
    }

    @RequestMapping("/update")
    public String update(
            @RequestParam(name="id", required=true) Long id,
            @RequestParam(name="firstname", required=true) String firstname,
            @RequestParam(name="lastname", required=true) String lastname,
            Model model) {
        //TODO: check if the result is found
        Optional<Person> result = repository.findById(id);
        if(!result.isPresent()){
            return "notfound";
        }
        //TODO: delete the old person and add a new person
        repository.deleteById(id);
        repository.save(new Person(firstname,lastname));
        return "redirect:/list";
        //TODO: in case no data is found, display the "notfound" page
    }


    @RequestMapping("/delete")
    public String delete(
            @RequestParam(name="id", required=true) Long id) {
        //TODO: check if the result is found
        Optional<Person> result = repository.findById(id);
        if(!result.isPresent()){
            return "notfound";
        }
        //TODO: delete the old person and add a new person
        repository.deleteById(id);
        return "redirect:/list";
        //TODO: in case no data is found, display the "notfound" page
    }

}