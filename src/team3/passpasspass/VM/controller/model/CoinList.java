package team3.passpasspass.VM.controller.model;

import team3.passpasspass.VM.controller.CustomerPanel;

import java.util.ArrayList;

public class CoinList {
    private ArrayList<String[]> coins;
    CustomerPanel customerPanel;
    ChangeIterator changeIterator;

    public CoinList(CustomerPanel customerPanel){
        coins = ReadCSV.readCSV("./data/dwd_money_stat.csv");
        this.customerPanel = customerPanel;
        changeIterator = new ChangeIterator();
    }

//    public ArrayList<String[]> getCans(){
//        return cans;
//    }

    public void setNumber(int id, int num){
        coins.get(id)[1] = String.valueOf(num);
        notifyCoinNum();
    }

    public int getNumber(int id){
        return Integer.parseInt(coins.get(id)[1]);
    }

    public void notifyCoinNum(){
        customerPanel.updateCoinNum();
    }

    public ArrayList<String> returnChange(ArrayList<String> changeSol, ArrayList<Integer> coinsInfo, ArrayList<Integer> coinsNum, int change){
        changeSol = changeIterator.changeIterator(coinsInfo, coinsNum, change);
        return changeSol;//the last one[0] of changeSol is "true"/"false"
    }
}
