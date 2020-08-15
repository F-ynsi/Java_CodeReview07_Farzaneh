SELECT students.*
FROM students
INNER JOIN enrollment ON students.id = enrollment.fk_student_id
WHERE fk_class_id = 1;


SELECT students.*
FROM students
INNER JOIN enrollment ON students.id = enrollment.fk_student_id
INNER JOIN classes ON enrollment.fk_class_id = classes.id
WHERE classes.name = "Matematic";