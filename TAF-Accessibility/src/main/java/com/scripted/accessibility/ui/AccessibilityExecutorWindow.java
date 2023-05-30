package com.scripted.accessibility.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JTabbedPane;


/**
 * The Class AccessibilityExecutorWindow inherits the frame and also implements WindowListner it is main window for selecting suite file
 * and executing script.
 */
public class AccessibilityExecutorWindow extends Frame implements WindowListener {

    /** The serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    private JTabbedPane tabs;

    /**
     *AccessibilityExecutorWindow constructor Instantiates a new accessibility executor frame.
     */
    AccessibilityExecutorWindow() {
        super("Accessibility Test Cases Executor");
        setResizable(false);

        tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.addTab("Automation", new AutomationTabbedPanel(this));
        tabs.addTab("Manual", new ManualTabbedPanel());      
        tabs.setBounds(5, 30, 590, 615);
        add(tabs, BorderLayout.CENTER);

        setLayout(null);
        setSize(600, 650);
        addWindowListener(this);
        setVisible(true);
        setFrameLocation();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(final WindowEvent e) {
        

    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(final WindowEvent e) {
        dispose();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(final WindowEvent e) {
        

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(final WindowEvent e) {
        

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(final WindowEvent e) {
        

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(final WindowEvent e) {
        

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(final WindowEvent e) {
        
    }

    /**
     * SetFrameLocation method sets the frame location.
     */
    private void setFrameLocation() {
        final Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        final int iCoordX = (objDimension.width - this.getWidth()) / 2;
        final int iCoordY = (objDimension.height - this.getHeight()) / 2;
        this.setLocation(iCoordX, iCoordY);
    }

}
