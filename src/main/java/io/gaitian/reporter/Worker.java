package io.gaitian.reporter;

public class Worker {
    private final String name;
    private final String surname;
    private final Integer age;

    public Worker(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAge() {
        return age;
    }
}
