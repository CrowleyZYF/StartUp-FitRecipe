package cn.fitrecipe.android.entity;

/**
 * Created by wk on 2015/8/6.
 */
public class Component {

    private Ingredient ingredient;
    private String amount;
    private String remark;

    public int getMAmount() {
        return Integer.parseInt(amount);
    }

    public void setMAmount(int amount) {
        this.amount = String.valueOf(amount);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        if(remark == null || remark.length() == 0)
            remark = "--";
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
