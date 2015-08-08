package cn.fitrecipe.android.entity;

/**
 * Created by wk on 2015/8/6.
 */
public class Nutrition {

    private String name;
    private double amount;
    private String unit;

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}
