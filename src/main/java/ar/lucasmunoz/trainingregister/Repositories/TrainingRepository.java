package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.User;
import org.springframework.data.repository.CrudRepository;
import ar.lucasmunoz.trainingregister.Models.Training;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TrainingRepository extends CrudRepository<Training, Integer> {


    List<Training> findByIdAndCreatorFK(Integer Id, Integer CreatorFK);

    List<Training> findByNameAndCreatorFK(String name, User CreatorFK);


    List<Training> findByCreatorFK(User CreatorFK);


    List<Training> findByNameContainingIgnoreCase(String name);


}
