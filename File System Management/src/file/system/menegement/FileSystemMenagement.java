package file.system.menegement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileSystemMenagement {

    public static void main(String[] args) {

        System.out.println("Enter file operation command (LIST, DELETE, INFO, CREATE_DIR, RENAME, COPY or MOVE): ");

        try (BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));) {

            String s = bufferRead.readLine();
            s = s.toLowerCase();

            if ("list".equals(s)) {

                System.out.println("Command: LIST. Enter Absolute path: ");

                try (BufferedReader bufferRead1 = new BufferedReader(new InputStreamReader(System.in));) {

                    String list = bufferRead1.readLine();

                    File path = new File(list);

                    if (path.isAbsolute()) {
                        if (path.exists() && path.isDirectory()) {
                            String[] strings = path.list();
                            for (int i = 0; i < strings.length; i++) {
                                System.out.println(strings[i]);
                            }
                        }
                    } else {
                        System.out.println("Path is not absolute!");
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else if ("delete".equals(s)) {

                System.out.println("Command: DELETE. Enter absolute path: ");

                try (BufferedReader bufferRead1 = new BufferedReader(new InputStreamReader(System.in));) {

                    String del = bufferRead1.readLine();

                    File file = new File(del);
                    if (file.isAbsolute()) {
                        if (file.exists()) {
                            file.delete();
                            System.out.println("File successfully deleted!");
                        } else {
                            System.out.println("Cannot delete " + file.getName() + " because " + file.getName() + " does not exist.");
                        }
                    } else {
                        System.out.println("Path is not absolute!");
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else if ("rename".equals(s)) {

                System.out.println("Command: RENAME. Enter absolute path of old name file: ");
                try (BufferedReader bufferRead1 = new BufferedReader(new InputStreamReader(System.in));) {

                    String ren1 = bufferRead1.readLine();

                    System.out.println("Enter absolute path of new name file: ");

                    try (BufferedReader bufferRead2 = new BufferedReader(new InputStreamReader(System.in));) {

                        String ren2 = bufferRead2.readLine();

                        File oldfile = new File(ren1);
                        File newfile = new File(ren2);
                        if (oldfile.isAbsolute() && newfile.isAbsolute()) {
                            if (!oldfile.exists()) {
                                System.out.println("File doesn't exists!");
                                return;
                            }
                            if (newfile.exists()) {
                                System.out.println("File with desired name already exists!");
                                return;
                            }
                            if (oldfile.renameTo(newfile)) {
                                System.out.println("Rename succesful");
                            } else {
                                System.out.println("Rename failed");
                            }
                        } else {
                            System.out.println("Path is not absolute!");
                        }
                    }

                }

            } else if ("copy".equals(s)) {

                System.out.println("Command: COPY. Enter absolute path of file you want to copy: ");
                try (BufferedReader bufferRead1 = new BufferedReader(new InputStreamReader(System.in));) {
                	
                    String copy = bufferRead1.readLine();
                    System.out.println("Enter destination file: ");
                    try (BufferedReader bufferRead2 = new BufferedReader(new InputStreamReader(System.in));) {

                        String copy1 = bufferRead2.readLine();

                        File afile = new File(copy);
                        File bfile = new File(copy1);
                        if (afile.isAbsolute() && bfile.isAbsolute()) {
                            try (FileInputStream inStream = new FileInputStream(afile);
                                    FileOutputStream outStream = new FileOutputStream(bfile);) {

                                byte[] buffer = new byte[1024];

                                int length;

                                while ((length = inStream.read(buffer)) > 0) {

                                    outStream.write(buffer, 0, length);

                                }

                                System.out.println("File is copied successfuly!");

                            } catch (IOException exc) {
                                System.out.println(exc);
                            }
                        } else {
                            System.out.println("Pathe is not absolute!");
                        }
                    }
                }
            } else if ("move".equals(s)) {

                System.out.println("Command: MOVE.");

                String move = "";
                String dest = "";
                try (BufferedReader bufferRead1 = new BufferedReader(new InputStreamReader(System.in))) {
                    System.out.println("Enter file path: ");
                    move = bufferRead.readLine();

                    System.out.println("Enter destination folder: ");
                    dest = bufferRead.readLine();
                } catch (IOException e) {
                    System.out.println(e);
                }

                File file1 = new File(move);
                File file2 = new File(dest + "\\" + file1.getName());

                if (file1.isAbsolute() && file2.isAbsolute()) {
                    try (FileInputStream inStream = new FileInputStream(file1);
                            FileOutputStream outStream = new FileOutputStream(file2)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inStream.read(buffer)) > 0) {
                            outStream.write(buffer, 0, length);
                        }
                        System.out.println("File is moved successfuly!");

                        inStream.close();
                        outStream.close();

                        file1.delete();
                    } catch (IOException exc) {
                        System.out.println(exc);
                    }
                } else {
                    System.out.println("Path is not absolute");
                }
            } else if ("create_dir".equals(s)) {

                System.out.println("Command: CREATE_DIR. Enter absolute path for new directory: ");

                try (BufferedReader bufferRead1 = new BufferedReader(new InputStreamReader(System.in));) {
                    String dir = bufferRead1.readLine();

                    File newDir = new File(dir);

                    if (newDir.isAbsolute()) {
                        try {
                            if (!newDir.exists()) {
                                newDir.mkdir();
                                System.out.println("Created a directory called " + newDir.getName());
                            } else {
                                System.out.println("Directory called " + newDir.getName() + " already exists.");
                            }

                        } catch (Exception e) {
                            System.out.println("Couldn't create a directory called "
                                    + newDir.getName());
                        }
                    } else {
                        System.out.println("Path is not absolute!");
                    }
                }

            } else if ("info".equals(s)) {

                System.out.println("Command: INFO. Enter absolute path of file: ");

                try (BufferedReader bufferRead1 = new BufferedReader(new InputStreamReader(System.in));) {

                    String inf = bufferRead1.readLine();

                    File info = new File(inf);

                    if (info.isAbsolute()) {
                        if (info.exists()) {

                            System.out.println("File name is: " + info.getName());
                            System.out.println("File path is: " + info.getPath());
                            System.out.println("File length is: " + info.length());

                            Path path = Paths.get(inf);
                            BasicFileAttributes attr;
                            try {
                                attr = Files.readAttributes(path, BasicFileAttributes.class);
          
                                System.out.println("File creation date is: " + attr.creationTime());
                               
                            } catch (IOException e) {
                                System.out.println(e);
                            }

                            Instant instant = Instant.ofEpochMilli(info.lastModified());
                            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MM. yyyy. HH:mm:ss");
                            System.out.println("File last modified: " + dateTime.format(formatter));

                        }
                    } else {
                        System.out.println("Path is not absolute!");
                    }

                }

            } else {
                System.out.println("Invalid command!");
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
