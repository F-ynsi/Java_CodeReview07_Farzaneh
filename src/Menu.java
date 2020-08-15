import java.net.Inet4Address;
import java.util.Scanner;

public class Menu {
    private static Scanner scan = new Scanner(System.in);
    public Menu(DataAccess dataAccess){
        System.out.println("+------------------------------------+");
        System.out.println("|                                    |");
        System.out.println("|     Welcome to the school menu     |");
        System.out.println("|                                    |");
        System.out.println("+------------------------------------+");
        showMenu(dataAccess);
    }
    public void showMenu(DataAccess dataAccess){
        System.out.println("\nSelect from the following:\n" +
                "1) Display all students.\n" +
                "2) Display all teachers.\n" +
                "3) Display all classes.\n" +
                "4) Display courses of a specific teacher.\n" +
                "0) Quit");

        System.out.println("\nEnter your choice: ");
        int user_input = Integer.parseInt(scan.next());
        switch (user_input){
            case 1:
                dataAccess.displayAllStudents();
                showMenu(dataAccess);
                break;
            case 2:
                dataAccess.displayAllTeachers();
                showMenu(dataAccess);
                break;
            case 3:
                dataAccess.displayAllClasses();
                showMenu(dataAccess);
                break;

            case 4:
                System.out.println("\nPlease select teacher ID (0 to go back):");
                dataAccess.displayAllTeachersWithId();
                int teacherId = scan.nextInt();
                if (teacherId == 0){
                    showMenu(dataAccess);
                }
                else if(dataAccess.getAllTeachers().size() >= teacherId){
                    dataAccess.displayClassesOfTeacher(teacherId);
                    showMenu(dataAccess);
                }
                else{
                    System.out.println("Wrong teacher ID");
                    showMenu(dataAccess);
                }
                break;
                
            case 0:
                System.out.println("Thank you! BYE!");
                return;

            default:
                System.out.println("Enter a valid number!");
                showMenu(dataAccess);
                break;
        }
    }
}
