package com.share;


import java.util.Calendar;

public class SelectedStocks {


    Double sellAmount;
    Double buyAmount;
    Double profit;
    Double investment;
    int sellQuantity;
    int buyQunatity;
    int lineNo;
    String time;
    boolean sell = false;
    boolean buy = false;
    double totalBuyAmount;
    double totalSellAmount;
    int totalQuantity;
    String action;
    int maxMoneyBlockTime;
    String moneyBlockStartingDate = null;
    String moneyBlockMaxDate = null;
    int sailedAtFrequency = 0;
    double previousInvetmentValue =0.0;

    public double getPreviousInvetmentValue() {
        return previousInvetmentValue;
    }

    public void setPreviousInvetmentValue(double previousInvetmentValue) {
        this.previousInvetmentValue = previousInvetmentValue;
    }


    public int getSailedAtFrequency() {
        return sailedAtFrequency;
    }

    public void setSailedAtFrequency(int sailedAtFrequency) {
        this.sailedAtFrequency = sailedAtFrequency;
    }


    public Double getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Double sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Double getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(Double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getInvestment() {
        return investment;
    }

    public void setInvestment(Double investment) {
        this.investment = investment;
    }

    public int getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(int sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public int getBuyQunatity() {
        return buyQunatity;
    }

    public void setBuyQunatity(int buyQunatity) {
        this.buyQunatity = buyQunatity;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSell() {
        return sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public double getTotalBuyAmount() {
        return totalBuyAmount;
    }

    public void setTotalBuyAmount(double totalBuyAmount) {
        this.totalBuyAmount = totalBuyAmount;
    }

    public double getTotalSellAmount() {
        return totalSellAmount;
    }

    public void setTotalSellAmount(double totalSellAmount) {
        this.totalSellAmount = totalSellAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getMaxMoneyBlockTime() {
        return maxMoneyBlockTime;
    }

    public void setMaxMoneyBlockTime(int maxMoneyBlockTime) {
        this.maxMoneyBlockTime = maxMoneyBlockTime;
    }

    public String getMoneyBlockStartingDate() {
        return moneyBlockStartingDate;
    }

    public void setMoneyBlockStartingDate(String moneyBlockStartingDate) {
        this.moneyBlockStartingDate = moneyBlockStartingDate;
    }

    public String getMoneyBlockMaxDate() {
        return moneyBlockMaxDate;
    }

    public void setMoneyBlockMaxDate(String moneyBlockMaxDate) {
        this.moneyBlockMaxDate = moneyBlockMaxDate;
    }



}