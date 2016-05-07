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
import vm.emergencevg.ui.domain.*;

/**
 * Ohjelman graafinen käyttöliittymä.
 */
public class GUI implements Runnable {

    public JFrame frame;
    public DrawBoard drawboard;
    JPanel sidePanel;

    public GenerativeSpace space;
    ControlFunctions functions;

    public IterationsListener itTracker;
    public ScaleListener scaleUpdater;

    public Integer scale;

    /**
     * Konstruktori.
     *
     * @param space Ohjelman taustalooppi.
     * @param sidelength Muuttuja, joka määrittää piirtoalustalle piirrettävien
     * objektien oikeat mittasuhteet.
     */
    public GUI(GenerativeSpace space, int sidelength) {
        this.space = space;
        functions = space.functions;
        this.scale = sidelength;
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
        int width = 1300;
        int height = 700;

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

        drawboard = new DrawBoard(space, this);
        drawboard.setBackground(Color.BLACK);
        container.add(drawboard);

        MouseListen mListener = new MouseListen(space.mController, this);
        drawboard.addMouseListener(mListener);
        drawboard.addMouseMotionListener(mListener);

        createPanelComponents();
    }

    /**
     * Luo käyttöliittymän sivupaneelin oliot ja asettaa ne haluttuihin
     * sijainteihin.
     */
    public void createPanelComponents() {
        JLabel describeLogicLab = new JLabel("Describe particleType:");
        JLabel formatDescriberLab = new JLabel("name, ForNew, ToLive");
        JLabel exampleLabel = new JLabel("Example: name, 2 7, 5");
        JLabel drawSomeLab = new JLabel("<------ Draw something!");
        JLabel chooseparticleLab = new JLabel("Choose particle:");
        JLabel speedLab = new JLabel("Speed(0.2):");
        JLabel fileNameLab = new JLabel("Filename:");
        JLabel iterationsLabel = new JLabel("Iteration:");
        JLabel particleScaleLabel = new JLabel("particleScale(5):");
        JLabel fieldXlab = new JLabel("fieldX:");
        JLabel fieldYlab = new JLabel("fieldY:");

        JTextField tField1 = new JTextField(15);
        JTextField speedTfield = new JTextField(5);
        speedTfield.setText("" + space.speedModifier);
        JTextField fileNameTField = new JTextField(15);
        JTextField iterationField = new JTextField(5);
        JTextField particleScaleTField = new JTextField(3);
        JTextField spaceXtField = new JTextField(3);
        JTextField spaceYtField = new JTextField(3);

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
        JButton fieldSizeButton = new JButton("setFieldSize");

        fieldSizeButton.addActionListener(new FieldSizeListener(this, spaceXtField, spaceYtField));
        clearRecordButton.addActionListener(new ClearRecordListener(space, frame));
        button1.addActionListener(new StartListener(frame, functions));
        button2.addActionListener(new StopListener(frame, functions));
        button3.addActionListener(new ClearListener(frame, functions));

        ColorListener cListener = new ColorListener(frame, colorList);
        FormListener fListener = new FormListener(frame, formList);

        colorList.addActionListener(cListener);
        formList.addActionListener(fListener);

        ParticleTypeSelectListener listener = new ParticleTypeSelectListener(frame, space, particleList);
        particleList.addActionListener(listener);
        listener.initialize();

        clearParticleTypesButton.addActionListener(new ClearParticleTypesListener(space, frame, listener));
        saveButton.addActionListener(new SaveListener(space, frame, fileNameTField));
        loadPresentationButton.addActionListener(new LoadPresentationListener(space, frame, fileNameTField, listener));
        loadParticleTypesButton.addActionListener(new LoadParticleTypesListener(space, frame, fileNameTField, listener));

        itTracker = new IterationsListener(space, frame, iterationField);
        scaleUpdater = new ScaleListener(this, particleScaleTField, space.functions);
        iterationField.addActionListener(itTracker);
        tField1.addActionListener(new ParticleTypeInputListener(frame, space, listener, cListener, fListener, tField1));
        speedTfield.addActionListener(new SpeedInputListener(frame, space, speedTfield));
        particleScaleTField.addActionListener(scaleUpdater);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        constraints.gridx = 0;
        constraints.gridy = 0;
        sidePanel.add(fieldXlab, constraints);

        constraints.gridy = 1;
        sidePanel.add(spaceXtField, constraints);

        constraints.gridy = 2;
        sidePanel.add(fieldYlab, constraints);

        constraints.gridy = 3;
        sidePanel.add(spaceYtField, constraints);

        constraints.gridy = 4;
        sidePanel.add(fieldSizeButton, constraints);

        constraints.gridy = 5;
        sidePanel.add(particleScaleLabel, constraints);

        constraints.gridy = 6;
        sidePanel.add(particleScaleTField, constraints);

        constraints.gridx = 1;
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
        sidePanel.add(describeLogicLab, constraints);

        constraints.gridy = 6;
        sidePanel.add(formatDescriberLab, constraints);

        constraints.gridy = 7;
        sidePanel.add(exampleLabel, constraints);

        constraints.gridy = 8;
        sidePanel.add(tField1, constraints);

        constraints.gridy = 9;
        sidePanel.add(colorList, constraints);

        constraints.gridy = 10;
        sidePanel.add(formList, constraints);

        constraints.gridy = 11;
        sidePanel.add(chooseparticleLab, constraints);

        constraints.gridy = 12;
        sidePanel.add(particleList, constraints);

        constraints.gridy = 13;
        sidePanel.add(clearParticleTypesButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        sidePanel.add(speedLab, constraints);

        constraints.gridy = 8;
        sidePanel.add(speedTfield, constraints);

        constraints.gridy = 9;
        sidePanel.add(iterationsLabel, constraints);

        constraints.gridy = 10;
        sidePanel.add(iterationField, constraints);

        constraints.gridy = 11;
        sidePanel.add(clearRecordButton, constraints);

        constraints.gridy = 12;
        sidePanel.add(button1, constraints);

        constraints.gridy = 13;
        sidePanel.add(button2, constraints);

        constraints.gridy = 14;
        sidePanel.add(button3, constraints);

        constraints.gridy = 15;
        sidePanel.add(drawSomeLab, constraints);
    }
}
