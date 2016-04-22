package vm.emergencevg.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.logic.GenerativeSpace;
import vm.emergencevg.ui.domain.ClearListener;
import vm.emergencevg.ui.domain.ClearParticleTypesListener;
import vm.emergencevg.ui.domain.ClearRecordListener;
import vm.emergencevg.ui.domain.ColorListener;
import vm.emergencevg.ui.domain.FormListener;
import vm.emergencevg.ui.domain.IterationsListener;
import vm.emergencevg.ui.domain.LoadParticleTypesListener;
import vm.emergencevg.ui.domain.LoadPresentationListener;
import vm.emergencevg.ui.domain.LogicInputListener;
import vm.emergencevg.ui.domain.LogicSelectListener;
import vm.emergencevg.ui.domain.SaveListener;
import vm.emergencevg.ui.domain.SpeedInputListener;
import vm.emergencevg.ui.domain.StartListener;
import vm.emergencevg.ui.domain.StopListener;

/**
 * Ohjelman graafinen käyttöliittymä.
 */
public class GUI implements Runnable {

    public JFrame frame;
    GenerativeSpace space;
    ControlFunctions functions;
    public DrawBoard drawboard;
    public IterationsListener itTracker;
    JPanel sidePanel;
    int sideLength;

    /**
     *
     * @param space Ohjelman taustalooppi.
     * @param sidelength Muuttuja, joka määrittää piirtoalustalle piirrettävien
     * objektien oikeat mittasuhteet.
     */
    public GUI(GenerativeSpace space, int sidelength) {
        this.space = space;
        functions = space.functions;
        this.sideLength = sidelength;
    }

    /**
     * Käynnistää käyttöliittymän. Luodaa ensin Jframe olio pohjaksi.
     * Määritetään käyttöliittymän koko ja sulku operaatio. Kutsutaan
     * käyttöliittymä oliot luovaa metodia, pakataan JFrame ja asetetaan se
     * näkyväksi.
     */
    @Override
    public void run() {
        frame = new JFrame("EmergenceVG");
        int width = (space.xlength + 1) * sideLength + 10 + 100;
        int height = (space.ylength + 1) * sideLength + 10 + 20;

        frame.setPreferredSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Luo käyttöliittymän Komponentit. Luo ensin sivupaneelin ja piirtoalustan.
     * Määrittää piirtoalustan kuuntelijat ja kutsuu metodia, joka luo
     * sivupaneelin oliot.
     */
    public void createComponents(Container container) {
        sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(Color.CYAN);
        container.add(sidePanel, BorderLayout.EAST);

        drawboard = new DrawBoard(space, sideLength);
        drawboard.setBackground(Color.BLACK);
        container.add(drawboard);

        MouseListen mListener = new MouseListen(space.mController, sideLength);
        drawboard.addMouseListener(mListener);
        drawboard.addMouseMotionListener(mListener);

        createPanelComponents();
    }

    /**
     * Luo käyttöliittymän sivupaneelin oliot ja asettaa ne haluttuihin
     * sijainteihin.
     */
    public void createPanelComponents() {
        JLabel label0 = new JLabel("Describe the particle logic:");
        JLabel label1 = new JLabel("Format: name, ForNew, ToLive");
        JLabel label2 = new JLabel("Example: name, 2 7, 5");
        JLabel label3 = new JLabel("<------ Draw something!");
        JLabel label4 = new JLabel("Choose the logic to draw:");
        JLabel label5 = new JLabel("Speed:");
        JLabel fileNameLab = new JLabel("Filename:");
        JLabel iterationsLabel = new JLabel("Iteration:");

        JTextField tField1 = new JTextField(15);
        JTextField tField2 = new JTextField(5);
        tField2.setText("" + space.speedModifier);
        JTextField fileNameTField = new JTextField(15);
        JTextField iterationField = new JTextField(5);

        JComboBox<String> colorList = new JComboBox<String>();
        JComboBox<String> formList = new JComboBox<String>();

        JComboBox<ParticleType> particleList = new JComboBox<ParticleType>();

        JButton saveButton = new JButton("Save");
        JButton loadPresentationButton = new JButton("LoadPresentation");
        JButton loadParticleTypesButton = new JButton("LoadParticleTypes");
        JButton button1 = new JButton("Start");
        JButton button2 = new JButton("Stop");
        JButton button3 = new JButton("Clear");
        JButton clearRecordButton = new JButton("clearRecord");
        JButton clearParticleTypesButton = new JButton("clearParticleTypes");

        clearRecordButton.addActionListener(new ClearRecordListener(space, frame));
        button1.addActionListener(new StartListener(frame, functions));
        button2.addActionListener(new StopListener(frame, functions));
        button3.addActionListener(new ClearListener(frame, functions));

        ColorListener cListener = new ColorListener(frame, colorList);
        FormListener fListener = new FormListener(frame, formList);

        colorList.addActionListener(cListener);
        formList.addActionListener(fListener);

        LogicSelectListener listener = new LogicSelectListener(frame, space, particleList);
        particleList.addActionListener(listener);
        listener.initialize();

        clearParticleTypesButton.addActionListener(new ClearParticleTypesListener(space, frame, listener));
        saveButton.addActionListener(new SaveListener(space, frame, fileNameTField));
        loadPresentationButton.addActionListener(new LoadPresentationListener(space, frame, fileNameTField, listener));
        loadParticleTypesButton.addActionListener(new LoadParticleTypesListener(space, frame, fileNameTField, listener));

        itTracker = new IterationsListener(space, frame, iterationField);
        iterationField.addActionListener(itTracker);
        tField1.addActionListener(new LogicInputListener(frame, space, listener, cListener, fListener, tField1));
        tField2.addActionListener(new SpeedInputListener(frame, space, tField2));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        sidePanel.add(fileNameLab, constraints);

        constraints.gridy = 1;
        sidePanel.add(fileNameTField, constraints);

        constraints.gridy = 2;
        sidePanel.add(saveButton, constraints);

        constraints.gridy = 3;
        sidePanel.add(loadPresentationButton, constraints);

        constraints.gridy = 4;
        sidePanel.add(loadParticleTypesButton, constraints);

        constraints.gridy = 5;
        sidePanel.add(label0, constraints);

        constraints.gridy = 6;
        sidePanel.add(label1, constraints);

        constraints.gridy = 7;
        sidePanel.add(label2, constraints);

        constraints.gridy = 8;
        sidePanel.add(tField1, constraints);

        constraints.gridy = 9;
        sidePanel.add(colorList, constraints);

        constraints.gridy = 10;
        sidePanel.add(formList, constraints);

        constraints.gridy = 11;
        sidePanel.add(label4, constraints);

        constraints.gridy = 12;
        sidePanel.add(particleList, constraints);

        constraints.gridy = 13;
        sidePanel.add(clearParticleTypesButton, constraints);

        constraints.gridy = 14;
        sidePanel.add(button1, constraints);

        constraints.gridy = 15;
        sidePanel.add(button2, constraints);

        constraints.gridy = 16;
        sidePanel.add(button3, constraints);

        constraints.gridy = 17;
        sidePanel.add(label3, constraints);

        constraints.gridy = 18;
        sidePanel.add(label5, constraints);

        constraints.gridy = 19;
        sidePanel.add(tField2, constraints);

        constraints.gridy = 20;
        sidePanel.add(iterationsLabel, constraints);

        constraints.gridy = 21;
        sidePanel.add(iterationField, constraints);

        constraints.gridy = 22;
        sidePanel.add(clearRecordButton, constraints);
    }
}
