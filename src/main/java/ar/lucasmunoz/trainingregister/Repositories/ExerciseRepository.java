package ar.lucasmunoz.trainingregister.Repositories;

import ar.lucasmunoz.trainingregister.Models.Exercise;
import ar.lucasmunoz.trainingregister.Models.Training;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Integer> {

    List<Exercise> findByTrainingFk(Training training);

}
