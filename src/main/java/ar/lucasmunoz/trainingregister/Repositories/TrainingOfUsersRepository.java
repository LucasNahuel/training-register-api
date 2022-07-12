package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.Training;
import ar.lucasmunoz.trainingregister.Models.TrainingOfUsers;
import ar.lucasmunoz.trainingregister.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingOfUsersRepository extends CrudRepository<TrainingOfUsers, Integer> {

        List<TrainingOfUsers> findByUserAndIsActive(User user, Boolean isActive);

        List<TrainingOfUsers> findByUserAndTraining(User user, Training training);



}
