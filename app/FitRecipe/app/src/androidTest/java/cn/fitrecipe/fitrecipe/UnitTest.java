package cn.fitrecipe.fitrecipe;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.dao.LabelDao;
import cn.fitrecipe.android.dao.LabelsDao;
import cn.fitrecipe.android.entity.Label;
import cn.fitrecipe.android.entity.Labels;

/**
 * Created by wk on 2015/8/12.
 */
public class UnitTest extends InstrumentationTestCase{
    public void test() {
        LabelsDao labelsDao = new LabelsDao(getInstrumentation().getTargetContext());
        Labels labels = new Labels();
        labels.setId(1);
        labels.setCategory("sfdasd");
        List<Label> many = new ArrayList<>();
        Label label = new Label();
        label.setId(1);
        label.setName("abc");
        label.setType("def");
        label.setLabels(labels);
        many.add(label);
        LabelDao labelDao = new LabelDao(getInstrumentation().getTargetContext());
        labelDao.add(label);
        labels.setMany(many);
        labelsDao.addLabels(labels);
        assertEquals(labelsDao.getLabels(1).getMany().size(), 1);
    }
}
