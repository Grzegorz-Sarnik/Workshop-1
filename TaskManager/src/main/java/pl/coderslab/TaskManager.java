package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    private static final String FILENAME = "Workshop-1/TaskManager/tasks.csv";
    static String[][] tasks = new String[0][0];


    public static void main(String[] args) {
        readTasks(FILENAME);

        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            showOperation();
            String input = scanner.next();

            switch (input) {
                case "exit":
                    System.out.println(ConsoleColors.RED_BOLD + "THE END" + ConsoleColors.RESET);
                    saveToFile(FILENAME);
                    System.exit(0);
                case "add":
                    addTask();
                    System.out.println(ConsoleColors.GREEN_BOLD + "Success!" + ConsoleColors.RESET + " - task has " +
                            "been added.");
                    break;
                case "remove":
                    remove();
                    break;
                case "list":
                    list();
                    break;

                default:
                    System.out.println("error  - incorrect command");
            }
        }
    }

    public static void saveToFile(String fileName) {
        Path target = Paths.get(fileName);
        String[] line = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            line[i] = String.join(",", tasks[i]);
            try {
                Files.write(target, Arrays.asList(line));
            } catch (IOException e) {
                System.out.println("Nie udało się zapisać do pliku");
            }
        }

    }

    public static void remove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.println("Incorrect value! - Select integers greater then 0!");
        }

        int index = scanner.nextInt();

        try {
            tasks = ArrayUtils.remove(tasks, index);
            System.out.println(ConsoleColors.GREEN_BOLD + "Success!" + ConsoleColors.RESET + " - Value was successfully deleted.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect argument passed. please give number in range 0 - " + (tasks.length - 1) + ".");
        }
    }

    public static void list() {
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + " : " + Arrays.toString(tasks[i]));
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please add task description:");
        String description = scanner.nextLine();

        System.out.println("Please add task due date [yyyy-mm-dd] :");
        String dueDate = scanner.nextLine();

        System.out.println("Is your task is important: true/false");
        String importance = scanner.nextLine();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        String task = description + ", " + dueDate + ", " + importance;
        tasks[tasks.length - 1] = task.split(",");
    }

    public static void readTasks(String fileName) {
        Path source = Paths.get(fileName);
        try {
            Scanner scanner = new Scanner(source);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                for (int i = 0; i < tasks.length; i++) {
                    tasks[tasks.length - 1] = line.split(",");
                }
            }
        } catch (IOException e) {
            System.out.println("Selected file does not exist!");
        }
    }

    public static void showOperation() {
        String[] operations = {"add", "remove", "list", "exit"};
        System.out.println();
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);

        for (String operation : operations) {
            System.out.println(operation);
        }


    }
}


