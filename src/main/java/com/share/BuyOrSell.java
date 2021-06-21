package com.share;

import org.apache.commons.io.FileUtils;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class BuyOrSell {

    static List<Stock> result = new ArrayList<Stock>();
    static SortedMap<Integer,SelectedStocks> selectedStocksList = new TreeMap<Integer,SelectedStocks>();
    static int highCount=0;
    static Double highValue=null;
    static Double prevHigh= 0D;
    static int qunatity = 0;
    static int sellFrequency =5;
    static int buyFrequency =1;

    static double sellPrice = 0D;
    static double buyPrice = 0D;
    static double profit = 0D;
    static double investment = 0D;
    static double totalProfit=0.0;
    static double reaminingSharePrice=0.0;
    static double totalBuyAmount=0.0;
    static double totalSellAmount=0.0;
    static int maxMoneyBlockTime=0;
    static String moneyBlockStartingDate =null;
    static String moneyBlockMaxDate =null;
    static double invsetAtLastSell=0.0;
    static double grossProfit =0.0;
    static double grossMaxInvetment =0.0;
    static double grossInvestedMoney =0.0;





    public static int getBuyQuantity() {
        return buyQuantity;
    }

    public static void setBuyQuantity(int buyQuantity) {
        BuyOrSell.buyQuantity = buyQuantity;
    }

    static int buyQuantity =1;

    static int lowCount=0;
    static Double lowValue=null;
    static Double prevLow= 0D;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static StringBuffer buffer = null;
    static String fileName =null;



    public static void main (String args[])
    {
        ProcessFileInDownloadFolder.preProcess();
        List<String> fileNames = null;
        try {
            fileNames = FileUtils.readLines(new File("c:\\DATA\\filenames.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> resultLocation = new ArrayList<String>();

            for (String fileName : fileNames) {
                String location = "C:\\DATA\\StockOptions\\Results\\" + fileName + "\\" + fileName + ".csv";
                resultLocation.add(location);
            }

        int i=0;
        for (String fileName : fileNames) {
            System.out.println("Precessing File :"+ resultLocation.get(i));
            String location =resultLocation.get(i);
                analyse(fileName,resultLocation.get(i));
                i++;
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(" Gross Total Profit :"+(grossProfit));
        System.out.println(" Gross Total Max investmant :"+(grossMaxInvetment));
        System.out.println(" Gross Total Money Still invested :"+(grossInvestedMoney));
        System.out.println("--------------------------------------------------------------------------");


      /*  int max=6;
       for(int i=1;i<max;i++) {
           sellFrequency = i;
           buyFrequency=1;
           analyse(folder);
      }
        for(int i=1;i<max;i++) {
            buyFrequency = i;
            sellFrequency=1;
            analyse(folder);
        }*/
    }

    private static void analyse(String fileName,String result) {
        initilize();
        preocessFile(fileName);
        analyseResults();
        finalResult();
        printReport(result);
    }

    private static void initilize() {
        buffer=new StringBuffer();
        result = new ArrayList<Stock>();
        selectedStocksList = new TreeMap<Integer,SelectedStocks>();
        highCount=0;
        highValue=null;
        prevHigh= 0D;
        qunatity = 0;
        sellPrice = 0D;
        buyPrice = 0D;
        profit = 0D;
        investment = 0D;
        reaminingSharePrice=0.0;
        totalProfit=0.0;
        totalBuyAmount=0.0;
        totalSellAmount=0.0;
        maxMoneyBlockTime=0;
        moneyBlockStartingDate =null;
        moneyBlockMaxDate =null;


    }
    public static void write(StringBuffer buffer,String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(buffer.toString());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void printReport(String result) {
        write(buffer,result);
    }

    private static void finalResult() {
        buffer.append("Date,BuyAmount,Total Buy Amount,Sell Amount,Total Sell Amount,Action,BuyQuantity," +
                "Sell Quantity,Total Quantity,Profit,Investment,Money Blocked Time," +
                "MoneyBlock Start Date,MoneyBlock EndDate,SellAtFrequency,PreviousInvestment").append("\n");
        int blockTime=0;
        double maxMoneyInvested =0.0;
        int count=0;
        int lastRecord =selectedStocksList.size();
        for (Map.Entry<Integer,SelectedStocks>  entry : selectedStocksList.entrySet()) {


            String buyOrSell =null;
            SelectedStocks st=entry.getValue();
            String message = null;
            message =st.getTime()+ ","+st.getBuyAmount() + ","+st.getTotalBuyAmount()+","+st.getSellAmount()+","+st.getTotalSellAmount() +","+st.getAction() +","+st.getBuyQunatity()+
                    ","+st.getSellQuantity()+","+st.getTotalQuantity()+","
            +st.getProfit()+","+st.getInvestment()+","+st.getMaxMoneyBlockTime()+","+
                    st.getMoneyBlockStartingDate()+","+st.getMoneyBlockMaxDate()+","
            + st.getSailedAtFrequency()+","+st.getPreviousInvetmentValue();
            totalProfit=totalProfit+st.getProfit();
            reaminingSharePrice=st.getInvestment();
            totalBuyAmount =totalBuyAmount+st.getTotalBuyAmount();
            totalSellAmount=totalSellAmount+st.getTotalSellAmount();
            if(st.getAction().equals("SELL")){
                invsetAtLastSell =st.getPreviousInvetmentValue();
            }

            if(st.getMaxMoneyBlockTime()>blockTime){
                blockTime=st.getMaxMoneyBlockTime();
                moneyBlockMaxDate=st.getMoneyBlockMaxDate();
                moneyBlockStartingDate=st.getMoneyBlockStartingDate();
            }
            if(st.getInvestment()>maxMoneyInvested){
                maxMoneyInvested=st.getInvestment();
            }
            count++;
            if(count==lastRecord){
                System.out.println("---------------Recommondation-------------------------------");
                System.out.println("Date,BuyAmount,Total Buy Amount,Sell Amount,Total Sell Amount,Action,BuyQuantity," +
                        "Sell Quantity,Total Quantity,Profit,Investment,Money Blocked Time," +
                        "MoneyBlock Start Date,MoneyBlock EndDate,SellAtFrequency,PreviousInvestment");
                System.out.println(message);

            }
           buffer.append(message).append("\n");
        }
        System.out.println("----------------------------------------------");
        buffer.append("SellFrequency :"+sellFrequency+" BuyFrequency :"+buyFrequency+ "\n");
        System.out.println("SellFrequency :"+sellFrequency+" BuyFrequency :"+buyFrequency);
        buffer.append( " Total Buy Amount :"+totalBuyAmount);
        System.out.println(" Total Buy Amount :"+(totalBuyAmount));
        buffer.append( " Total Sell Amount :"+totalSellAmount);
        System.out.println(" Total Sell Amount :"+totalSellAmount);
        buffer.append(" Total Profit :"+(totalProfit));
        buffer.append(" Max Money Block Time :"+blockTime);
        System.out.println(" Max Money Block Time  :"+blockTime);
        buffer.append(" Max Money Invested :"+maxMoneyInvested);
        System.out.println(" Max Money Invested  :"+maxMoneyInvested);

        buffer.append(" Max Money Block starting Date :"+moneyBlockStartingDate);
        System.out.println(" Max Money Block starting Date  :"+moneyBlockStartingDate);

        buffer.append(" Max Money Block End Date :"+moneyBlockMaxDate);
        System.out.println(" Max Money Block End Date  :"+moneyBlockMaxDate);
        System.out.println(" Profit at the last investment  :"+invsetAtLastSell);

        System.out.println("----------------------------------------------");
        System.out.println(" Total Profit :"+(totalProfit));
        System.out.println(" Remaining Money Invested :"+(reaminingSharePrice));
        grossProfit+=totalProfit;
        grossMaxInvetment+=maxMoneyInvested;
        grossInvestedMoney+=reaminingSharePrice;

    }

    public static void analyseResults(){
        for (Stock st : result) {
            if(!shouldIBuy(st)) {
                shouldIsell(st);
            }
        }
    }

    public static boolean shouldIsell(Stock st){
        boolean result =false;
        if(qunatity==0){
            return false;
        }
            //Double currValue=st.getHigh();
            Double currValue=st.getOpen();
            if(prevHigh==0){
                highValue=currValue;
                prevHigh =currValue;
            }
            else{
                if(currValue>=prevHigh){
                    highCount++;
                    prevHigh=currValue;
                }
                else{
                    prevHigh=currValue;
                    highCount=0;
                }
            }
            adjustSellFrequency();
            if(highCount>=sellFrequency ){
                highCount=0;
                prevHigh=currValue;
                createSellTransaction(st);
                result=true;
            }
        adjustSellFrequency();
        return result;

    }

    private static void adjustSellFrequency() {
        if(maxMoneyBlockTime==0){
            sellFrequency=5;
        }
        if(maxMoneyBlockTime>5 && sellFrequency<10){
            sellFrequency=4;

        }
        if(maxMoneyBlockTime>10 && sellFrequency<15){
            sellFrequency=3;

        }
        if(maxMoneyBlockTime>15 && sellFrequency<20){
            sellFrequency=2;

        }
        if(maxMoneyBlockTime>20 ){
            sellFrequency=1;

        }
        //System.out.println("Adjusting sell frequency to :"+sellFrequency);
    }

    private static void createSellTransaction(Stock st) {
        if(st.getOpen() * qunatity-investment<=0){
            return;
        }
        double sellingPrice = st.getOpen();
        SelectedStocks stSelected= new SelectedStocks();
        stSelected.setBuy(false);
        stSelected.setTime(st.getTime());
        stSelected.setSell(true);
        stSelected.setSellQuantity(qunatity);
        stSelected.setTotalSellAmount(sellingPrice * qunatity);
        stSelected.setInvestment(0.0);
        stSelected.setProfit(sellingPrice * qunatity-investment);
        selectedStocksList.put(st.getLineNo(),stSelected);
        stSelected.setLineNo(st.getLineNo());
        qunatity=0;
        stSelected.setTotalQuantity(qunatity);
        stSelected.setSellAmount(sellingPrice);
        stSelected.setBuyAmount(0.0);
        stSelected.setAction("SELL");
        maxMoneyBlockTime=0;
        stSelected.setMaxMoneyBlockTime(maxMoneyBlockTime);
        stSelected.setMoneyBlockMaxDate("-");
        stSelected.setMoneyBlockStartingDate("-");
        stSelected.setSailedAtFrequency(sellFrequency);
        stSelected.setPreviousInvetmentValue(investment);
        investment=0;

    }

    public static boolean shouldIBuy(Stock st){
        boolean result=false;
        {


                Double currValue=st.getOpen();

                if(prevLow==0){
                    lowValue=currValue;
                    prevLow =currValue;
                }
                else{
                    if(currValue<=prevLow){
                        lowCount++;
                        prevLow=currValue;
                    }
                    else{
                        prevLow=currValue;
                        lowCount=0;
                    }
                }
                if(lowCount>=buyFrequency){
                    lowCount=0;
                    prevLow=currValue;
                    createBuyTransaction(st);
                    result=true;
                }
        }
        return result;
    }

    private static void createBuyTransaction(Stock st) {
        SelectedStocks stSelected= new SelectedStocks();
        stSelected.setBuy(true);
        stSelected.setTime(st.getTime());
        stSelected.setAction("BUY");
        stSelected.setSell(false);
        stSelected.setBuyQunatity(st.getBuyQuantity());
        qunatity = qunatity + st.getBuyQuantity();
        stSelected.setInvestment(investment+st.getBuyQuantity()*st.getOpen());
        stSelected.setProfit(0.0);
        stSelected.setSellQuantity(0);
        stSelected.setLineNo(st.getLineNo());
        stSelected.setTotalQuantity(qunatity);
        stSelected.setTotalBuyAmount(st.getBuyQuantity()*st.getOpen());
        stSelected.setBuyAmount(st.getOpen());
        stSelected.setPreviousInvetmentValue(investment);
        investment=investment+st.getBuyQuantity()*st.getOpen();
        stSelected.setSellAmount(0.0);
        selectedStocksList.put(st.getLineNo(),stSelected);
        maxMoneyBlockTime=maxMoneyBlockTime+1;
        stSelected.setMaxMoneyBlockTime(maxMoneyBlockTime);
        stSelected.setMoneyBlockMaxDate(st.getTime());
        if(maxMoneyBlockTime==1) {
            stSelected.setMoneyBlockStartingDate(st.getTime());
            moneyBlockStartingDate=st.getTime();
        }
        else{
            stSelected.setMoneyBlockStartingDate(moneyBlockStartingDate);
        }
        stSelected.setSailedAtFrequency(0);


    }

    public static void preocessFile( String fileName) {
        StringBuffer data = new StringBuffer();
        fileName="C:\\DATA\\StockOptions\\ProcessedData"+"\\"+fileName+".csv";

                try {
                    File file = new File(fileName);
                    FileReader fr = new FileReader(file);   //reads the file
                    BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
                    String line;
                    int line_no =1;
                    while ((line = br.readLine()) != null) {
                        StringTokenizer delimate = new StringTokenizer(line, ",");
                        int count=0;
                        Stock st =new Stock();
                        st.setBuyQuantity(buyQuantity);
                        while (delimate.hasMoreElements()) {
                            String value = null;
                            value =(String)delimate.nextElement();
                            int year;
                            int date;
                            int month;
                            try {
                                switch (count) {
                                    case 0:
                                        st.setLineNo(Integer.parseInt(value));
                                        break;
                                    case 1:
                                        st.setTime(value);
                                        break;
                                    case 2:
                                        st.setOpen(new Double(value));
                                        break;
                                    case 3:
                                        st.setHigh(new Double(value));
                                        break;
                                    case 4:
                                        st.setLow(new Double(value));
                                        break;
                                    case 5:
                                        st.setClose(new Double(value));
                                        break;
                                    case 6:
                                        st.setAdj_Close(new Double(value));
                                        break;
                                    case 7:
                                        st.setVolume(new Double(value));
                                        break;

                                }
                                count++;
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }

                        }
                        if(st.getHigh()!=null){
                            result.add(st);
                        }
                    }


                    fr.close();
                    //writeToFile(fileEntry.getAbsolutePath(),data.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }
    }



