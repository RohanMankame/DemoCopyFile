public class Main {
    public static void main(String[] args)
    {

        String orignalLocation = "C:\\Users\\Rohan_nu3d8wo\\OneDrive\\Desktop\\SorcTestFolder"; // SET SOURCE FOLDER PATH HERE
        String destinationLocation = "C:\\Users\\Rohan_nu3d8wo\\OneDrive\\Desktop\\DestTestFolder"; //SET DESTINATION FOLDER PATH HERE
        Boolean transfered = false;

        Transfer transFile = new Transfer();

        transFile.getFiles(orignalLocation , destinationLocation);

        System.out.println("Done Program");

    }
}