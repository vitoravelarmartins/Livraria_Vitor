package br.com.livraria.livrariaV.Repository;

import br.com.livraria.livrariaV.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
        public List<UserModel> findByNameIgnoreCase(String name);
}
