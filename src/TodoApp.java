import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TodoApp {
  private final static String FILE_NAME = "data.csv";
  
  public static void main(String[] args) {
    List<String> fileLines = readLinesFromFile();
    if (args.length == 0) {
      System.out.println("Todo application\n" +
              "=======================\n" +
              "\n" +
              "Command line arguments:\n" +
              " -l   Lists all the tasks\n" +
              " -a   Adds a new task\n" +
              " -r   Removes an task\n" +
              " -c   Completes an task");
    }
  
    if (args[0].equals("-l")) {
      if (args.length == 1) {
        if (fileLines.size() > 0) {
          for (int i = 0; i < fileLines.size(); i++)
            System.out.println(i + 1 + " - " + fileLines.get(i));
        } else if (fileLines.size() == 0) {
          System.out.println("No todos for today :)");
        }
      }
    }
  
    if (args[0].equals("-a")) {
      if (args.length == 1) {
        System.out.println("Unable to add: no task provided");
      } else if (args.length == 2) {
        fileLines.add("[ ] " + args[1]);
        writeToFile(fileLines);
      }
    }
  
    if (args[0].equals("-r")) {
      if (args.length == 1) {
        System.out.println("Unable to remove: no index provided");
      } else if (args.length == 2) {
        try
        {
          int givenIndex = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
          System.out.println("Unable to remove: index is not a number");
        }
        try {
          if (fileLines.size() < Integer.parseInt(args[1])) {
            System.out.println("Unable to remove: index is out of bounds");
          }
        } catch (IndexOutOfBoundsException e) {
          System.out.println("Unable to remove: index is not a number");
        }
      } else {
        fileLines.remove(Integer.parseInt(args[1]) - 1);
        writeToFile(fileLines);
      }
    }
    
    if (args[0].equals("-c")) {
      if (args.length == 1) {
        System.out.println("Unable to check: no index provided");
      } else if (args.length == 2) {
        String completedTask = fileLines.get(Integer.parseInt(args[1])).substring(2);
        fileLines.remove(Integer.parseInt(args[1]) - 1);
        fileLines.add("[x" + completedTask);
        writeToFile(fileLines);
      }
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
  
}
