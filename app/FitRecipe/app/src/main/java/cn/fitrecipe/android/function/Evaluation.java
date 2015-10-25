package cn.fitrecipe.android.function;
/**
 * @author Frank
 *
 */

import cn.fitrecipe.android.entity.Report;


public class Evaluation {

	/**
	 * @param gender: int, use MALE and FEMALE
	 * @param age: int, should elder than 18
	 * @param height: int, cm
	 * @param weight: double, kg
     * @param goalType: 0 for gain muscle, 1 for lose weight
     * @param preciseFat: fat, 0.18
     * @param jobType: 0, 1, 2, 3 for tight, medium, high and very high work intensity
     * @param exerciseFrequency: 0 for 1~2, 1 for 3~5, 2 for 6~7, 3 for more than 7
     * @param exerciseInterval: 0, 1, 2, 3; 0 for 0 ~ 30min, 1 for 30 ~ 60min, 2 for 60 ~ 90min, 3 for more than 90min
     * @param exerciseTime: 1 for before breakfast, 2 for before lunch, 3 for before supper, 4 for after supper
	 * @param weightGoal: it should always be positive
	 * @param daysToGoal: how many days the user want
	 */
	
	private int gender;
	private int age;
	private int height;
	private double weight;
	private int goalType;
    private double preciseFat;
    private int jobType;
    private int exerciseFrequency;
    private int exerciseInterval;
    private int exerciseTime;
	private double weightGoal;
	private int daysToGoal;
	
	private static final int INVALID = -1;
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static final int GAINMUSCLE = 0;
	public static final int LOSEWEIGHT = 1;
    private static final double[] BMRRate1 = {1.375, 1.55, 1.725, 1.9};
    private static final double[][] BMRRate2 = {
            {1.55, 1.78, 2.1},
            {1.56, 1.64, 1.8}};

    private int calorieWeight = -1;
    private static final int[] calorie = {0, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400, 10000};
    //谷物、蔬菜、肉类、奶、水果、油脂
    private static final int[] calorieIntakeUnit = {25, 500, 50, 80, 200, 10};
    private static final double[][] calorieIntake = {
            {6, 1, 2, 2, 0, 1},
            {6, 1, 2, 2, 0, 1},
            {7, 1, 3, 2, 0, 1.5},
            {9, 1, 3, 2, 0, 1.5},
            {9, 1, 4, 2, 1, 1.5},
            {11, 1, 4, 2, 1, 2},
            {13, 1, 4.5, 2, 1, 2},
            {15, 1, 4.5, 2, 1, 2},
            {17, 1, 5, 2, 1, 2},
            {17, 1, 5, 2, 1, 2}
    };

    public Evaluation (int gender, int age, int height, double weight, double preciseFat, int jobType, int goalType, int exerciseFrequency, int exerciseInterval) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.preciseFat = preciseFat;
        this.jobType = jobType;
        this.goalType = goalType;
        this.exerciseFrequency = exerciseFrequency;
        this.exerciseInterval = exerciseInterval;
        this.exerciseTime = INVALID;
        this.weightGoal = INVALID;
        this.daysToGoal = INVALID;
    }

    public void setExerciseTime(int exerciseTime){
        this.exerciseTime = exerciseTime;
    }

    public void setWeightGoal(double weightGoal){
        this.weightGoal = weightGoal;
    }

    public void setDaysToGoal(int daysToGoal){
        this.daysToGoal = daysToGoal;
    }

    public int getGoalType(){
        return this.goalType;
    }
	
	
	/**
	 * @return BMI
	 */
	private double getBMI() {
		return weight / (((double)height / 100) * ((double)height / 100));
	}

    /**
     * @return BMR
     */
    private int getBMR(){
        if (preciseFat == -1){
            //如果没有精确体脂，那么使用Mifflin公式
            int s = gender==MALE?5:-161;
            return (int) (10*weight + 6.25*height - 5*age + s);
        }else {
            //如果有精确体脂，那么使用Katch-McArdle公式
            return (int) (370 + 21.6 * (weight*(1-preciseFat)));
        }
    }

    /**
     * @return TDEE, Total Daily Energy Expenditure
     */
    private int getTDEE(){
        int TDEE1 = (int) (getBMR() * BMRRate1[exerciseFrequency]);
        int TDEE2 = (int) (getBMR() * BMRRate2[gender][jobType]);
        return TDEE1>=TDEE2 ? TDEE1:TDEE2;
    }

    /**
     * @return BurningRate
     */
    private int[] getBurningRate(){
        double HRmax;
        int[] result = new int[2];
        if (getBMI() > 24)
            HRmax = 200 - 0.5 * age;
        else
            HRmax = 208 - 0.7 * (double)age;
        result[0] = (int) (0.6 * HRmax);
        result[1] = (int) (0.9 * HRmax);
        return result;
    }
	
	/**
	 * @return number 0 of result is the low boundary of body weight, number 1 of result is the high
	 */
	public double[] getWeightBoundary() {
		double[] result = new double[3];
		// BMI from 18.5 to 24 is healthy
		double heightInM = (double)height / 100;
		result[0] = 18.5 * (heightInM * heightInM);
		result[1] = 24 * (heightInM * heightInM);
        result[2] = (result[0]+result[1])/2;
		return result;
	}
	
	/**
	 * @return it not precise !!!
	 */
	public int getShortestDaysToGoal() {
		// for lose weight, the delta is -700kcal/day
		// for gain muscle, the delta is +1000kcal/day
		double delta;
		if (goalType == GAINMUSCLE) {
			delta = 1000;
		} else {
			delta = -700;
		}
		return (int)(((weightGoal - weight) * 7200) / delta);
	}

    /**
     * @return weekTarget
     */
    public double getWeekTarget(){
        return (7 * (getWeightGoal() - getWeight()) / getDaysToGoal());
    }

    /**
     * @return suggestCalorie
     */
    public int[] getSuggestCalorie(){
        int[] result = new int[3];
        if(Math.abs(getWeekTarget())<0.5){
            //稳健型，减脂85%~90%，增肌105%~110%
            if (getGoalType()==GAINMUSCLE){
                result[0] = (int) (getTDEE() * 1.05);
                result[1] = (int) (getTDEE() * 1.1);
                result[2] = (result[0]+result[1])/2;
            }else{
                result[0] = (int) (getTDEE() * 0.85);
                result[1] = (int) (getTDEE() * 0.9);
                result[2] = (result[0]+result[1])/2;
            }
        }else{
            //激进型，减脂80%~85%，增肌110%~115%
            if (getGoalType()==GAINMUSCLE){
                result[0] = (int) (getTDEE() * 1.1);
                result[1] = (int) (getTDEE() * 1.15);
                result[2] = (result[0]+result[1])/2;
            }else{
                result[0] = (int) (getTDEE() * 0.8);
                result[1] = (int) (getTDEE() * 0.85);
                result[2] = (result[0]+result[1])/2;
            }
        }
        return result;
    }

    /**
     * @return suggestSport
     */
    public int[] getSuggestSport(){
        int[] result = new int[3];
        result[0] = (int) (getTDEE() * 0.2);
        result[1] = (int) (getTDEE() * 0.3);
        result[2] = (result[0]+result[1])/2;
        return result;
    }

    /**
     * @return exerciseTimeexerciseFrequency
     */
    public int getReportExerciseTime(){
        if(Math.abs(getWeekTarget())<0.5){
            if (getGoalType()==GAINMUSCLE){
                return 40;
            }else{
                return 50;
            }
        }else{
            if (getGoalType()==GAINMUSCLE){
                return 60;
            }else{
                return 70;
            }
        }
    }

    /**
     * @return exerciseFrequency
     */
    public int getReportExerciseFrequency(){
        if(Math.abs(getWeekTarget())<0.5){
            return 3;
        }else{
            return 5;
        }
    }

    /**
     * @return Protein
     */
    public int[] getProtein(){
        double temp;
        int[] result = new int[3];
        if(Math.abs(getWeekTarget())<0.5){
            if (getGoalType()==GAINMUSCLE){
                temp = 1.8 * weight;
            }else{
                temp = 1.5 * weight;
            }
        }else{
            if (getGoalType()==GAINMUSCLE){
                temp = 2.75 * weight;
            }else{
                temp = 1.8 * weight;
            }
        }
        result[0] = (int) (0.9 * temp);
        result[1] = (int) (1.1 * temp);
        result[2] = (int) temp;
        return result;
    }

    /**
     * @return Fat
     */
    public int[] getFat(){
        double temp;
        int[] result = new int[3];
        if (getGoalType()==GAINMUSCLE){
            temp = 1 * weight;
        }else{
            temp = 0.8 * weight;
        }
        result[0] = (int) (0.9 * temp);
        result[1] = (int) (1.1 * temp);
        result[2] = (int) temp;
        return result;
    }

    /**
     * @return Carbohydrate
     */
    public int[] getCarbohydrate(){
        double calorieLeft = (getSuggestCalorie()[2]-(getProtein()[0]+getProtein()[1])/2*4-(getFat()[0]+getFat()[1])/2*9)/4;
        int[] result = new int[3];
        result[0] = (int) (0.9 * calorieLeft);
        result[1] = (int) (1.1 * calorieLeft);
        result[2] = (int) (calorieLeft);
        return result;
    }
	
	public Report report(String update) {
		final String NotSure = "ToBeContinued";
		Report report = new Report();
		report.setGender(gender);
		report.setAge(age);
		report.setHeight(height);
		report.setWeight(weight);
		report.setGoalType(goalType);
        report.setWeightGoal(weightGoal);
        report.setDaysToGoal(daysToGoal);
		report.setPreciseFat(preciseFat);
		report.setUpdatetime(update);

		//BMI
		report.setBMI(getBMI());
		//BMR
		report.setBMR(getBMR());
        //TDEE
        report.setTDEE(getTDEE());
        //BurningRate
        report.setBurningRateMin(getBurningRate()[0]);
        report.setBurningRateMax(getBurningRate()[1]);
		//Weight
		report.setBestWeight(getWeightBoundary()[2]);
		report.setBestWeightMin(getWeightBoundary()[0]);
		report.setBestWeightMax(getWeightBoundary()[1]);

        //Intake
        report.setCaloriesIntake(getSuggestCalorie()[2]);
        report.setCaloriesIntakeMin(getSuggestCalorie()[0]);
        report.setCaloriesIntakeMax(getSuggestCalorie()[1]);
        //Sport
        report.setSuggestFitCalories(getSuggestSport()[2]);
        report.setSuggestFitCaloriesMin(getSuggestSport()[0]);
        report.setSuggestFitCaloriesMax(getSuggestSport()[1]);
		//frequency
        report.setSuggestFitFrequency(getReportExerciseFrequency());
		//interval
		report.setSuggestFitTime(getReportExerciseTime());

        //water intake, from USDA
        report.setWaterIntakeMax((3.7 - gender) * 1.1);
        report.setWaterIntakeMin((3.7 - gender) * 0.9);
        report.setWaterIntake((3.7 - gender));
        //protein
		report.setProteinIntake(getProtein()[2]);
		report.setProteinIntakeMin(getProtein()[0]);
		report.setProteinIntakeMax(getProtein()[1]);
        //Carbohydrate
		report.setCarbohydrateIntake(getCarbohydrate()[2]);
		report.setCarbohydrateIntakeMin(getCarbohydrate()[0]);
		report.setCarbohydrateIntakeMax(getCarbohydrate()[1]);
        //Fat
		report.setFatIntake(getFat()[2]);
        report.setFatIntakeMin(getFat()[0]);
		report.setFatIntakeMax(getFat()[1]);
		//fiber intake is constant? more than 25g/day
		report.setFiberIntake(25);
		report.setFiberIntakeMax(27.5);
		report.setFiberIntakeMin(22.5);
		//unsaturated fatty acids intake, constant, 250mg/day
		report.setUnsaturatedFattyAcidsIntake(25);
		report.setUnsaturatedFattyAcidsIntakeMax(27.5);
		report.setUnsaturatedFattyAcidsIntakeMin(22.5);
		//cholesterol less than 300mg/day, not sure
		report.setCholesterolIntakeMax(330);
		report.setCholesterolIntakeMin(270);
		report.setCholesterolIntake(300);
		//sodium intake, from USDA
		//ALSodium for Adequate intakes and ULSodium for max level, g/day
		double ALSodium = 0;
		double ULSodium = 0;
		if (age < 50) {
			ALSodium = 1.5;
			ULSodium = 2.3;
		} else if (age > 51 && age < 70) {
			ALSodium = 1.3;
			ULSodium = 2.3;
		} else {
			ALSodium = 1.2;
			ULSodium = 2.3;
		}
		report.setSodiumIntakeMax(ULSodium*1000);
		report.setSodiumIntakeMin(ALSodium*1000);
		//VC intake, from USDA, mg/day
		double ALVC = 90;
		double ULVC = 2000;
		report.setVCIntakeMin((ALVC - gender * 15));
		report.setVCIntakeMax(ULVC);
		//VD intake, from USDA, IU/day
		double ALVD = 7.5 ;
		double ULVD = 10;
		report.setVDIntakeMin(ALVD);
		report.setVDIntakeMax(ULVD);
		
		//MealRate
		report.setBreakfastRate((0.25 + (exerciseTime==1?0.05:0)) * getSuggestCalorie()[2]);
		report.setSnackMorningRate(0.05 * getSuggestCalorie()[2]);
		report.setLunchRate((0.3 + (exerciseTime==2?0.05:0)) * getSuggestCalorie()[2]);
		report.setSnackAfternoonRate(0.05 * getSuggestCalorie()[2]);
		report.setDinnerRate((0.25 + (exerciseTime==3?0.05:0)) * getSuggestCalorie()[2]);
		report.setSnackNightRate((0.05 + (exerciseTime==4?0.05:0)) * getSuggestCalorie()[2]);

        for(int i = 0; i < calorie.length; i++){
            if(getSuggestCalorie()[2]>calorie[i]&&getSuggestCalorie()[2]<calorie[i+1]){
                calorieWeight = i;
                i = calorie.length+2;
            }
        }

        //谷物、蔬菜、肉类、奶、水果、油脂
        report.setDietStructureOilMin(0.9 * calorieIntake[calorieWeight][5] * calorieIntakeUnit[5]);
        report.setDietStructureOilMax(1.1 * calorieIntake[calorieWeight][5] * calorieIntakeUnit[5]);
        report.setDietStructureMeatMin(0.9 * calorieIntake[calorieWeight][2] * calorieIntakeUnit[2]);
        report.setDietStructureMeatMax(1.1 * calorieIntake[calorieWeight][2] * calorieIntakeUnit[2]);
        report.setDietStructureMilkMin(0.9 * calorieIntake[calorieWeight][3] * calorieIntakeUnit[3]);
        report.setDietStructureMilkMax(1.1 * calorieIntake[calorieWeight][3] * calorieIntakeUnit[3]);
        report.setDietStructureVegetableMin(0.9 * calorieIntake[calorieWeight][1] * calorieIntakeUnit[1]);
        report.setDietStructureVegetableMax(1.1 * calorieIntake[calorieWeight][1] * calorieIntakeUnit[1]);
        report.setDietStructureFruitMin(0.9 * calorieIntake[calorieWeight][4] * calorieIntakeUnit[4]);
        report.setDietStructureFruitMax(1.1 * calorieIntake[calorieWeight][4] * calorieIntakeUnit[4]);
        report.setDietStructureGrainMin(0.9 * calorieIntake[calorieWeight][0] * calorieIntakeUnit[0]);
        report.setDietStructureGrainMax(1.1 * calorieIntake[calorieWeight][0] * calorieIntakeUnit[0]);

        return report;
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

    public int isGoalType() {
        return goalType;
    }

    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    public double getWeightGoal() {
        return weightGoal;
    }

    public int getDaysToGoal() {
        return daysToGoal;
    }

    public double getPreciseFat() {
        return preciseFat;
    }

    public void setPreciseFat(double preciseFat) {
        this.preciseFat = preciseFat;
    }

    public int getJobType() {
        return jobType;
    }

    public void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public int getExerciseFrequency() {
        return exerciseFrequency;
    }

    public void setExerciseFrequency(int exerciseFrequency) {
        this.exerciseFrequency = exerciseFrequency;
    }

    public int getExerciseInterval() {
        return exerciseInterval;
    }

    public void setExerciseInterval(int exerciseInterval) {
        this.exerciseInterval = exerciseInterval;
    }
}
