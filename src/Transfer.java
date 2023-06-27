import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Transfer {

    int id = 0;

    public void getFiles(String sorcDirectory, String destDirectory)
    {
        boolean h = false;
        File orignalLocation = new File(sorcDirectory); //Creating a File object for directory
        String contentFiles[] = orignalLocation.list(); //List of all files and directories
        System.out.println("List of files and directories in the specified directory:");
        for (int i = 0; i < contentFiles.length; i++)
        {
            System.out.println(contentFiles[i]);
            String fileName = ("\\" + contentFiles[i]);
            transferFile( sorcDirectory + fileName , destDirectory + fileName );

        }

    }


    public void transferFile(String orignalLocation ,String destinationLocation)
    {
        // !!!TO-DO!!!
        File sourceFile = new File(orignalLocation);
        File destination = new File(destinationLocation);

        try //working case
        {
            Files.copy(sourceFile.toPath(), destination.toPath());
            id += 1;
            String Name = getName(orignalLocation);
            System.out.println("Successfully Transferred File");
            String fileDateTime = getFileDateTime(orignalLocation);
            String transDateTime = getTransDateTime();
            Boolean testSuccess = deleteFile(orignalLocation);
            int Success = 1;
            System.out.println("Successfully deleted File from source");
            SendData( id, Name, fileDateTime,  transDateTime,  Success);

        }
        catch (IOException e) //Failed case
        {
            String Name = getName(orignalLocation);
            System.out.println("Failed File Transfer");//throw new RuntimeException(e);
            String fileDateTime = getFileDateTime(orignalLocation);
            String transDateTime = getTransDateTime();
            int Success = 0;
            System.out.println("File still in the source, File has not been transferred");
            SendData( id, Name, fileDateTime,  transDateTime,  Success);


        }
    }

    public String getName(String orignalLocation)
    {
        File sourceFile = new File(orignalLocation);

        String Name = sourceFile.getName();
        return Name;

    }





    public String getFileDateTime(String orignalLocation)
    {
        //!!!TO-DO!!!
        File sourceFile = new File(orignalLocation);
        try {
            String creationTime = Files.getAttribute(Path.of(orignalLocation), "creationTime").toString();
            System.out.println("creationTime:" + creationTime);
            return creationTime;
        } catch (IOException ex) {
            // handle exception
            System.out.println("Failed to get date time of creation");
            return ("Failed");
        }
    }

    public String getTransDateTime()
    {
        //!!!TO-DO!!!
        DateTimeFormatter transDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd"+"T"+"HH:mm:ss");
        LocalDateTime now = LocalDateTime.now(); // current time
        String Time = (transDateTime.format(now));
        return Time;
    }


    public boolean deleteFile(String orignalLocation)
    {
        //!!!TO-DO!!!
        File sourceFile = new File(orignalLocation);
        sourceFile.delete(); // delete sorcFile
        System.out.println("Sorc file deleted");
        return true;

    }

    //timer()
    //!!!To-Do!!!

    public void SendData(int id, String Name,String fileDateTime, String transDateTime, int Success)
    {
        System.out.println("sending Data");
        Connection conn = null;
        Statement stmt = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to DB");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/filetransferdb","root","rohan");
            System.out.println("Connected");

            stmt = conn.createStatement();
            System.out.println("INSERT INTO filestransfered "+" VALUES( " + "'"+id+ "'" + " , "+ "'" +Name+ "'" + " , " +  "'" +fileDateTime+ "'"  +" , "+ "'" +transDateTime+ "'" +" , "+ "'" +Success+ "'" +" );");
            String sql = "INSERT INTO filestransfered"
                    + " VALUES( " + "'"+id+ "'" + " , "+ "'" +Name+ "'" + " , " +  "'" +fileDateTime+ "'"  +" , "+ "'" +transDateTime+ "'" +" , "+ "'" +Success+ "'" +" );";
            stmt.executeUpdate(sql);

            System.out.println("Record added");

        }  catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



}
