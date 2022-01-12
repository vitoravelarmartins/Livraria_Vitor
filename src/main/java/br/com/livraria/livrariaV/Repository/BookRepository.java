package br.com.livraria.livrariaV.Repository;

import br.com.livraria.livrariaV.Model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Integer> {

}
