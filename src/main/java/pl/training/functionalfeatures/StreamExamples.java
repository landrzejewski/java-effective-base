package pl.training.functionalfeatures;

import java.net.SocketTimeoutException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StreamExamples {

    record Employee(
            int id,
            String name,
            String department,
            double salary,
            int age,
            int yearsExperience,
            boolean remote
    ) {}

    static List<Employee> createEmployees() {
        return List.of(
                new Employee(1,  "Alice Johnson",  "Engineering", 95_000,  28, 5,  true),
                new Employee(2,  "Bob Smith",      "Marketing",   65_000,  32, 8,  false),
                new Employee(3,  "Carol Davis",    "Engineering", 110_000, 35, 12, true),
                new Employee(4,  "David Wilson",   "Sales",       75_000,  29, 6,  false),
                new Employee(5,  "Eve Brown",      "Engineering", 88_000,  26, 3,  true),
                new Employee(6,  "Frank Miller",   "HR",          72_000,  41, 15, false),
                new Employee(7,  "Grace Lee",      "Marketing",   68_000,  30, 7,  true),
                new Employee(8,  "Henry Garcia",   "Engineering", 105_000, 33, 9,  false),
                new Employee(9,  "Ivy Martinez",   "Sales",       82_000,  27, 4,  true),
                new Employee(10, "Jack Anderson",  "Engineering", 92_000,  31, 7,  false),
                new Employee(11, "Kate Thompson",  "HR",          78_000,  36, 11, true),
                new Employee(12, "Liam White",     "Marketing",   71_000,  28, 5,  false),
                new Employee(13, "Mia Harris",     "Sales",       79_000,  34, 10, true),
                new Employee(14, "Noah Clark",     "Engineering", 98_000,  29, 6,  false),
                new Employee(15, "Olivia Lewis",   "Marketing",   64_000,  25, 2,  true),
                new Employee(16, "Paul Walker",    "Sales",       85_000,  38, 13, false),
                new Employee(17, "Quinn Hall",     "HR",          76_000,  33, 9,  true),
                new Employee(18, "Ruby Allen",     "Engineering", 115_000, 37, 14, false),
                new Employee(19, "Sam Young",      "Marketing",   69_000,  31, 8,  true),
                new Employee(20, "Tina King",      "Sales",       81_000,  30, 7,  false)
        );
    }

    static Predicate<Employee> byMinSalary(double minSalary) {
        return employee -> employee.salary >= minSalary;
    }

    static String toNames(List<Employee> employees) {
        return employees.stream()
                .map(Employee::name)
                .collect(joining(", "));
    }

    public static void main(String[] args) {
        var employees = createEmployees();

        // employees.forEach(employee ->System.out.println(employee.name()));

        record NameSalary(String name, double salary) {}

        employees.stream()
                //.filter(employee -> employee.salary >= 100_000)
                .filter(byMinSalary(100_000))
                //.map(employee ->  employee.name())
                //.map(Employee::name)
                .map(employee -> new NameSalary(employee.name, employee.salary))
                .forEach(System.out::println);

        employees.stream()
                .filter(Employee::remote)
                .findFirst()
                .ifPresent(System.out::println);


        var youngest = employees.stream()
                .min(comparingInt(Employee::age));
        var oldest = employees.stream()
                .max(comparingInt(Employee::age));

        System.out.format("Wiek %d - %d%n",
                youngest.map(Employee::age).orElse(0),
                oldest.map(Employee::age).orElse(0)
        );

        var stats = employees.stream()
                .mapToDouble(Employee::salary)
                .summaryStatistics();
        System.out.format("Pensja min: $%.0f - max: $%.0f (avg. $%.2f)\n", stats.getMin(),  stats.getMax(), stats.getAverage());


        double avg = employees.stream().collect(teeing(
                summingDouble(Employee::salary),
                counting(),
                (sum, count) -> count == 0 ? 0 : sum / count
        ));
        System.out.println("Average salary: " + avg);

        // employees by dept
        employees.stream()
                .collect(groupingBy(Employee::department))
                .forEach((name, emp) -> System.out.printf("%s: %s\n", name, toNames(emp)));

    }

}
