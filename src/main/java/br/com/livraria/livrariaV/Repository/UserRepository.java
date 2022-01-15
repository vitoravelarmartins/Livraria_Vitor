package br.com.livraria.livrariaV.Repository;

import br.com.livraria.livrariaV.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
        public List<UserModel> findByNameIgnoreCase(String name);
}
