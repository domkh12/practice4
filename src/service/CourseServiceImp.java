package service;

import exception.CourseIdException;
import exception.CourseTitleException;
import exception.InvalidInputException;
import model.Course;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import repository.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImp implements CourseService{
    Repository repository = new Repository();
    List<Course> courses = repository.getCourses();
    @Override
    public void addCourse(Course course)throws CourseTitleException, InvalidInputException {
        if (course.getCourseTitle() == null || course.getCourseTitle().isEmpty()) {
            throw new CourseTitleException("[!] Course title cannot be empty.");
        }
        if (isNumeric(course.getCourseTitle())) {
            throw new InvalidInputException("[!] Course title cannot contain only numbers.");
        }
        for (String instructor : course.getInstructorName()) {
            if (isNumeric(instructor)) {
                throw new InvalidInputException("[!] Instructor name cannot contain only numbers.");
            }
        }
        for (String requirement : course.getCourseRequirment()) {
            if (isNumeric(requirement)) {
                throw new InvalidInputException("[!] Course requirement cannot contain only numbers.");
            }
        }
        repository.addCourse(course);
        courses = repository.getCourses();
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("[0-9]+");
    }

    @Override
    public void showAllList() {
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        for(int i = 0; i < 5; i++){
            table.setColumnWidth(i, 20,20);
        }
        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("TITLE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("INSTRUCTOR", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("REQUIREMENT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("START DATE", new CellStyle(CellStyle.HorizontalAlign.CENTER));

    for (Course c: courses){
        table.addCell(String.valueOf(c.getId()),new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
        table.addCell(c.getCourseTitle(), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
        table.addCell(Arrays.toString(c.getInstructorName()), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
        table.addCell(Arrays.toString(c.getCourseRequirment()), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
        table.addCell(c.getStartDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
        }

        System.out.println(table.render());
    }

    @Override
    public void findById(int id) throws CourseIdException{
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        for(int i = 0; i < 5; i++){
            table.setColumnWidth(i, 20,20);
        }
        List<Course> resultSearch = courses.stream().filter(e->e.getId().equals(id)).toList();
        if (resultSearch.isEmpty()) {
            throw new CourseIdException("[!] Course with ID " + id + " not found.");
        }
        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("TITLE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("INSTRUCTOR", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("REQUIREMENT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("START DATE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        for(Course c: resultSearch){
            table.addCell(String.valueOf(c.getId()),new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(c.getCourseTitle(), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(Arrays.toString(c.getInstructorName()), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(Arrays.toString(c.getCourseRequirment()), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(c.getStartDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
        }
        System.out.println(table.render());
    }

    @Override
    public void findByTitle(String title) throws CourseTitleException{
        if (title == null || title.isEmpty()) {
            throw new CourseTitleException("[!] Title cannot be empty.");
        }
        List<Course> resultSearch = courses.stream().filter(e->e.getCourseTitle().contains(title)).toList();
        if (resultSearch.isEmpty()) {
            throw new CourseTitleException("[!] No courses found with the title containing: " + title);
        }
        Table table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        for(int i = 0; i < 5; i++){
            table.setColumnWidth(i, 20,20);
        }

        table.addCell("ID", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("TITLE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("INSTRUCTOR", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("REQUIREMENT", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        table.addCell("START DATE", new CellStyle(CellStyle.HorizontalAlign.CENTER));
        for(Course c: resultSearch){
            table.addCell(String.valueOf(c.getId()),new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(c.getCourseTitle(), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(Arrays.toString(c.getInstructorName()), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(Arrays.toString(c.getCourseRequirment()), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
            table.addCell(c.getStartDate(), new CellStyle(CellStyle.HorizontalAlign.CENTER),1);
        }
        System.out.println(table.render());
    }

    @Override
    public void removeById(int id) throws CourseIdException{
        List<Course> updatedCourses = courses.stream()
                .filter(course -> !course.getId().equals(id))
                .collect(Collectors.toList());
        if (updatedCourses.size() == courses.size()) {
            throw new CourseIdException("[!] Course with ID " + id + " not found.");
        }
        repository.setCourses(updatedCourses); // Assuming repository has a setCourses method
        courses = repository.getCourses(); // Refresh local list
        System.out.println("[*] Course with ID " + id + " removed successfully.");
    }

}
