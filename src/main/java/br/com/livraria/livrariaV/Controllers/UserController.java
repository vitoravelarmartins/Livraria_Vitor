package br.com.livraria.livrariaV.Controllers;


import br.com.livraria.livrariaV.Model.UserModel;
import br.com.livraria.livrariaV.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

    // GET
    // POST
    // PUT
    // DELETE
    // PATCH

    @Autowired
    private UserRepository userRepository;

    private List<UserModel> users = new ArrayList<>();


    //GET
    @GetMapping("/h")
    public UserModel userOne() {
        UserModel user = new UserModel();
        user.setId(1);
        user.setName("Vitor");
        user.setEmail("vitor@g.com");
        return user;
    }

    //POST
    @PostMapping("/create")
    public UserModel userAdd(@RequestBody UserModel user) {
        return this.userRepository.save(user);


    }

    //GET_LIST
    @GetMapping("/list")
    public List<UserModel> list() {
        return this.userRepository.findAll();
    }

    // Trazendo user recebendo parametro
    @GetMapping("/{id}")
    public UserModel userID(@PathVariable("id") Integer id) {
        System.out.println("The id user is s" + id);
        UserModel user = new UserModel();
        user.setId(1);
        user.setName("Vitor");
        user.setEmail("vitor@g.com");
        return user;
    }

    // Percorrer Lista para encontrar usuario ID
    @GetMapping("/{id}/scan")
    public ResponseEntity<UserModel> userScan(@PathVariable("id") Integer id) {
        Optional<UserModel> userFind = this.userRepository.findById(id);

        if (userFind.isPresent()) {
            return ResponseEntity.accepted().body(userFind.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //Chamar User com ID maior que indicado
    @GetMapping("/list/more/{id}")
    public List<UserModel> listMoreThan(@PathVariable("id") int id) {
        //return this.userRepository.findAllMoreThan(id);
        return this.userRepository.findByIdGreaterThan(id);
    }

    //Procura pelo Nome
    @GetMapping("/findName/{name}")
    public List<UserModel> findName(@PathVariable("name") String name) {
        return this.userRepository.findByNameIgnoreCase(name);
    }

}
