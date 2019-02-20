/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package other.custom;

/**
 *
 * @author chuna
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * A simple demonstration application showing how to create a bar chart with a
 * custom item label generator.
 *
 */
public class BarChart extends JPanel {

    private String chartTitle, xName, yName, series;
    private String[] columnNames;
    private Number[] numbers;

    public BarChart() {

    }

    public BarChart(String chartTitle, String xName, String yName, String series, String[] columnNames, Number[] numbers) {
        this.chartTitle = chartTitle;
        this.xName = xName;
        this.yName = yName;
        this.series = series;
        this.columnNames = columnNames;
        this.numbers = numbers;
        init();
    }

    public void init() {
        final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 320));
        setLayout(new FlowLayout());
        add(chartPanel);
    }

    private CategoryDataset createDataset() {

        // row keys...
        final String series1 = "First";

        // column keys...
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < numbers.length; i++) {
            dataset.addValue(numbers[i], series, columnNames[i]);
        }

        return dataset;

    }

    private JFreeChart createChart(final CategoryDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                chartTitle, // chart title
                xName, // domain axis label
                yName, // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                false, // include legend
                true, // tooltips?
                false // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.15);

        // disable bar outlines...
        final CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;

    }

//    public static void main(final String[] args) {
//        String[] columnNames = {"Có mặt", "Vắng mặt", "Vắng mặt(Có đăng ký)", "Đi trễ", "Điểm danh thủ công", "Điểm danh tự động"};
//        Number[] numbers = {1, 2, 3, 4, 5};
//        final BarChart demo = new BarChart("Thống kế kết quả điểm danh", "Loại danh sách", "Số lượng", columnNames, numbers);
//        JFrame frame = new JFrame();
//        frame.setLayout(new GridLayout());
//        frame.getContentPane().add(demo);
//        frame.setVisible(true);
//
//    }
}
