package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.ExerciseSet;
import ar.lucasmunoz.trainingregister.Models.SetLog;
import ar.lucasmunoz.trainingregister.Models.TrainingLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetLogRepository  extends CrudRepository<SetLog, Integer> {

    List<SetLog> findByTrainingLogFKAndSetFK(TrainingLog trainingLog, ExerciseSet exerciseSet);

    List<SetLog> findByTrainingLogFK(TrainingLog trainingLog);

}
