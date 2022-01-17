package br.com.livraria.livrariaV.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

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
    @Column (length = 50)
    public String dateRent;
    @Column (length = 50)
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
