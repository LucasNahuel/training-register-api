package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.Exercise;
import ar.lucasmunoz.trainingregister.Models.ExerciseDay;
import ar.lucasmunoz.trainingregister.Models.Training;
import ar.lucasmunoz.trainingregister.Models.TrainingDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDayRepository extends CrudRepository<ExerciseDay, Integer> {

    List<ExerciseDay> findByExerciseFkAndTrainingDayFk(Exercise exercise, TrainingDay trainingDay);

    List<ExerciseDay> findByExerciseFk(Exercise exercise);

}
