INSERT INTO training_day (id, day) VALUES (1, 'monday');
INSERT INTO training_day (id, day) VALUES (2, 'tuesday');
INSERT INTO training_day (id, day) VALUES (3, 'wednesday');
INSERT INTO training_day (id, day) VALUES (4, 'thursday');
INSERT INTO training_day (id, day) VALUES (5, 'friday');
INSERT INTO training_day (id, day) VALUES (6, 'saturday');
INSERT INTO training_day (id, day) VALUES (7, 'sunday');

INSERT INTO user (id, user, password, token, email) VALUES (1, "lucas", "can_317", null, "munozlucas96@gmail.com");

INSERT INTO user (id, user, password, token, email) VALUES (2, "juan", "can_317", null, "munozjuan96@gmail.com");

INSERT INTO training (id, name, creatorfk_id) VALUES (1, "training 1", 1);

INSERT INTO training_of_users (id, is_active, training_id, user_id) VALUES (1, true, 1, 1);

INSERT INTO exercise (id, name, training_fk_id) VALUES (1, "exercise one", 1);

INSERT INTO exercise_day (id, execution_order, exercise_fk_id, training_day_fk_id) VALUES (1, 1, 1, 1);

INSERT INTO exercise_set (id, repetitions, weight, exercise_day_fk_id) VALUES (1, 1, 1, 1);