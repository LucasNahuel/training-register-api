package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.TrainingDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TrainingDayRepository extends CrudRepository<TrainingDay, Integer> {


    List<TrainingDay> findByDay(String day);

}
