public class Student {

    private String name;
    private Integer old;
    private Double salary;
    private Double score;
    private Status Status;

    public enum Status {
        FREE,
        BUSY,
        VOCATION
    }

    public Student(String name, Integer old, Double salary, Double score, Student.Status status) {
        this.name = name;
        this.old = old;
        this.salary = salary;
        this.score = score;
        Status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", old=" + old +
                ", salary=" + salary +
                ", score=" + score +
                ", Status=" + Status +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOld() {
        return old;
    }

    public void setOld(Integer old) {
        this.old = old;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Student.Status getStatus() {
        return Status;
    }

    public void setStatus(Student.Status status) {
        Status = status;
    }
}


