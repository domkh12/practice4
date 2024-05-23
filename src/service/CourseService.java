package service;

import exception.CourseIdException;
import exception.CourseTitleException;
import exception.InvalidInputException;
import model.Course;

public interface CourseService {
    void addCourse(Course course) throws CourseTitleException, InvalidInputException;
    void showAllList();
    void findById(int id)throws CourseIdException;
    void findByTitle(String title)throws CourseTitleException;
    void removeById(int id)throws CourseIdException;
}
