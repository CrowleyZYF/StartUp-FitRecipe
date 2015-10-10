package cn.fitrecipe.android.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by wk on 2015/8/18.
 */

@DatabaseTable(tableName = "fr_report")
public class Report implements Serializable{
    //user info

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private int gender;
    @DatabaseField
    private int age;
    @DatabaseField
    private int height;
    @DatabaseField
    private double weight;
    @DatabaseField
    private String roughFat;
    @DatabaseField
    private double preciseFat;
    @DatabaseField
    private int daysToGoal;
    @DatabaseField
    private double weightGoal;

    //BaseInfo
    @DatabaseField
    private double BMI;                         //身体质量指数（BMI）
    @DatabaseField
    private double BMR;                         //基础代谢率（BMR）
    @DatabaseField
    private double SuggestFitFrequency;         //建议每周运动频率
    @DatabaseField
    private double SuggestFitTime;              //建议每次运动时长
    @DatabaseField
    private double CaloriesIntake;              //每日所需热量
    @DatabaseField
    private double CaloriesIntakeMin;           //每日所需热量下限
    @DatabaseField
    private double CaloriesIntakeMax;           //每日所需热量上限
    @DatabaseField
    private double SuggestFitCalories;          //每日运动量
    @DatabaseField
    private double SuggestFitCaloriesMin;       //每日运动量下限
    @DatabaseField
    private double SuggestFitCaloriesMax;       //每日运动量上限
    @DatabaseField
    private double BestWeight;                  //最佳体重
    @DatabaseField
    private double BestWeightMin;               //健康体重下限
    @DatabaseField
    private double BestWeightMax;               //健康体重上限
    @DatabaseField
    private double BurningRateMin;              //燃脂心率下限
    @DatabaseField
    private double BurningRateMax;              //燃脂心率上限


    //NutritionInfo
    @DatabaseField
    private double ProteinIntake;               //蛋白质摄入量
    @DatabaseField
    private double ProteinIntakeMin;            //蛋白质摄入量下限
    @DatabaseField
    private double ProteinIntakeMax;            //蛋白质摄入量上限
    @DatabaseField
    private double CarbohydrateIntake;
    @DatabaseField
    private double CarbohydrateIntakeMin;       //碳水化合物摄入量下限
    @DatabaseField
    private double CarbohydrateIntakeMax;       //碳水化合物摄入量上限
    @DatabaseField
    private double FatIntake;                   //脂类摄入量
    @DatabaseField
    private double FatIntakeMin;                //脂类摄入量下限
    @DatabaseField
    private double FatIntakeMax;                //脂类摄入量上限
    @DatabaseField
    private double WaterIntake;                 //水摄入量
    @DatabaseField
    private double WaterIntakeMin;              //水摄入量下限
    @DatabaseField
    private double WaterIntakeMax;              //水摄入量上限
    @DatabaseField
    private double FiberIntake;                 //纤维素摄
    @DatabaseField
    private double FiberIntakeMin;              //纤维素摄入量下限
    @DatabaseField
    private double FiberIntakeMax;              //纤维素摄入量上限
    @DatabaseField
    private double UnsaturatedFattyAcidsIntake; //不饱和脂肪酸摄
    @DatabaseField
    private double UnsaturatedFattyAcidsIntakeMin;//不饱和脂肪酸摄入量下限
    @DatabaseField
    private double UnsaturatedFattyAcidsIntakeMax;//不饱和脂肪酸摄入量上限
    @DatabaseField
    private double CholesterolIntake;           //胆固醇摄
    @DatabaseField
    private double CholesterolIntakeMin;        //胆固醇摄入量下限
    @DatabaseField
    private double CholesterolIntakeMax;        //胆固醇摄入量上限
    @DatabaseField
    private double SodiumIntakeMin;             //钠摄入量下限
    @DatabaseField
    private double SodiumIntakeMax;             //钠摄入量上限
    @DatabaseField
    private double VCIntakeMin;                 //维生素C摄入量下限
    @DatabaseField
    private double VCIntakeMax;                 //维生素C摄入量上限
    @DatabaseField
    private double VDIntakeMin;                 //维生素D摄入量下限
    @DatabaseField
    private double VDIntakeMax;                 //维生素D摄入量上限


    //DietStructure
    @DatabaseField
    private double DietStructureOilMin;         //油脂下限
    @DatabaseField
    private double DietStructureOilMax;         //油脂上限
    @DatabaseField
    private double DietStructureMeatMin;        //肉类下限
    @DatabaseField
    private double DietStructureMeatMax;        //肉类上限
    @DatabaseField
    private double DietStructureMilkMin;        //蛋奶下限
    @DatabaseField
    private double DietStructureMilkMax;        //蛋奶上限
    @DatabaseField
    private double DietStructureVegetableMin;   //蔬菜下限
    @DatabaseField
    private double DietStructureVegetableMax;   //蔬菜上限
    @DatabaseField
    private double DietStructureFruitMin;       //水果下限
    @DatabaseField
    private double DietStructureFruitMax;       //水果上限
    @DatabaseField
    private double DietStructureGrainMin;       //谷物下限
    @DatabaseField
    private double DietStructureGrainMax;       //谷物上限


    //OtherInfo
    @DatabaseField
    private double BreakfastRate;               //早餐摄入
    @DatabaseField
    private double SnackMorningRate;            //上午加餐摄入
    @DatabaseField
    private double LunchRate;                   //午餐摄入
    @DatabaseField
    private double SnackAfternoonRate;          //下午加餐摄入
    @DatabaseField
    private double DinnerRate;                  //晚餐摄入
    @DatabaseField
    private String updatetime;
    @DatabaseField
    private boolean goalType;


    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public double getBMR() {
        return BMR;
    }

    public void setBMR(double BMR) {
        this.BMR = BMR;
    }

    public double getSuggestFitFrequency() {
        return SuggestFitFrequency;
    }

    public void setSuggestFitFrequency(double suggestFitFrequency) {
        SuggestFitFrequency = suggestFitFrequency;
    }

    public double getSuggestFitTime() {
        return SuggestFitTime;
    }

    public void setSuggestFitTime(double suggestFitTime) {
        SuggestFitTime = suggestFitTime;
    }

    public double getCaloriesIntake() {
        return CaloriesIntake;
    }

    public void setCaloriesIntake(double caloriesIntake) {
        CaloriesIntake = caloriesIntake;
    }

    public double getCaloriesIntakeMin() {
        return CaloriesIntakeMin;
    }

    public void setCaloriesIntakeMin(double caloriesIntakeMin) {
        CaloriesIntakeMin = caloriesIntakeMin;
    }

    public double getCaloriesIntakeMax() {
        return CaloriesIntakeMax;
    }

    public void setCaloriesIntakeMax(double caloriesIntakeMax) {
        CaloriesIntakeMax = caloriesIntakeMax;
    }

    public double getSuggestFitCalories() {
        return SuggestFitCalories;
    }

    public void setSuggestFitCalories(double suggestFitCalories) {
        SuggestFitCalories = suggestFitCalories;
    }

    public double getSuggestFitCaloriesMin() {
        return SuggestFitCaloriesMin;
    }

    public void setSuggestFitCaloriesMin(double suggestFitCaloriesMin) {
        SuggestFitCaloriesMin = suggestFitCaloriesMin;
    }

    public double getSuggestFitCaloriesMax() {
        return SuggestFitCaloriesMax;
    }

    public void setSuggestFitCaloriesMax(double suggestFitCaloriesMax) {
        SuggestFitCaloriesMax = suggestFitCaloriesMax;
    }

    public double getBestWeight() {
        return BestWeight;
    }

    public void setBestWeight(double bestWeight) {
        BestWeight = bestWeight;
    }

    public double getBestWeightMin() {
        return BestWeightMin;
    }

    public void setBestWeightMin(double bestWeightMin) {
        BestWeightMin = bestWeightMin;
    }

    public double getBestWeightMax() {
        return BestWeightMax;
    }

    public void setBestWeightMax(double bestWeightMax) {
        BestWeightMax = bestWeightMax;
    }

    public double getBurningRateMin() {
        return BurningRateMin;
    }

    public void setBurningRateMin(double burningRateMin) {
        BurningRateMin = burningRateMin;
    }

    public double getBurningRateMax() {
        return BurningRateMax;
    }

    public void setBurningRateMax(double burningRateMax) {
        BurningRateMax = burningRateMax;
    }

    public double getProteinIntakeMin() {
        return ProteinIntakeMin;
    }

    public void setProteinIntakeMin(double proteinIntakeMin) {
        ProteinIntakeMin = proteinIntakeMin;
    }

    public double getProteinIntakeMax() {
        return ProteinIntakeMax;
    }

    public void setProteinIntakeMax(double proteinIntakeMax) {
        ProteinIntakeMax = proteinIntakeMax;
    }

    public double getCarbohydrateIntakeMin() {
        return CarbohydrateIntakeMin;
    }

    public void setCarbohydrateIntakeMin(double carbohydrateIntakeMin) {
        CarbohydrateIntakeMin = carbohydrateIntakeMin;
    }

    public double getCarbohydrateIntakeMax() {
        return CarbohydrateIntakeMax;
    }

    public void setCarbohydrateIntakeMax(double carbohydrateIntakeMax) {
        CarbohydrateIntakeMax = carbohydrateIntakeMax;
    }

    public double getFatIntakeMin() {
        return FatIntakeMin;
    }

    public void setFatIntakeMin(double fatIntakeMin) {
        FatIntakeMin = fatIntakeMin;
    }

    public double getFatIntakeMax() {
        return FatIntakeMax;
    }

    public void setFatIntakeMax(double fatIntakeMax) {
        FatIntakeMax = fatIntakeMax;
    }

    public double getWaterIntakeMin() {
        return WaterIntakeMin;
    }

    public void setWaterIntakeMin(double waterIntakeMin) {
        WaterIntakeMin = waterIntakeMin;
    }

    public double getWaterIntakeMax() {
        return WaterIntakeMax;
    }

    public void setWaterIntakeMax(double waterIntakeMax) {
        WaterIntakeMax = waterIntakeMax;
    }

    public double getFiberIntakeMin() {
        return FiberIntakeMin;
    }

    public void setFiberIntakeMin(double fiberIntakeMin) {
        FiberIntakeMin = fiberIntakeMin;
    }

    public double getFiberIntakeMax() {
        return FiberIntakeMax;
    }

    public void setFiberIntakeMax(double fiberIntakeMax) {
        FiberIntakeMax = fiberIntakeMax;
    }

    public double getUnsaturatedFattyAcidsIntakeMin() {
        return UnsaturatedFattyAcidsIntakeMin;
    }

    public void setUnsaturatedFattyAcidsIntakeMin(double unsaturatedFattyAcidsIntakeMin) {
        UnsaturatedFattyAcidsIntakeMin = unsaturatedFattyAcidsIntakeMin;
    }

    public double getUnsaturatedFattyAcidsIntakeMax() {
        return UnsaturatedFattyAcidsIntakeMax;
    }

    public void setUnsaturatedFattyAcidsIntakeMax(double unsaturatedFattyAcidsIntakeMax) {
        UnsaturatedFattyAcidsIntakeMax = unsaturatedFattyAcidsIntakeMax;
    }

    public double getCholesterolIntakeMin() {
        return CholesterolIntakeMin;
    }

    public void setCholesterolIntakeMin(double cholesterolIntakeMin) {
        CholesterolIntakeMin = cholesterolIntakeMin;
    }

    public double getCholesterolIntakeMax() {
        return CholesterolIntakeMax;
    }

    public void setCholesterolIntakeMax(double cholesterolIntakeMax) {
        CholesterolIntakeMax = cholesterolIntakeMax;
    }

    public double getSodiumIntakeMin() {
        return SodiumIntakeMin;
    }

    public void setSodiumIntakeMin(double sodiumIntakeMin) {
        SodiumIntakeMin = sodiumIntakeMin;
    }

    public double getSodiumIntakeMax() {
        return SodiumIntakeMax;
    }

    public void setSodiumIntakeMax(double sodiumIntakeMax) {
        SodiumIntakeMax = sodiumIntakeMax;
    }

    public double getVCIntakeMin() {
        return VCIntakeMin;
    }

    public void setVCIntakeMin(double VCIntakeMin) {
        this.VCIntakeMin = VCIntakeMin;
    }

    public double getVCIntakeMax() {
        return VCIntakeMax;
    }

    public void setVCIntakeMax(double VCIntakeMax) {
        this.VCIntakeMax = VCIntakeMax;
    }

    public double getVDIntakeMin() {
        return VDIntakeMin;
    }

    public void setVDIntakeMin(double VDIntakeMin) {
        this.VDIntakeMin = VDIntakeMin;
    }

    public double getVDIntakeMax() {
        return VDIntakeMax;
    }

    public void setVDIntakeMax(double VDIntakeMax) {
        this.VDIntakeMax = VDIntakeMax;
    }

    public double getDietStructureOilMin() {
        return DietStructureOilMin;
    }

    public void setDietStructureOilMin(double dietStructureOilMin) {
        DietStructureOilMin = dietStructureOilMin;
    }

    public double getDietStructureOilMax() {
        return DietStructureOilMax;
    }

    public void setDietStructureOilMax(double dietStructureOilMax) {
        DietStructureOilMax = dietStructureOilMax;
    }

    public double getDietStructureMeatMin() {
        return DietStructureMeatMin;
    }

    public void setDietStructureMeatMin(double dietStructureMeatMin) {
        DietStructureMeatMin = dietStructureMeatMin;
    }

    public double getDietStructureMeatMax() {
        return DietStructureMeatMax;
    }

    public void setDietStructureMeatMax(double dietStructureMeatMax) {
        DietStructureMeatMax = dietStructureMeatMax;
    }

    public double getDietStructureMilkMin() {
        return DietStructureMilkMin;
    }

    public void setDietStructureMilkMin(double dietStructureMilkMin) {
        DietStructureMilkMin = dietStructureMilkMin;
    }

    public double getDietStructureMilkMax() {
        return DietStructureMilkMax;
    }

    public void setDietStructureMilkMax(double dietStructureMilkMax) {
        DietStructureMilkMax = dietStructureMilkMax;
    }

    public double getDietStructureVegetableMin() {
        return DietStructureVegetableMin;
    }

    public void setDietStructureVegetableMin(double dietStructureVegetableMin) {
        DietStructureVegetableMin = dietStructureVegetableMin;
    }

    public double getDietStructureVegetableMax() {
        return DietStructureVegetableMax;
    }

    public void setDietStructureVegetableMax(double dietStructureVegetableMax) {
        DietStructureVegetableMax = dietStructureVegetableMax;
    }

    public double getDietStructureFruitMin() {
        return DietStructureFruitMin;
    }

    public void setDietStructureFruitMin(double dietStructureFruitMin) {
        DietStructureFruitMin = dietStructureFruitMin;
    }

    public double getDietStructureFruitMax() {
        return DietStructureFruitMax;
    }

    public void setDietStructureFruitMax(double dietStructureFruitMax) {
        DietStructureFruitMax = dietStructureFruitMax;
    }

    public double getDietStructureGrainMin() {
        return DietStructureGrainMin;
    }

    public void setDietStructureGrainMin(double dietStructureGrainMin) {
        DietStructureGrainMin = dietStructureGrainMin;
    }

    public double getDietStructureGrainMax() {
        return DietStructureGrainMax;
    }

    public void setDietStructureGrainMax(double dietStructureGrainMax) {
        DietStructureGrainMax = dietStructureGrainMax;
    }

    public double getBreakfastRate() {
        return BreakfastRate;
    }

    public void setBreakfastRate(double breakfastRate) {
        BreakfastRate = breakfastRate;
    }

    public double getSnackMorningRate() {
        return SnackMorningRate;
    }

    public void setSnackMorningRate(double snackMorningRate) {
        SnackMorningRate = snackMorningRate;
    }

    public double getLunchRate() {
        return LunchRate;
    }

    public void setLunchRate(double lunchRate) {
        LunchRate = lunchRate;
    }

    public double getSnackAfternoonRate() {
        return SnackAfternoonRate;
    }

    public void setSnackAfternoonRate(double snackAfternoonRate) {
        SnackAfternoonRate = snackAfternoonRate;
    }

    public double getDinnerRate() {
        return DinnerRate;
    }

    public void setDinnerRate(double dinnerRate) {
        DinnerRate = dinnerRate;
    }

    public double getProteinIntake() {
        return ProteinIntake;
    }

    public void setProteinIntake(double proteinIntake) {
        ProteinIntake = proteinIntake;
    }

    public double getFatIntake() {
        return FatIntake;
    }

    public void setFatIntake(double fatIntake) {
        FatIntake = fatIntake;
    }

    public double getWaterIntake() {
        return WaterIntake;
    }

    public void setWaterIntake(double waterIntake) {
        WaterIntake = waterIntake;
    }

    public double getFiberIntake() {
        return FiberIntake;
    }

    public void setFiberIntake(double fiberIntake) {
        FiberIntake = fiberIntake;
    }

    public double getUnsaturatedFattyAcidsIntake() {
        return UnsaturatedFattyAcidsIntake;
    }

    public void setUnsaturatedFattyAcidsIntake(double unsaturatedFattyAcidsIntake) {
        UnsaturatedFattyAcidsIntake = unsaturatedFattyAcidsIntake;
    }

    public double getCholesterolIntake() {
        return CholesterolIntake;
    }

    public void setCholesterolIntake(double cholesterolIntake) {
        CholesterolIntake = cholesterolIntake;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getRoughFat() {
        return roughFat;
    }

    public void setRoughFat(int roughFat) {
        if((gender == 0 && roughFat == 4) || (gender == 1 && roughFat == 4))
            this.roughFat = null;
        else
            this.roughFat = new String[]{"小于12%","12%~20%","20%~30%","30%以上", "20%以下","20%~30%","30~40%","40%以上"}[gender * 4 + roughFat];
    }

    public double getPreciseFat() {
        return preciseFat;
    }

    public void setPreciseFat(double preciseFat) {
        this.preciseFat = preciseFat;
    }

    public double getCarbohydrateIntake() {
        return CarbohydrateIntake;
    }

    public void setCarbohydrateIntake(double carbohydrateIntake) {
        CarbohydrateIntake = carbohydrateIntake;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public boolean isGoalType() {
        return goalType;
    }

    public void setGoalType(boolean goalType) {
        this.goalType = goalType;
    }

    public int getDaysToGoal() {
        return daysToGoal;
    }

    public void setDaysToGoal(int daysToGoal) {
        this.daysToGoal = daysToGoal;
    }

    public double getWeightGoal() {
        return weightGoal;
    }

    public void setWeightGoal(double weightGoal) {
        this.weightGoal = weightGoal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
