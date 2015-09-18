package cn.fitrecipe.android.function;
/**
 * @author Frank
 *
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.fitrecipe.android.entity.Report;


public class Evaluation {

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

	public int getRoughFat() {
		return roughFat;
	}

	public void setRoughFat(int roughFat) {
		this.roughFat = roughFat;
	}

	public boolean isGoalType() {
		return goalType;
	}

	public void setGoalType(boolean goalType) {
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

	/**
	 * @param gender: int, use MALE and FEMALE
	 * @param age: int, should elder than 18
	 * @param height: int, cm
	 * @param weight: double, kg
	 * @param roughFat: input the rough body fat, choose from pictures, 0 to 6
     * @param preciseFat: -1 for not sure
     * @param jobType: 0, 1, 2, 3, 4 for tight, medium, high and very high work intensity, 0 for not sure
	 * @param goalType: true for gain muscle, false for lose weight
     * @param exerciseFrequency: 0, 1, 2, 3, 4, 5, 6, 7, 8 times/week, 0 for not sure, 8 for more than 7 times/week
     * @param exerciseInterval: 0, 1, 2, 3. 0 for not sure, 1 for 0 ~ 30min, 2 for 30 ~ 60min, 3 for more than 30min. it has to be finished if the exerciseFrequency is not empty
     * @param exerciseTime: 1 for before breakfast, 2 for before lunch, 3 for before supper, 4 for after supper
	 * @param weightGoal: it should always be positive
	 * @param daysToGoal: how many days the user want
	 */
	
	private int gender;
	private int age;
	private int height;
	private double weight;
	private int roughFat;
	private boolean goalType;
	private double weightGoal;
	private int daysToGoal;
	//parameters above this must be filled
	private double preciseFat;
	private int jobType;
	private int exerciseFrequency;
	private int exerciseInterval;
    private int exerciseTime;
	
	private static final int INVALID = -1;
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static final boolean GAINMUSCLE = true;
	public static final boolean LOSEWEIGHT = false;
	
	private static final double[] maleRoughFat = {0.08, 0.15, 0.25, 0.35};
	private static final double[] femaleRoughFat = {0.15, 0.25, 0.35, 0.45};

    /**
     * @author ZYF
     * Add five functions and there are some change for some parameters
     * @param roughFat:
     * maleRoughFat
     *                0: <12%
     *                1: 12%~20%
     *                2: 20%~30%
     *                3: >30%
     *                4: empty, do some input to the preciseFat
     * femaleRoughFat
     *                0: <20%
     *                1: 20%~30%
     *                2: 30%~40%
     *                3: >40%
     *                4: empty, do some input to the preciseFat
     * @param jobType:
     *               0: tight
     *               1: medium
     *               2: high
     *               3: very high
     * @param exerciseFrequency:
     *               0: 1-2
     *               1: 3-4
     *               2: 5-6
     *               3: 7
     * @param exerciseInterval:
     *               0: 0 ~ 30min
     *               1: 30 ~ 60min
     *               2: 60 ~ 90min
     *               3: more than 90min
     */
    public Evaluation (int gender, int age, int height, double weight, int roughFat, double preciseFat, int jobType, boolean goalType, int exerciseFrequency, int exerciseInterval) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.roughFat = roughFat;
        this.preciseFat = preciseFat;
        this.jobType = jobType;
        this.goalType = goalType;
        this.exerciseFrequency = exerciseFrequency;
        this.exerciseInterval = exerciseInterval;
        this.exerciseTime = INVALID;
        this.weightGoal = INVALID;
        this.daysToGoal = INVALID;
    }

    /**
     * @param exerciseTime:(new added)
     *               1: for before breakfast
     *               2: for before lunch
     *               3: for before supper
     *               4: for after supper
     */
    public void setExerciseTime(int exerciseTime){
        this.exerciseTime = exerciseTime;
    }

    public void setWeightGoal(double weightGoal){
        this.weightGoal = weightGoal;
    }

    public void setDaysToGoal(int daysToGoal){
        this.daysToGoal = daysToGoal;
    }

    public boolean getGoalType(){
        return this.goalType;
    }
    //New added finish
	
	Evaluation (int gender, int age, int height, double weight, int roughFat, boolean goalType, double weightGoal, int daysToGoal,//these entries have to be filled by every user
					double preciseFat,	//we can convert it from roughFat
					int jobType,
					int exerciseFrequency,	//times pre week
					int exerciseInterval
					) {
		this.gender = gender;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.roughFat = roughFat;
		this.goalType = goalType;
		this.weightGoal = weightGoal;
		this.daysToGoal = daysToGoal;
		this.preciseFat = preciseFat;
		this.jobType = jobType + 1;
		this.exerciseFrequency = exerciseFrequency + 1;
		this.exerciseInterval = exerciseInterval + 1;
	}
	
	Evaluation (int gender, int age, int height, double weight, int roughFat, boolean goalType, double weightGoal, int daysToGoal) {
		this.gender = gender;
		this.age = age;
		this.height = height;
		this.weight = weight;
		this.roughFat = roughFat;
		this.goalType = goalType;
		this.weightGoal = weightGoal;
		this.daysToGoal = daysToGoal;
		this.preciseFat = INVALID;
		this.jobType = INVALID;
		this.exerciseFrequency = INVALID;
		this.exerciseInterval = INVALID;
	}
	
	
	/**
	 * @return 0 if the parameters are valid
	 */
	private int validationCheck() {
		return 0;
	}
	
	
	/**
	 * @return number 0 of result is the low boundary of body weight, number 1 of result is the high
	 */
	public double[] getWeightBoundary() {
		double[] result = new double[2];
		// BMI from 18.5 to 24 is healthy
		double heightInM = (double)height / 100;
		result[0] = 18.5 * (heightInM * heightInM);
		result[1] = 24 * (heightInM * heightInM);
		return result;
	}
	
	/**
	 * @return it not precise !!!
	 */
	public int getShortestDaysToGoal() {
		// for lose weight, the delta is -700kcal/day
		// for gain muscle, the delta is +1000kcal/day
		double delta = 0;
		if (goalType == GAINMUSCLE) {
			delta = 1000;
		} else {
			delta = -700;
		}
		
		return (int)((double)((weightGoal - weight) * 7200) / delta);
		
	}
	
	
	/**
	 * @return energy consumption/day
	 */
	public double getBMR() {
		// get BMR
		double BMR = 0;
		double alpha = 0;
		double beta = 0;
		if (preciseFat == INVALID) {
			if (gender == MALE)
				preciseFat = maleRoughFat[roughFat];
			else
				preciseFat = femaleRoughFat[roughFat];

		}

		BMR = Math.max((370 + 21.6 * preciseFat),
				(13.88 * weight + 4.16 * (double)height - 3.43 * (double)age - 112.4 * (double)gender + 53.34));

		if (jobType != INVALID) {
			alpha = (double)jobType / 4 * 0.5;
		}
		if (exerciseInterval != INVALID) {
			beta = ((double)exerciseInterval / 4 * 0.5 +  (double)exerciseFrequency / 4 * 0.5) * 0.5;
		}

		double metabolic = BMR * (1.0 + (alpha + beta) * 0.15);
		return metabolic;
	}
	
	public Report report() {
		final String NotSure = "ToBeContinued";
//		HashMap<String, Object> result = new HashMap<String, Object>();
		Report report = new Report();
		report.setGender(gender);
		report.setAge(age);
		report.setHeight(height);
		report.setWeight(weight);
		report.setGoalType(goalType);

		report.setRoughFat(roughFat);
		report.setPreciseFat(preciseFat);
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		report.setUpdatetime(sdf.format(date));

		//BMI
		double heightInM = (double)height / 100;
		double BMI = weight / (heightInM * heightInM);
//		result.put("BMI", BMI);
		report.setBMI(BMI);
		
		//BMR
//		result.put("BMR", getBMR());
		report.setBMR(getBMR());

		//weight
		double lWeight = 18.5 * heightInM * heightInM;
		double hWeight = 24 * heightInM * heightInM;
		double bWeight = (lWeight + hWeight) / 2;
//		result.put("BestWeight", bWeight);
//		result.put("BestWeightMin", lWeight);
//		result.put("BestWeightMax", hWeight);
		report.setBestWeight(bWeight);
		report.setBestWeightMin(lWeight);
		report.setBestWeightMax(hWeight);
		
		//heart rate: HRmax = 208 - 0.7 * age, fat if 200 - 0.5 * age
		//the burning heart rate is 0.6 ~ 0.9 HRmax
		double HRmax = 0;
		if (BMI > 24)
			HRmax = 200 - 0.5 * age;
		else
			HRmax = 208 - 0.7 * (double)age;
		double lHR = 0.6 * HRmax;
		double hHR = 0.9 * HRmax;
//		result.put("BurningRateMin", lHR);
//		result.put("BurningRateMax", hHR);
		report.setBurningRateMin(lHR);
		report.setBurningRateMax(hHR);
		
		//exercise frequency and interval are constant
		//frequency 3 times/week
		//interval 45 minutes
//		result.put("SuggestFitFrequency", 3);
//		result.put("SuggestFitTime", 45);
		report.setSuggestFitFrequency(3);
		report.setSuggestFitTime(45);
		
		//protein intaking per day, g/day, not precise
		// GAINMUSCLE: carbohydrate : protein : fat = 4:2:1
		// LOSEWEIGHT: carbohydrate : protein : fat = 4:4:2
		double protein = 0;
		double carbohydrate = 0;
		double fat = 0;
		if (goalType == GAINMUSCLE) {
			protein = 1.8 * weight;
			carbohydrate = 2 * protein;
			fat = protein / 2;
			
		} else {
			protein = 1.2 * weight;
			carbohydrate = protein;
			fat = protein / 2;
		}
//		result.put("ProteinIntake", protein);
//		result.put("ProteinIntakeMin", protein * 0.9);
//		result.put("ProteinIntakeMax", protein * 1.1);
//		result.put("CarbohydrateIntakeMin", carbohydrate * 0.9);
//		result.put("CarbohydrateIntakeMax", carbohydrate * 1.1);
//		result.put("FatIntake", fat);
//		result.put("FatIntakeMin", fat * 0.9);
//		result.put("FatIntakeMax", fat * 1.1);
		report.setProteinIntake(protein);
		report.setProteinIntakeMin(protein * 0.9);
		report.setProteinIntakeMax(protein * 1.1);
		report.setCarbohydrateIntake(carbohydrate);
		report.setCarbohydrateIntakeMin(carbohydrate * 0.9);
		report.setCarbohydrateIntakeMax(carbohydrate * 1.1);
		report.setFatIntake(fat);
		report.setFatIntakeMax(fat * 1.1);
		report.setFatIntakeMin(fat * 0.9);
		
		//total calories intake pre day, nore precise, kcal/day
		double intake = protein * 4 + carbohydrate * 4 + fat * 9;
//		result.put("CaloriesIntake", intake);
//		result.put("CaloriesIntakeMin", intake * 0.9);
//		result.put("CaloriesIntakeMax", intake * 1.1);
		report.setCaloriesIntake(intake);
		report.setCaloriesIntakeMax(intake * 1.1);
		report.setCaloriesIntakeMin(intake * 0.9);

		//exercise should consume about 0.2 ~ 0.3 of total intake, not precise
//		result.put("SuggestFitCalories", 0.25 * intake);
//		result.put("SuggestFitCaloriesMin", 0.2 * intake);
//		result.put("SuggestFitCaloriesMax", 0.3 * intake);
		report.setSuggestFitCalories(0.25 * intake);
		report.setSuggestFitCaloriesMin(0.2 * intake);
		report.setSuggestFitCaloriesMax(0.3 * intake);

		//water intake, from USDA
//		result.put("WaterIntake", (3.7 - gender));
//		result.put("WaterIntakeMin", (3.7 - gender) * 0.9);
//		result.put("WaterIntakeMax", (3.7 - gender) * 1.1);
		report.setWaterIntakeMax((3.7 - gender) * 1.1);
		report.setWaterIntakeMin((3.7 - gender) * 0.9);
		report.setWaterIntake((3.7 - gender));
		
		//fiber intake is constant? more than 25g/day
//		result.put("FiberIntake", 25);
//		result.put("FiberIntakeMin", 22.5);
//		result.put("FiberIntakeMax", 27.5);
		report.setFiberIntake(25);
		report.setFiberIntakeMax(27.5);
		report.setFiberIntakeMin(22.5);
		
		//unsaturated fatty acids intake, constant, 250mg/day
//		result.put("UnsaturatedFattyAcidsIntake", 250);
//		result.put("UnsaturatedFattyAcidsIntakeMin", 225);
//		result.put("UnsaturatedFattyAcidsIntakeMax", 275);
		report.setUnsaturatedFattyAcidsIntake(250);
		report.setUnsaturatedFattyAcidsIntakeMax(275);
		report.setUnsaturatedFattyAcidsIntakeMin(225);
		
		//cholesterol less than 300mg/day, not sure
//		result.put("CholesterolIntake", 300);
//		result.put("CholesterolIntakeMin", 270);
//		result.put("CholesterolIntakeMax", 330);
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
//		result.put("SodiumIntakeMin", ALSodium);
//		result.put("SodiumIntakeMax", ULSodium);
		report.setSodiumIntakeMax(ULSodium);
		report.setSodiumIntakeMin(ALSodium);
		
		//VC intake, from USDA, mg/day
		double ALVC = 90;
		double ULVC = 2000;
//		result.put("VCIntakeMin", (ALVC - gender * 15));
//		result.put("VCIntakeMax", ULVC);
		report.setVCIntakeMin((ALVC - gender * 15));
		report.setVCIntakeMax(ULVC);
		
		//VD intake, from USDA, IU/day
		double ALVD = 600;
		double ULVD = 4000;
//		result.put("VDIntakeMin", ALVD);
//		result.put("VDIntakeMax", ULVD);
		report.setVDIntakeMin(ALVD);
		report.setVDIntakeMax(ULVD);
		
		//breakfast should have about 0.3 of total calories intakes
		//lunch 0.4
		//dinner 0.3
//		result.put("BreakfastRate", 0.3 * intake);
//		result.put("LunchRate", 0.4 * intake);
//		result.put("DinnerRate", 0.3 * intake);
		report.setBreakfastRate(0.3 * intake);
		report.setLunchRate(0.4 * intake);
		report.setDinnerRate(0.3 * intake);

		//not sure
//		result.put("DietStructureOilMin",NotSure);
//		result.put("DietStructureOilMax",NotSure);
//		result.put("DietStructureMeatMin",NotSure);
//		result.put("DietStructureMeatMax",NotSure);
//		result.put("DietStructureMilkMin",NotSure);
//		result.put("DietStructureMilkMax",NotSure);
//		result.put("DietStructureVegetableMin",NotSure);
//		result.put("DietStructureVegetableMax",NotSure);
//		result.put("DietStructureFruitMin",NotSure);
//		result.put("DietStructureFruitMax",NotSure);
//		result.put("DietStructureGrainMin",NotSure);
//		result.put("DietStructureGrainMax",NotSure);
//		result.put("SnackMorningRate",NotSure);
//		result.put("SnackAfternoonRate",NotSure);

        return report;
	}
}
