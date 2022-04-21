import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private String directory;
    private Scanner console;

    {
        console = new Scanner(System.in);
    }

    public Menu() {
    }

    public void setDirectory(String directory) throws WrongPath{

        this.directory = directory;
        if(!Files.exists(Paths.get(this.directory)) || !Files.isDirectory(Paths.get(this.directory))) {
            throw new WrongPath();
        }
    }

    private void mvCommand(String fromPath, String toPath) throws WrongPath {
        Path from = Paths.get(fromPath);
        Path to = Paths.get(toPath);
        if (!Files.exists(from) || Files.isDirectory(from)) {
            throw new WrongPath();
        }
        if(to.getFileName().equals(to)) {
            File oldName = new File(fromPath);
            File newName = new File(toPath);
            oldName.renameTo(newName);
        } else {
            try {
                Files.move(from, to);
            }
            catch (java.io.IOException ioException) {
                System.out.println("Can't move file");
            }
        }
    }
    private void lsCommand() {
        File file = new File(directory);
        String[] fileList = file.list();
        for(String str : fileList) {
            System.out.println(str);
        }
    }

    private void cdCommand(String directory) throws WrongPath {
        Path destination = Paths.get(directory);
        if (!Files.exists(destination.toAbsolutePath()) || !Files.isDirectory(destination)) {
            throw new WrongPath();
        } else {
            this.directory = destination.toAbsolutePath().toString();
        }
    }
    public void startMenu() {
        String currLine = console.nextLine();
        while (!currLine.equals("exit")) {
            try
            {
                String args[] = currLine.split(" ");
                switch (args.length) {
                    case 1:
                        if (args[0].equals("ls")) {
                            lsCommand();
                        }
                        break;
                    case 2:
                        if (args[0].equals("cd")) {
                            cdCommand(args[1]);
                        }
                        break;
                    case 3:
                        if (args[0].equals("mv")) {
                            mvCommand(args[1], args[2]);
                        }
                        break;
                }
            }
            catch (WrongPath wrongPath) {
                System.out.println("Wrong path");
            }
            currLine = console.nextLine();
        }
        System.exit(-1);
    }
}