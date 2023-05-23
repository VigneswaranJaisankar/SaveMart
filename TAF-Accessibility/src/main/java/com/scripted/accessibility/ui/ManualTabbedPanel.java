package com.scripted.accessibility.ui;

import java.awt.Button;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import org.apache.commons.validator.UrlValidator;

import com.scripted.accessibility.utils.AccessibilityUtils;
import com.scripted.accessibility.utils.ImageComponent;

/**
 * This class ManualTabbedPanel is used to design Manual tab functionality.
 */
public class ManualTabbedPanel extends Panel implements ActionListener, FocusListener {
   
    /** The serialVersionUID. */
    private static final long serialVersionUID = 1L;

    String[] schemes = { "http", "https" };
    UrlValidator urlValidator = new UrlValidator(schemes);

    /** The url text field. */
    private final TextField urlTextField;

    /** The Start button. */
    private final Button startButton;

    /** Scan page button. */
    private final Button scanPage;

    /** Stop execution button. */
    private final Button stopExecution;

    /** Show report button. */
    private final Button showReport;

    /** Accessibility Utils Object. */
    AccessibilityUtils accessibilityCopUtils = new AccessibilityUtils();

    /**
     *ManualTabbedPanel constructor Instantiates a  manual executor frame.
     */
    public ManualTabbedPanel() {
        setBounds(35, 400, 50, 450);
        setLayout(null);

        ImageComponent imageComponent = new ImageComponent("accessibility-Small.png");
        imageComponent.setFocusable(true);
        Panel imagePanel = new Panel();
        imagePanel.setBounds(20, 40, 115, 115);
        imagePanel.add(imageComponent);
        add(imagePanel);

      /*  imageComponent = new ImageComponent("xpanxion-ust-global-group.png");
        imagePanel = new Panel();
        imagePanel.setBounds(400, 50, 155, 45);
        imagePanel.add(imageComponent);
        add(imagePanel);
*/
        Label label = new Label("Accessibility");
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 18);
        label.setFont(labelFont);
        label.setBounds(200, 100, 200, 30);
        add(label);

        imageComponent = new ImageComponent("AccessibilityCop-Small.png");
        imagePanel = new Panel();
        imagePanel.setBounds(220, 125, 115, 115);
        imagePanel.add(imageComponent);
        add(imagePanel);

        labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);
        label = new Label("Application URL:");
        label.setFont(labelFont);
        label.setBounds(60, 320, 120, 30);
        add(label);

        urlTextField = new TextField("Please enter application url.", 300);
        urlTextField.setBounds(200, 320, 350, 30);
        urlTextField.addFocusListener(this);
        add(urlTextField);

        startButton = new Button("Start");
        startButton.setBounds(60, 450, 100, 30);
        startButton.setEnabled(false);
        startButton.addActionListener(this);
        add(startButton);

        scanPage = new Button("Scan Page");
        scanPage.setBounds(185, 450, 100, 30);
        scanPage.setEnabled(false);
        scanPage.addActionListener(this);
        add(scanPage);

        stopExecution = new Button("Stop Execution");
        stopExecution.setBounds(310, 450, 100, 30);
        stopExecution.setEnabled(false);
        stopExecution.addActionListener(this);
        add(stopExecution);

        showReport = new Button("Show Report");
        showReport.setBounds(435, 450, 100, 30);
        showReport.setEnabled(false);
        showReport.addActionListener(this);
        add(showReport);
    }

    /**
     * actionPerformed methods can perform the action depending upon the commands.
     * Start Test, ScanPage of given Url, Stop Execution and Show Report.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start")) {
            if (!urlValidator.isValid(urlTextField.getText())) {
                urlTextField.setText("Invalid url. please enter correct url.");
                startButton.setEnabled(false);
            } else {
                urlTextField.setEnabled(false);
                startButton.setEnabled(false);
                accessibilityCopUtils.startExecution(urlTextField.getText());
                scanPage.setEnabled(true);
                stopExecution.setEnabled(true);
            }
        }

        if (e.getActionCommand().equals("Scan Page")) {
            scanPage.setEnabled(false);
            scanPage.setLabel("Scanning...");
            stopExecution.setEnabled(false);
            accessibilityCopUtils.scanPage();
            scanPage.setEnabled(true);
            scanPage.setLabel("Scan Page");
            stopExecution.setEnabled(true);
        }

        if (e.getActionCommand().equals("Stop Execution")) {
            startButton.setEnabled(false);
            stopExecution.setEnabled(false);
            scanPage.setEnabled(false);
            accessibilityCopUtils.stopExecution();
            showReport.setEnabled(true);
        }

        if (e.getActionCommand().equals("Show Report")) {
            accessibilityCopUtils.showReport();
        }
    }

    public void focusGained(FocusEvent e) {
        urlTextField.setText("");
        startButton.setEnabled(true);
    }

    public void focusLost(FocusEvent e) {
    }
}
