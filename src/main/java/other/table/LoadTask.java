/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author chuna
 */
public class LoadTask extends SwingWorker<String, List<Object[]>> {

    private final int max;
    private final int itemsPerPage;
    private Object[][] data;

    protected LoadTask(int max, int itemsPerPage) {
        super();
        this.max = max;
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public String doInBackground() {
        int current = 0;
        int c = max / itemsPerPage;
        int i = 0;
        while (i < c && !isCancelled()) {
            current = makeRowListAndPublish(current, itemsPerPage);
            i++;
        }
        int m = max % itemsPerPage;
        if (m > 0) {
            makeRowListAndPublish(current, m);
        }
        return "Done";
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    private int makeRowListAndPublish(int current, int size) {
        List<Object[]> result = new ArrayList<>(size);
        int j = current;
        while (j < getData().length) {
            result.add(getData()[j]);
            j++;
        }
        publish(result);
        return j;
    }
}
