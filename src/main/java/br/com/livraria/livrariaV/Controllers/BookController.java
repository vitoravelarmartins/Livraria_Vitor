package br.com.livraria.livrariaV.Controllers;

import br.com.livraria.livrariaV.Exception.BookNotFoundException;
import br.com.livraria.livrariaV.Model.BookModel;
import br.com.livraria.livrariaV.Model.UserModel;
import br.com.livraria.livrariaV.Repository.BookRepository;
import br.com.livraria.livrariaV.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books") // http://localhost:8080/books
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    //Post Criar Livros
    @PostMapping("/create")
    public BookModel bookAdd(@RequestBody BookModel book) {
        var readyToUse = "DISPONIVEL";
        book.setStatusBook(readyToUse);
        return this.bookRepository.save(book);
    }

    //GET Listar Todos os Livros
    @GetMapping("/list")
    public List<BookModel> list() {
        return this.bookRepository.findAll();
    }

    @PutMapping("/{idBook}/edit")
    public void bookEdit(@PathVariable("idBook") Integer idBook, @RequestBody BookModel bookDetails) throws Exception {

        var b = bookRepository.findById(idBook);
        var novoBook = b.get();
        System.out.println(novoBook.getIdBook());
        novoBook.setAuthor(bookDetails.getAuthor());
        System.out.println(novoBook.getAuthor());
        novoBook.setTitle(bookDetails.getTitle());
        novoBook.setAuthor(bookDetails.getAuthor());
        novoBook.setStatusBook(bookDetails.getStatusBook());
        bookRepository.save(novoBook);


        //return ResponseEntity.accepted().body(b.get());

    }

    @PutMapping("/books/{idBook}/edit")
    public BookModel updateNote(@PathVariable(value = "idBook") Integer bookId,
                                @RequestBody BookModel bookDetails) throws BookNotFoundException {

        BookModel book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setStatusBook(bookDetails.getStatusBook());

        BookModel updatedBook = bookRepository.save(book);

        return updatedBook;
    }




}
