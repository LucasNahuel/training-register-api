package ar.lucasmunoz.trainingregister.Repositories;
// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import ar.lucasmunoz.trainingregister.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    List<User> findByUserAndPassword(String user, String password);

    List<User> findByUser(String user);

}