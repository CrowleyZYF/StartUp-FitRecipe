package cn.fitrecipe.android.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.Basket;
import cn.fitrecipe.android.entity.BasketRecord;
import cn.fitrecipe.android.entity.PlanComponent;
import cn.fitrecipe.android.entity.PunchRecord;
import cn.fitrecipe.android.entity.Report;
import cn.fitrecipe.android.entity.SeriesPlan;

/**
 * Created by wk on 2015/8/15.
 */
public class FrDbHelper {

    private static FrDbHelper instance;
    private static Context context;

    private FrDbHelper(Context context) {
        this.context = context;
    }

    public static FrDbHelper getInstance(Context context) {
        synchronized (FrDbHelper.class) {
            if(instance == null)
                instance = new FrDbHelper(context);
        }
        return instance;
    }

    /**
     * get Report belongs to Author
     * @return Report
     */
    public Report getReport() {
        ReportDao dao = new ReportDao(context);
        return dao.getReport();
    }

    /**
     * add Report
     * @param report
     */
    public void addReport(Report report) {
        ReportDao dao = new ReportDao(context);
        dao.add(report);
    }

    /**
     * save author
     * @param author
     */
    public void saveAuthor(Author author) {
        new AuthorDao(context).save(author);
    }

    /**
     * Author logout
     * @param author
     */
    public void authorLogout(Author author) {
        new AuthorDao(context).logout(author);
        new ReportDao(context).clear();
    }

    public Author getLoginAuthor() {
        return new AuthorDao(context).getAuthor();
    }


    /**
     * add list of plancomponent to basket
     * @param components
     */
    public void addToBasket(List<PlanComponent> components, String date, String type) {
        BasketDao dao = new BasketDao(context);
        Basket basket = dao.getBasket();
        if(basket == null)  basket = new Basket();
        List<PlanComponent> componentList = basket.getContent();
        if(componentList == null)
            basket.setContent(components);
        else {
            if(components != null) {
                for (int i = 0; i < components.size(); i++) {
                    boolean flag = true;
                    for(int j = 0; j < componentList.size(); j++)
                        if(components.get(i).getName().equals(componentList.get(j).getName())) {
                            componentList.get(j).setAmount(componentList.get(j).getAmount() + components.get(i).getAmount());
                            flag = false;
                            break;
                        }
                    if(flag)
                        componentList.add(components.get(i));
                }
            }
            basket.setContent(componentList);
        }
        dao.add(basket);

        BasketRecordDao dao1 = new BasketRecordDao(context);
        dao1.add(date, type);
    }

    /**
     * remove components from basket
     * @param components
     */
    public void removeFromBasket(List<PlanComponent> components, String date, String type) {
        BasketDao dao = new BasketDao(context);
        Basket basket = dao.getBasket();
        List<PlanComponent> componentList = basket.getContent();
        if(componentList == null)
            basket.setContent(components);
        else {
            if(components != null) {
                for (int i = 0; i < components.size(); i++) {
                    for(int j = 0; j < componentList.size(); j++)
                        if(components.get(i).getName().equals(componentList.get(j).getName())) {
                            componentList.get(j).setAmount(componentList.get(j).getAmount() - components.get(i).getAmount());
                            if(componentList.get(j).getAmount() == 0)
                                componentList.remove(j);
                            break;
                        }
                }
            }
            basket.setContent(componentList);
        }
        dao.add(basket);

        BasketRecordDao dao1 = new BasketRecordDao(context);
        dao1.delete(date, type);
    }
    /**
     * clear basket
     */
    public void clearBasket() {
        BasketDao dao = new BasketDao(context);
        dao.add(new Basket());

        BasketRecordDao dao1 = new BasketRecordDao(context);
        dao1.clear();
    }

    /**
     * save the whole basket
     * @param basket
     */
    public void saveBasket(Basket basket) {
        BasketDao dao = new BasketDao(context);
        dao.add(basket);
    }

    /**
     * get content of basketN
     * @return Basket
     */
    public Basket getBasket() {
        BasketDao dao = new BasketDao(context);
        return dao.getBasket();
    }

    /**
     * join plan :personal plan or offical plan
     * if the plan is personal plan, and already exist, simply rewrite totally
     * @param plan
     */
    public void joinPlan(SeriesPlan plan) {
        SeriesPlanDao dao = new SeriesPlanDao(context);
        dao.add(plan);
    }


    /**
     *
     * @param start
     * @param end
     * @return list of plan
     */
    public List<SeriesPlan> getCalendar(String start, String end) {
        SeriesPlanDao dao = new SeriesPlanDao(context);
        return dao.get(start, end);
    }

    /**
     * get basket info
     * @param start
     * @param end
     * @return map
     */
    public Map<String, List<BasketRecord>> getBasketInfo(String start, String end) {
        BasketRecordDao dao = new BasketRecordDao(context);
        Map<String, List<BasketRecord>> map = new TreeMap<>();
        List<BasketRecord> brs = dao.get(start, end);
        Iterator<BasketRecord> iterator = brs.iterator();
        while(iterator.hasNext()) {
            BasketRecord br = iterator.next();
            if(map.containsKey(br.getDate()))
                map.get(br.getDate()).add(br);
            else {
                List<BasketRecord> newBrs = new ArrayList<>();
                newBrs.add(br);
                map.put(br.getDate(), newBrs);
            }
        }
        return map;
    }

    /**
     * punch date, type
     * @param date
     * @param type
     * @param img
     */
    public void punch(String date, String type, String img) {
        PunchRecordDao dao = new PunchRecordDao(context);
        PunchRecord pr = new PunchRecord();
        pr.setDate(date);
        pr.setType(type);
        pr.setImg(img);
        dao.add(pr);
    }

    /**
     * cancel punch date, type
     * @param date
     * @param type
     */
    public void unpunch(String date, String type) {
        PunchRecordDao dao = new PunchRecordDao(context);
        dao.delete(date, type);
    }

    /**
     * get punch info
     * @param start
     * @param end
     * @return
     */
    public Map<String, List<PunchRecord>> getPunchInfo(String start, String end) {
        PunchRecordDao dao = new PunchRecordDao(context);
        Map<String, List<PunchRecord>> map = new TreeMap<>();
        List<PunchRecord> prs = dao.get(start, end);
        Iterator<PunchRecord> iterator = prs.iterator();
        while(iterator.hasNext()) {
            PunchRecord pr = iterator.next();
            if(map.containsKey(pr.getDate()))
                map.get(pr.getDate()).add(pr);
            else {
                List<PunchRecord> newPrs = new ArrayList<>();
                newPrs.add(pr);
                map.put(pr.getDate(), newPrs);
            }
        }
        return map;
    }
}
