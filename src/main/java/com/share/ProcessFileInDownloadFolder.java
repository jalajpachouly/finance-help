package com.share;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.net.URL;

public class ProcessFileInDownloadFolder {
    static List<String> files =null;

    static {
        try {
             files = FileUtils.readLines(new File("c:\\DATA\\filenames.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void preProcess ()
    {
        // Delete existing directory
        // Download files from net
        // Pre process
        // Run the analysis and generate recommondation
        try {
            FileUtils.deleteDirectory(new File("C:\\DATA\\StockOptions"));
            List<String> directoryList = FileUtils.readLines(new File("c:\\DATA\\filenames.txt"));
            FileUtils.forceMkdir(new File("C:\\DATA\\StockOptions"));
            FileUtils.forceMkdir(new File("C:\\DATA\\StockOptions\\RawData"));
            FileUtils.forceMkdir(new File("C:\\DATA\\StockOptions\\ProcessedData"));

            for (String directory : directoryList) {
                String name="C:\\DATA\\StockOptions\\Results\\"+directory;
                FileUtils.forceMkdir(new File(name));
                name="C:\\DATA\\StockOptions\\RawData\\"+directory;
                FileUtils.forceMkdir(new File(name));
            }
            List<String> rawURLS = FileUtils.readLines(new File("c:\\DATA\\download_files.txt"));
            long endPeriod = Instant.now().getEpochSecond();
           long startPeriod =endPeriod - 157680000 ;
           // long startPeriod =endPeriod - 2630000;
            int fileCounter =0;
            for (String url : rawURLS) {
                url = rawURLS.get(fileCounter).replace("StartPeriod",Long.toString(startPeriod));
                url =url.replace("EndPeriod",Long.toString(endPeriod));
                try {
                    String name ="C:\\DATA\\StockOptions\\RawData\\"+files.get(fileCounter)+"\\"+files.get(fileCounter)+".csv";
                    downloadFile(new URL(url),name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fileCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        listFilesForFolder(new File("C:\\DATA\\StockOptions\\RawData"));
    }

    public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }
    public static void main (String [] args)
    {
        preProcess();
    }
    private static void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        }finally{
            sourceChannel.close();
            destChannel.close();
        }
    }


    public static void listFilesForFolder(final File folder) {
        StringBuffer data = new StringBuffer();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {

                try {
                    precessFile(fileEntry);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        }

    private static void precessFile(File fileEntry) throws IOException {
        StringBuffer buffer = new StringBuffer();
        File file = new File(fileEntry.getName());
        //creates a new file instance
        FileReader fr = new FileReader(fileEntry);   //reads the file
        BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
        StringBuffer sb = null;    //constructs a string buffer with no characters
        String line;
        int line_no =0;
        while ((line = br.readLine()) != null) {
            if(line_no==0){
                line_no++;
                continue;
            }
            if(line.contains("null")){
                continue;
            }
            String finalLine = line_no+","+line;
            buffer.append(finalLine).append("\n");
            line_no++;
        }
        write(buffer, fileEntry.getName());
    }

    public static void write(StringBuffer buffer,String fileName) {
        String file="C:\\DATA\\StockOptions\\ProcessedData\\"+fileName;
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(buffer.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

