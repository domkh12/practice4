package view;

import exception.CourseIdException;
import exception.CourseTitleException;
import exception.InvalidInputException;
import model.Course;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;
import repository.Repository;
import service.CourseService;
import service.CourseServiceImp;

import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class CourseDashboard {
    CourseService courseService = new CourseServiceImp();
    Repository repository = new Repository();

    private static void option() {
        Table table = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);
        table.setColumnWidth(0, 60, 60);

        table.addCell("Course Management", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("  1. Add new Course", new CellStyle(CellStyle.HorizontalAlign.LEFT));
        table.addCell("  2. List Course", new CellStyle(CellStyle.HorizontalAlign.LEFT));
        table.addCell("  3. Find Course By Id", new CellStyle(CellStyle.HorizontalAlign.LEFT));
        table.addCell("  4. Find Course By Title", new CellStyle(CellStyle.HorizontalAlign.LEFT));
        table.addCell("  5. Remove Course By Id", new CellStyle(CellStyle.HorizontalAlign.LEFT));
        table.addCell("  0/99. Exit", new CellStyle(CellStyle.HorizontalAlign.LEFT));

        System.out.println(table.render());
    }

    private static Course insertNewCourseInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("[+] Insert course title: ");
        String title = scanner.nextLine();

        System.out.print("[+] Insert instructor names (comma separated): ");
        String instructorNames = scanner.nextLine();
        String[] insNames = instructorNames.split(",");
        for (int i = 0; i < insNames.length; i++) {
            insNames[i] = insNames[i].trim();
        }

        System.out.print("[+] Insert course requirements (comma separated): ");
        String courseRequirements = scanner.nextLine();
        String[] requirements = courseRequirements.split(",");
        for (int i = 0; i < requirements.length; i++) {
            requirements[i] = requirements[i].trim();
        }

        Integer randomId = new Random().nextInt(10000);
        return new Course(randomId, title, insNames, requirements, LocalDate.now().toString());
    }

    private static int searchById() throws InvalidInputException{
        System.out.print("[+] Insert Course ID: ");
        String input = new Scanner(System.in).next();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("[!] Course ID must be a Number.");
        }
    }

    private static String searchByTitle() {
        System.out.print("[+] Insert Course Title: ");
        return new Scanner(System.in).next();
    }

    private static int removeById() throws InvalidInputException{
        System.out.print("[+] Insert Course Id To Remove: ");
        String input = new Scanner(System.in).next();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("[!] Course ID must be a Number.");
        }
    }

    public void display() {
        while (true) {
            option();
            System.out.print("[+] Insert Option: ");
            String op = new Scanner(System.in).next();
            try {
                switch (op.trim()) {
                    case "1":
                        courseService.addCourse(insertNewCourseInfo());
                        break;
                    case "2":
                        courseService.showAllList();
                        break;
                    case "3":
                        courseService.findById(searchById());
                        break;
                    case "4":
                        courseService.findByTitle(searchByTitle());
                        break;
                    case "5":
                        courseService.removeById(removeById());
                        break;
                    case "0":
                    case "99":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("[!] Invalid Option. Please try again!!");
                }
            } catch (InvalidInputException|CourseIdException | CourseTitleException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
