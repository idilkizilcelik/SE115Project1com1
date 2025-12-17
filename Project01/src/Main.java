public class Main{
    public static String [] months = {"January" , "Feburary", "March", "April", "May", "June" ,
            "July","August", "September" , "October" , "November", "December"};
    public static String [] commodities = { "Gold", "Wheat" , "Oil", "Silver", "Copper"};

    public static int [][][] profits; //data storing [month][day][commodityNumber]
    // there will be loadData part but since we didn't learn it I'm skipping this part till friday.

    //this part converts commodity value to a number for using into profits array part.
    public static int commodityNumber(String Commodity) {
        if (Commodity == null) {
            return -1;
        }
        for (int i = 0; i < commodities.length; i++) {
            if (commodities[i].equals(Commodity)) {
                return i;
            }
        }return -1;
    }
    public static String mostProfitableCommodityInMonth(int month){
        if(month<0 || month >11 || profits == null) {
            return "INVALID MONTH";
        }
        int bestCommodity= 0;
        int bestValue= -9999999;
        for ( int i=0 ; i<commodities.length ; i++){
            int sum = 0;
            for(int j = 0 ; j<28 ; j++){
            sum += profits[month][i][j];
        }
            if( sum > bestValue){
                bestValue= sum;
                bestCommodity = i;
            }

            }
        return commodities[bestCommodity]+ " " + bestValue;
    }
    public static int totalProfitOnDay(int day , int month){
        if(day<0 || day>28){
            return -99999;
        }
        if(month<0 || month >11){
            return -99999;
        }
        if(profits == null){
            return -99999;
        }
        int dayNumber= day-1;
        int total=0;
        for(int i=0 ; i<commodities.length ; i++){
            total += profits[month][dayNumber][i];
        }
        return total;
    }
    public static int


}