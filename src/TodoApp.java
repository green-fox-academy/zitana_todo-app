import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoApp {
  private final static String FILE_NAME = "data.csv";
  
  public static void main(String[] args) {
    List<String> todoList = readLinesFromFile();
    
    if (args.length == 0) {
        userManual();
        return;
    }
    
    if (isValidArgument(args[0])) {
  
      if (args[0].equals("-l")) {
        listTasks(args, todoList);
      } else if (args[0].equals("-a")) {
        addTask(args, todoList);
      } else if (args[0].equals("-r")) {
        removeTask(args, todoList);
      } else if (args[0].equals("-c")) {
        completeTask(args, todoList);
      }
      
    } else {
      System.out.println("Unsupported argument");
    }
    
  }
  
  private static List<String> readLinesFromFile() {
    Path path = Paths.get(FILE_NAME);
    List<String> rawLines;
    
    try {
      rawLines = Files.readAllLines(path);
    } catch (IOException e) {
      e.printStackTrace();
      rawLines = new ArrayList<>();
    }
    
    return rawLines;
  }
  
  private static void writeToFile(List<String> data) {
    Path path = Paths.get(FILE_NAME);
    try {
      Files.write(path, data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static boolean isValidArgument(String argument) {
    List<String> arguments = new ArrayList<>(Arrays.asList("-l", "-r", "-a", "-c"));
      return (arguments.contains(argument));
  }
  
  
  private static void userManual() {
    System.out.println("Todo application\n" +
            "=======================\n" +
            "\n" +
            "Command line arguments:\n" +
            " -l   Lists all the tasks\n" +
            " -a   Adds a new task\n" +
            " -r   Removes an task\n" +
            " -c   Completes an task");
  }
  
  private static void printTodoList(List dataFromFile) {
    for (int i = 0; i < dataFromFile.size(); i++) {
      System.out.println(i + 1 + " - " + dataFromFile.get(i));
    }
  }
  
  private static void listTasks(String[] args, List<String> todoList) {
    if (args.length == 1) {
      if (todoList.size() > 0) {
        printTodoList(todoList);
      } else if (todoList.size() == 0) {
        System.out.println("No todos for today :)");
      }
    }
  }
  
  private static void addTask(String[] args, List<String> todoList) {
    if (args.length == 1) {
      System.out.println("Unable to add: no task provided");
    } else if (args.length == 2) {
      todoList.add("[ ] " + args[1]);
      writeToFile(todoList);
    }
  }
  private static void removeTask(String[] args, List<String> todoList) {
    if (args.length == 1) {
      System.out.println("Unable to remove: no index provided");
    } else if (args.length == 2) {
      try {
        todoList.remove(Integer.parseInt(args[1]) - 1);
        writeToFile(todoList);
      } catch (Exception ex) {
        if (ex instanceof NumberFormatException) {
          System.out.println("Unable to remove: index is not a number");
        } else if (ex instanceof IndexOutOfBoundsException) {
          System.out.println("Unable to remove: index is out of bounds");
        }
      }
    }
  }
  
  private static void completeTask(String[] args, List<String>todoList) {
    if (args.length == 1) {
      System.out.println("Unable to check: no index provided");
    } else if (args.length == 2) {
      try {
        int indexOfTask = Integer.parseInt(args[1]);
        String completedTask = todoList.get(indexOfTask).substring(2);
        todoList.remove(indexOfTask - 1);
        todoList.add("[x" + completedTask);
        writeToFile(todoList);
      } catch (Exception ex) {
        if (ex instanceof NumberFormatException) {
          System.out.println("Unable to check: index is not a number");
        } else if (ex instanceof IndexOutOfBoundsException) {
          System.out.println("Unable to check: index is out of bounds");
        }
      }
    }
  }
}
