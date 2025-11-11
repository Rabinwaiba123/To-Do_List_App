import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoApp {
    private static final String FILE_NAME = "tasks.txt";
    private static final List<String> tasks = new  ArrayList<>();

    private static void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String task;
            while ((task = reader.readLine()) != null){
                tasks.add(task);
            }
        }catch(IOException e){
            // File might not exist on first run; no problem
            System.out.println("No existing task file found. Starting fresh.");
        }
    }

    private static void saveTasks() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){
            for (String task:  tasks){
                writer.write(task);
                writer.newLine();
            }
            System.out.println("Tasks saved successfully.");
        }catch (IOException e){
            System.out.println("Error saving tasks:"+ e.getMessage());
        }
    }

    private static void runApp() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running){
            System.out.println("\n========== TO-DO LIST MENU ==========");
            System.out.println("1. View Tasks");
            System.out.println("2. Add Task");
            System.out.println("3. Delete Task");
            System.out.println("4. Exit");

            String choice = scanner.nextLine();

            switch (choice){
                case "1":
                    viewTask();
                    break;
                case "2":
                    addTask(scanner);
                    break;
                case "3":
                    deleteTask(scanner);
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");

            }
        }
        scanner.close();
    }

    private static void viewTask() {
        if(tasks.isEmpty()){
            System.out.println("No tasks in the list.");
        }else{
            System.out.println("\nYour Tasks:");
            for(int i = 0; i < tasks.size(); i++){
                System.out.println((i+1)+". " + tasks.get(i));

            }
        }
    }

    private static void addTask(Scanner scanner){
        System.out.print("Enter the task description: ");
        String task = scanner.nextLine().trim();
        if(!task.isEmpty()){
            tasks.add(task);
            System.out.print("Task added.");
        }else{
            System.out.println("Task cannot be empty.");
        }
    }

    private static void deleteTask(Scanner scanner){
        if(tasks.isEmpty()){
            System.out.print("No tasks to delete.");
            return;
        }

        viewTask();
        System.out.print("Enter the task number to delete: ");
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine());
            if(taskNumber > 0 && taskNumber <= tasks.size()){
                tasks.remove(taskNumber - 1);
                System.out.print("Task deleted.");

            }else{
                System.out.println("Invalid task number.");
            }
        }catch (NumberFormatException e){
            System.out.println("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        loadTasks(); // Load tasks from file when program starts
        runApp(); //Start user interaction
        saveTasks(); //Save tasks to file before exiting

    }
}
