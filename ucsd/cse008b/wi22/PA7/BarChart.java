///////////////////////////////////////////////////////////////////////////////
// Main Class File:    BarChartRacer.java
// File:               BarChart.java
// Quarter:            CSE 8B Winter 2022
//
//
// Author:             Kameron Gano, kgano@ucsd.edu
// Instructor's Name:  Prof Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
// Pair Partner:       Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof Gregory Miranda
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Defines a barchart given a title, x axis, and source of data
 * Has 3 member ArrayLists to keep track of an entry's name, value, and color
 * Draws one image per chunk of data (a given date)
 *
 * Bugs: None known
 *
 * @author Kameron Gano
 * @author Ryo Andrew Onozuka
 */

import java.awt.Color;
import java.awt.*;
import java.util.ArrayList;

public class BarChart {

    // color palette for bars
    private static final Color[] PALETTE = StdDraw.initColors();
    private String title; // bar chart title
    private String xAxisLabel; // x-axis label
    private String dataSource; // data source
    private String caption; // caption
    private ArrayList<String> names; // list of bar names
    private ArrayList<Integer> values; // list of bar values
    private ArrayList<Color> colors; // list of bar colors

    /**
     * Initializes BarChart with title, xAxisLabel, dataSource.
     *
     * @param title of the BarChart
     * @param xAxisLabel of the BarChart
     * @param dataSource of the BarChart
     */
    public BarChart(String title, String xAxisLabel, String dataSource) {
        this.title = title;
        this.xAxisLabel = xAxisLabel;
        this.dataSource = dataSource;
        this.reset();
    }

    /**
     * Sets the caption (year) for each Bar.
     *
     * @param caption initializes caption with implicit reference.
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Adds any given name, value, and color corresponding to the paletteIndex
     * to "this" ArrayList.
     *
     * @param name of BarChart
     * @param paletteIndex used to get color from PALETTE
     * @param value of BarChart
     */
    public void add(String name, int value, int paletteIndex) {
        this.names.add(name);
        this.colors.add(PALETTE[paletteIndex]);
        this.values.add(value);
    }

    /**
     * Removes all bars from the bar chart. Sets caption to empty string.
     */
    public void reset() {
        this.names = new ArrayList<String>();
        this.colors = new ArrayList<Color>();
        this.values = new ArrayList<Integer>();

        this.caption = "";
    }

        /*
     * #############################################################################
     * ###########
     * Do not modify methods below this line.
     * #############################################################################
     * ###########
     */

    /**
     * compute units (multiple of 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000, ...)
     * so that between 4 and 8 axes labels
     * there is no need to modify this method, it is used with draw()
     */
    private static int getUnits(double xmax) {
        int units = 1;
        while (Math.floor(xmax / units) >= 8) {
            // hack to identify 20, 200, 2000, ...
            if (units % 9 == 2)
                units = units * 5 / 2;
            else
                units = units * 2;
        }
        return units;
    }

    /**
     * Draws this bar chart to standard draw.
     * Do not touch this method, it should draw
     */
    public void draw() {
        StdDraw.clear();
        // nothing to draw
        if (this.names.isEmpty())
            return;

        // leave room for at least 8 bars
        int numberOfBars = Math.max(8, this.names.size());

        // set the scale of the coordinate axes
        double xmax = Double.NEGATIVE_INFINITY;
        for (int value : this.values) {
            if (value > xmax)
                xmax = value;
        }

        StdDraw.setXscale(-0.01 * xmax, 1.2 * xmax);
        StdDraw.setYscale(-0.01 * numberOfBars, numberOfBars * 1.25);

        // draw title
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 24));
        StdDraw.text(0.6 * xmax, numberOfBars * 1.2, this.title);

        // draw x-axis label
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 16));
        StdDraw.textLeft(0, numberOfBars * 1.10, this.xAxisLabel);

        // draw axes
        int units = this.getUnits(xmax);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (int unit = 0; unit <= xmax; unit += units) {
            StdDraw.setPenColor(StdDraw.GRAY);
            StdDraw.text(unit, numberOfBars * 1.02, String.format("%,d", unit));
            StdDraw.setPenColor(new Color(230, 230, 230));
            StdDraw.line(unit, 0.1, unit, numberOfBars * 1.0);
        }

        // draw caption
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        if (this.caption.length() <= 4)
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 100));
        else if (this.caption.length() <= 8)
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 60));
        else
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, 40));
        StdDraw.textRight(1.15 * xmax, 0.2 * numberOfBars, this.caption);

        // draw data source acknowledgment
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 14));
        StdDraw.textRight(1.14 * xmax, 0.1 * numberOfBars, this.dataSource);

        // draw bars
        for (int i = 0; i < this.names.size(); i++) {
            String name = this.names.get(i);
            int value = this.values.get(i);
            Color color = this.colors.get(i);
            StdDraw.setPenColor(color);
            StdDraw.filledRectangle(0.5 * value, numberOfBars - i - 0.5, 0.5 * value, 0.4);
            StdDraw.setPenColor(StdDraw.BLACK);
            int fontSize = (int) Math.ceil(14 * 10.0 / numberOfBars);
            StdDraw.setFont(new Font("SansSerif", Font.BOLD, fontSize));
            StdDraw.textRight(value - 0.01 * xmax, numberOfBars - i - 0.5, name);
            StdDraw.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
            StdDraw.setPenColor(StdDraw.DARK_GRAY);
            StdDraw.textLeft(value + 0.01 * xmax, numberOfBars - i - 0.5, String.format("%,d", value));
        }
    }
}
