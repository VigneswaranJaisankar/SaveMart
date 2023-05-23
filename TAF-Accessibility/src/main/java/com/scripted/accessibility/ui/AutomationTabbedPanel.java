package com.scripted.accessibility.ui;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.scripted.accessibility.utils.AccessibilityUtils;
import com.scripted.accessibility.utils.ImageComponent;

/**
 * This class AutomationTabbedPanel is used to design Automation tab functionality.
 */
public class AutomationTabbedPanel extends Panel implements ActionListener {

   /** The Id serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The file dialog. */
    private FileDialog fileDialog;

    /** The file selected text area. */
    private final TextArea fileSelectedTextArea;

    /** The execute button. */
    private final Button executeButton;

    /** Show report button. */
    private final Button showReport;

    /** Parent frame. */
    private Frame parent;

    /** Accessibility Utils Object. */
    AccessibilityUtils accessibilityCopUtils = new AccessibilityUtils();

    /**
     * constructor AutomationTabbedPanel designs Automation tab functionality.
     * @param parent Object
     */
    public AutomationTabbedPanel(Frame parent) {
        this.parent = parent;

        setBounds(35, 400, 50, 450);
        setLayout(null);

        ImageComponent imageComponent = new ImageComponent("accessibility-Small.png");
        imageComponent.setFocusable(true);
        Panel imagePanel = new Panel();
        imagePanel.setBounds(20, 40, 115, 115);
        imagePanel.add(imageComponent);
        add(imagePanel);

        /*imageComponent = new ImageComponent("xpanxion-ust-global-group.png");
        imagePanel = new Panel();
        imagePanel.setBounds(400, 50, 155, 45);
        imagePanel.add(imageComponent);
        add(imagePanel);*/

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
        label = new Label("Select Suite File:");
        label.setFont(labelFont);
        label.setBounds(60, 320, 150, 30);
        add(label);

        showFileDialogDemo();

        label = new Label("File selected:");
        label.setFont(labelFont);
        label.setBounds(60, 400, 150, 30);
        add(label);

        fileSelectedTextArea = new TextArea("Please select file", 50, 300, TextArea.SCROLLBARS_NONE);
        fileSelectedTextArea.setBounds(220, 400, 300, 50);
        fileSelectedTextArea.setEditable(false);
        add(fileSelectedTextArea);

        executeButton = new Button("Execute");
        executeButton.setBounds(220, 500, 100, 30);
        executeButton.setEnabled(false);
        executeButton.addActionListener(this);
        add(executeButton);

        showReport = new Button("Show Report");
        showReport.setBounds(350, 500, 100, 30);
        showReport.setEnabled(false);
        showReport.addActionListener(this);
        add(showReport);
    }

    /**
     * Method actionPerformed invoke accessibility test script and performs action, scans and enables report.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Execute")) {
            // Invoke accessibility test script
            executeButton.setEnabled(false);
            AccessibilityUtils accessibilityCopUtils = new AccessibilityUtils();
            accessibilityCopUtils.performAccessibilityOnSites(fileDialog.getDirectory() + fileDialog.getFile());
            showReport.setEnabled(true);
        } else if (e.getActionCommand().equals("Show Report")) {
            accessibilityCopUtils.showReport();
        }
    }

    /**
     * Show file dialog demo.
     */
    private void showFileDialogDemo() {
        fileDialog = new FileDialog(parent, "Select file");
        final Button showFileDialogButton = new Button("Browse...");
        showFileDialogButton.setBounds(220, 320, 100, 30);
        showFileDialogButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                fileDialog.setVisible(true);
                if (fileDialog.getDirectory() != null) {
                    fileSelectedTextArea.setText(fileDialog.getDirectory() + fileDialog.getFile());
                    executeButton.setEnabled(true);
                }
            }
        });
        add(showFileDialogButton);
    }

}
