package repository;

import model.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {
    List<Course> courses = new ArrayList<>();
    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public void setCourses(List<Course> updatedCourses) {
        this.courses = new ArrayList<>(updatedCourses);
    }
}
