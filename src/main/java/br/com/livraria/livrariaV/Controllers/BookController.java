package br.com.livraria.livrariaV.Controllers;


import br.com.livraria.livrariaV.Model.BookModel;
import br.com.livraria.livrariaV.Repository.BookRepository;
import br.com.livraria.livrariaV.Services.CalcDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books") // http://localhost:8080/books
public class BookController {

    @Autowired
    private BookRepository bookRepository;


    String readyToUse = "DISPONIVEL";
    String leased = "LOCADO";
    String delayed = "ATRASADO";
    String deliveryToday = "ENTREGA HOJE";
    String withinTime = "DENTRO DO PRAZO";

    public BookController() throws Exception {
    }

    //POST - Criar Livro
    @PostMapping("/create")
    public BookModel bookCreate(@RequestBody BookModel book) {
        // Ja seta livro como disponivel para locar
        book.setStatusBook(readyToUse);
        return this.bookRepository.save(book);
    }

    //GET - Procura livro pelo NOME
    @GetMapping("/title/{title}")
    public List<BookModel> findTitle(@PathVariable("title") String title) {
        return this.bookRepository.findByTitleIgnoreCase(title);
    }
    //GET - Lista todos os livros, verifica data e seta status do usuario EX. "Atrasado"
    @EventListener(ApplicationReadyEvent.class)
    @GetMapping("/list")
    public List<BookModel> listBook() throws Exception {

        List<BookModel> listaBook = this.bookRepository.findAll();

        for (BookModel bookModel : listaBook) {
            if (bookModel.getStatusBook().equals(leased)) {
                System.out.println(bookModel.getTitle());

                var idBook = bookModel.getIdBook();
                var returnCalcDate = calcDateInBook(idBook);

                switch (returnCalcDate) {
                    case -1:

                        settingStatus(idBook, delayed);
                        System.out.println("-1"+delayed);
                        break;
                    case 0:
                        settingStatus(idBook, deliveryToday);
                        System.out.println("0"+deliveryToday);
                        break;
                    case 1:
                        settingStatus(idBook, withinTime);
                        System.out.println("1"+withinTime);
                        break;
                }
            }
        }
        return this.bookRepository.findAll();
    }

    //PUT - Editar livros
    @PutMapping("/{idBook}/edit")
    public ResponseEntity<BookModel> bookEdit(@PathVariable("idBook") Integer idBook, @RequestBody BookModel bookDetails) throws Exception {

        Optional<BookModel> inBook = bookRepository.findById(idBook);

        inBook.get().setAuthor(bookDetails.getAuthor());
        inBook.get().setTitle(bookDetails.getTitle());

        bookRepository.save(inBook.get());
        return ResponseEntity.accepted().body(inBook.get());
    }

    //GET - Calcular data
    @GetMapping
    public Integer calcDateInBook(@PathVariable("idBook") Integer idBook) {

            CalcDate calcDate = new CalcDate();
            Optional<BookModel> inBook = bookRepository.findById(idBook);

        if (inBook.get().getStatusBook().equals(readyToUse)) {
            return 2; //Livros que não foram LOCADOS vão retonar "2"
        } else
            return calcDate.calcDate(inBook.get().getDateDelivery());
    }

    //PUT - Definir status para usuario
    @PutMapping
    public void settingStatus(@PathVariable("idBook") Integer idBook, @PathVariable("statusValueUser") String statusValueUser) throws Exception {
        Optional<BookModel> inBook = bookRepository.findById(idBook);
        inBook.get().getIdUser().getStatusUser();
        inBook.get().getIdUser().setStatusUser(statusValueUser);
        bookRepository.save(inBook.get());
    }
   @DeleteMapping("/{idBook}/delete")
    public ResponseEntity<BookModel> deleteBook(@PathVariable("idBook") Integer idBook){
       Optional<BookModel> inBook = bookRepository.findById(idBook);
       if(inBook.get().getStatusBook().equals(leased)){
           return  new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
       }else
       bookRepository.delete(inBook.get());
       return  new ResponseEntity<>(HttpStatus.ACCEPTED);
   }


}




