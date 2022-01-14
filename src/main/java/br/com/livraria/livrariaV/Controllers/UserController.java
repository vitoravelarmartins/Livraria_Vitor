package br.com.livraria.livrariaV.Controllers;


import br.com.livraria.livrariaV.Model.BookModel;
import br.com.livraria.livrariaV.Model.UserModel;
import br.com.livraria.livrariaV.Repository.BookRepository;
import br.com.livraria.livrariaV.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private BookRepository bookRepository;

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

    //Locar Livro
    @PutMapping("{idUser}/books/{idBook}/locar")
    public ResponseEntity<BookModel> bookRent(@PathVariable("idUser") Integer idUser,
                                              @PathVariable("idBook") Integer idBook,
                                              @RequestBody BookModel bookDetails
    ) throws Exception {

        String rented = "LOCADO";
        String setStatus = "DENTRO DO PRAZO";


        Optional<UserModel> inUser = userRepository.findById(idUser);
        Optional<BookModel> inBook = bookRepository.findById(idBook);

        var getUserStatus = inUser.get().getStatusUser();
        var getBookStatus = inBook.get().getStatusBook();

        if (getUserStatus != null | getBookStatus.equals(rented)) {
            System.out.println(getUserStatus);
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        } else
            inBook.get().setIdUser(inUser.get());
        inBook.get().setDateRent(bookDetails.getDateRent());
        inBook.get().setDateDelivery(bookDetails.getDateDelivery());
        inBook.get().setStatusBook(rented);
        inUser.get().setStatusUser(setStatus);
        bookRepository.save(inBook.get());

        return ResponseEntity.accepted().body(inBook.get());

    }

    @PutMapping("{idUser}/books/{idBook}/devolver")
    public ResponseEntity<BookModel> bookDelivery(@PathVariable("idUser") Integer idUser,
                                                  @PathVariable("idBook") Integer idBook) throws Exception {

        String available = "DISPONIVEL";

        Optional<UserModel> inUser = userRepository.findById(idUser);
        Optional<BookModel> inBook = bookRepository.findById(idBook);

        var userId = idUser.toString();
        var userIdInBook = inBook.get().getIdUser().getId().toString();

        if (userId.equals(userIdInBook)) {

            inBook.get().setIdUser(null);
            inBook.get().setDateRent(null);
            inBook.get().setDateDelivery(null);
            inBook.get().setStatusBook(available);
            inUser.get().setStatusUser(null);
            bookRepository.save(inBook.get());

            return ResponseEntity.accepted().body(inBook.get());
            //return new ResponseEntity<>(HttpStatus.ACCEPTED);

        } else
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

    }

    @GetMapping("/books/calc/{idBook}")
    public ResponseEntity<BookModel> calcTime(@PathVariable("idBook") Integer idBook) {


        Optional<BookModel> inBook = bookRepository.findById(idBook);

        if (inBook.get().getStatusBook().equals("DISPONIVEL")) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        } else
            System.out.println(inBook.get().getDateRent());
        System.out.println(inBook.get().getDateDelivery());

        Date data = new Date(System.currentTimeMillis());
        System.out.println(data);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDelivery = null;
        try {
            dateDelivery = formatter.parse(inBook.get().getDateDelivery());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(dateDelivery);
        System.out.println(dateDelivery.compareTo(data)); // -1 Atrasado *0 Entrega Hoje* 1 Dentro do prazo


        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @PutMapping
    public void fake(@PathVariable("idUser") Integer idUser) throws Exception {

        Optional<UserModel> inUser = userRepository.findById(idUser);
        inUser.get().setStatusUser("ATRASADO FAKE 22");
    }


    @GetMapping("/listbook")
    public List<BookModel> listBook() throws Exception {
        List<BookModel> listaBook = this.bookRepository.findAll();
        for (BookModel bookModel : listaBook) {

            if(bookModel.getStatusBook().equals("LOCADO")){
                fake(bookModel.getIdUser().getId());
                System.out.println("to aqui");


            }else
                System.out.println("No fake");

            System.out.println(bookModel.getDateDelivery());
        }
        //System.out.println(this.bookRepository.findAll().size());

        return this.bookRepository.findAll();
    }


}
