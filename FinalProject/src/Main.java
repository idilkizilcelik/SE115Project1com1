import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    public static int[][][] profits;

    public static void loadData() {
        profits = new int[12][28][commodities.length];

        for (int i = 0; i < 12; i++) {
            Scanner reader = null;
            try {
                reader = new Scanner(Paths.get("Data_Files" + ".txt" + months[i]));
                while (reader.hasNextLine()) {
                    String line = reader.nextLine().trim();
                    String[] parts = line.split(",");
                    String dayStr = parts[0].trim();
                    String commStr = parts[1].trim();
                    String profitStr = parts[2].trim();
                    int day;
                    int profitValue;
                    try {
                        day = Integer.parseInt(dayStr);
                        profitValue = Integer.parseInt(profitStr);
                    } catch (NumberFormatException ex) {
                        continue;
                    }

                    int cIndex = commodityIndex(commStr);
                    profits[i][day - 1][cIndex] = profitValue;

                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (reader != null) reader.close();
            }


        }

    }
    public static int commodityIndex(String commodity) {
        if (commodity == null) {
            return -1;
        }
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(commodity)) {
                return i;
            }
        }
        return -1;
    }
    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month > 11 || profits == null){ return "INVALID_MONTH";}
            int bestSum = -99999;
            int bestIndex = 0;
            for (int i = 0; i < commodities.length; i++) {
                int sum = 0;
                for (int j = 0; j < 28; j++) {
                    sum += profits[month][j][i];
                }
                if (sum > bestSum) {
                    bestSum = sum;
                    bestIndex = i;
                }

            }
            return commodities[bestIndex] + bestSum;
        }
    public static int totalProfitOnDay(int month, int day){
        if(month <0 || month>11 || day<1 || day>28 )
            return -99999;
        int totalprofit=0;
        for( int i= 0; i<commodities.length; i++){
            totalprofit+= profits[month][day-1][i];
        }
        return totalprofit;
    }
    public static int commodityProfitInRange(String commodity, int from, int to) {
        int cIndex = commodityIndex(commodity);
        if (commodity == null || from < 1 || from > 28 || to < from )
            return -99999;
        int profitInRange = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = from - 1; i < to - 1; i++) {
                profitInRange = profits[i][j][cIndex];
            }
        }
        return profitInRange;
    }
    public static int bestDayOfMonth(int month){
        if(month<0 || month>11){return -1;}
        int bestday=1;
        int bestsum= Integer.MIN_VALUE;
        for(int i= 0 ; i<= 28 ; i++){
            int total = totalProfitOnDay(month, i);
            if(total>bestsum){
                total=bestsum;
                bestday= i;
            }
        }
        return bestday;

        }



    }


