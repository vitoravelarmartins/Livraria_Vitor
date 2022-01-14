package br.com.livraria.livrariaV.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idBook;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column (length = 10)
    public String dateRent;
    @Column (length = 10)
    private String dateDelivery;
    @Column(nullable = false)
    private String statusBook;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserModel idUser;



//    public Date getDateRent(Date dateRent) {
//        return dateRent;
//    }
//
//    public Date getDateDelivery(Date dateDelivery) {
//        return dateDelivery;
//    }
}
