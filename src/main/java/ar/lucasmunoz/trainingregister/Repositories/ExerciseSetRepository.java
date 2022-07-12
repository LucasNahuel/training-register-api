package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.ExerciseDay;
import ar.lucasmunoz.trainingregister.Models.ExerciseSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseSetRepository extends CrudRepository<ExerciseSet, Integer> {

    List<ExerciseSet> findByExerciseDayFk(ExerciseDay exerciseDay);


}
