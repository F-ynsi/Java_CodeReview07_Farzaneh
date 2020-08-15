import javax.security.auth.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataAccess {

    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/cr7_Farzaneh" + "?useTimezone=true&serverTimezone=UTC";


    public DataAccess() throws SQLException, ClassNotFoundException {
        java.lang.Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Connecting to database...");
        SECRET secret = new SECRET();
        connection = DriverManager.getConnection(url, secret.getUser(), secret.getPassword());
        connection.setAutoCommit(true);
        connection.setReadOnly(false);

    }

    public void closeDb() throws SQLException {
        System.out.println("Closing connection...");
        connection.close();
    }


    public ArrayList<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        ArrayList <Student> studentList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                studentList.add(new Student(id, name, surname, email));
            }
            rs.close();
            preparedStatement.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return studentList;
    }

    public ArrayList<Teacher> getAllTeachers(){
        String sql = "SELECT * FROM teachers";
        ArrayList <Teacher> teacherList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                teacherList.add(new Teacher(id, name, surname, email));
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return teacherList;
    }

    public ArrayList<Class> getAllClasses(){
        String sql = "SELECT * FROM classes";
        ArrayList <Class> classList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                classList.add(new Class(id, name));
            }
            rs.close();
            preparedStatement.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return classList;
    }



    public HashMap<ArrayList<Class>, Teacher> getClassesOfTeacher(int teacherId){

        String sql_classes = "SELECT classes.id, classes.name FROM teachersclassesrelation " +
                "JOIN classes ON classes.id = teachersclassesrelation.fk_class_id " +
                "WHERE teachersclassesrelation.fk_teacher_id = ?";
        String sql_name = "SELECT * FROM teachers WHERE teachers.id= ?";
        ArrayList <Class> classList = new ArrayList<>();

        HashMap <ArrayList<Class>, Teacher> classTeacher = new HashMap<>();

        try {
            PreparedStatement pst1 = connection.prepareStatement(sql_classes);
            pst1.setInt(1, teacherId);
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {
                int classId = rs1.getInt("id");
                String className = rs1.getString("name");
                classList.add(new Class(classId, className));
            }

            PreparedStatement pst2 = connection.prepareStatement(sql_name);
            pst2.setInt(1, teacherId);
            ResultSet rs2 = pst2.executeQuery();
            rs2.next();
            String name = rs2.getString("name");
            String surname = rs2.getString("surname");
            String email = rs2.getString("email");
            Teacher teacher = new Teacher(teacherId, name, surname, email);
            classTeacher.put(classList, teacher);

            rs1.close();
            rs2.close();
            pst1.close();
            pst2.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return classTeacher;
    }

    public void displayAllStudents(){
        ArrayList <Student> studentList = getAllStudents();
        System.out.println("\n-------------------------- ALL STUDENTS --------------------------");
        for (Student std: studentList) {
            System.out.println(std);
        }
    }

    public void displayAllTeachers(){

        ArrayList <Teacher> teacherList = getAllTeachers();
        System.out.println("\n-------------------------- ALL TEACHERS --------------------------");
        for (Teacher teacher: teacherList){
            System.out.println(teacher);
        }
    }
    public void displayAllClasses(){
        ArrayList <Class> classList = getAllClasses();
        System.out.println("\n-------------------------- ALL CLASSES --------------------------");
        for (Class cs: classList){
            System.out.println(cs);
        }
    }


    public void displayAllTeachersWithId(){
        ArrayList <Teacher> teacherList= getAllTeachers();
        for (Teacher teacher: teacherList){
            System.out.println("ID " + teacher.getId() + ": " + teacher);
        }
    }




    public void displayClassesOfTeacher(int teacherId){
        HashMap<ArrayList<Class>, Teacher> classTeacher = getClassesOfTeacher(teacherId);
        //System.out.println("\n########## Information of teacher with ID " + teacherId + " ##########");

        for (Teacher teacher: classTeacher.values()){
            String format = String.format("------------------- Teacher \"%s\" " +
                    "teaches following courses ------------------- ", teacher.getFullName());
            System.out.println(format);
        }

        for (ArrayList <Class> classes: classTeacher.keySet()){
            for (Class cs: classes){
                System.out.println(cs);
            }
        }

        System.out.println("=".repeat(83));
    }


    public void closeDB() throws SQLException {
        System.out.println("Closing connection...");
        connection.close();
    }

}

