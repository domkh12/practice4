package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    private Integer id;
    private String courseTitle;
    private String[] instructorName;
    private String[] courseRequirment;
    private String startDate;
}
