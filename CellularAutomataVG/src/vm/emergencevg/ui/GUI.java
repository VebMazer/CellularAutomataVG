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
import javax.swing.JSeparator;

import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import javax.swing.JTextField;
import javax.swing.WindowConstants;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import vm.emergencevg.domain.ParticleType;
import vm.emergencevg.logic.ControlFunctions;
import vm.emergencevg.logic.Environment;
import vm.emergencevg.ui.domain.*;

/**
 * Ohjelman graafinen käyttöliittymä.
 */
public class GUI implements Runnable {
    
    public Environment environment;
    ControlFunctions       functions;

    public Integer scale;

    public JFrame    frame;
    public DrawBoard drawboard;
    
    JMenuBar menuBar;
    JPanel   topBar;

    ModeListener modeListener;
    public IterationsListener itTracker;
    public ScaleListener scaleUpdater;
    public ParticleTypeSelectListener particleTypeListener;

    /**
     * Konstruktori.
     *
     * @param environment Ohjelman taustalooppi.
     * @param sidelength Muuttuja, joka määrittää piirtoalustalle piirrettävien
     * objektien oikeat mittasuhteet.
     */
    public GUI(Environment environment, int sidelength) {
        this.environment = environment;
        functions = environment.functions;
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
        int height = 750;

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
        menuBar = new JMenuBar();

        topBar = new JPanel(new GridBagLayout());
        topBar.setBackground(Color.LIGHT_GRAY);
        container.add(topBar, BorderLayout.NORTH);

        drawboard = new DrawBoard(environment, this);
        drawboard.setBackground(Color.BLACK);
        Border border = new LineBorder(Color.LIGHT_GRAY, 3, true);
        drawboard.setBorder(border);
        container.add(drawboard);

        MouseListen mListener = new MouseListen(environment.mController, this);
        drawboard.addMouseListener(mListener);
        drawboard.addMouseMotionListener(mListener);

        createTopBar();
        
        createFileMenu();
        createParticleMenu();
        createModeMenu();
        //createHelpMenu();
        
        frame.setJMenuBar(menuBar);
    }

    /**
     * Creates the file menu and adds it to the menuBar.
     */
    public void createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem saveAsItem = new JMenuItem("Save As...");
        
        saveAsItem.addActionListener(
            new SaveAsListener(environment, frame)
        );
        
        fileMenu.add(saveAsItem);

        JMenuItem openFileItem = new JMenuItem("Open File...");
        
        openFileItem.addActionListener(
            new OpenFileListener(environment, frame, particleTypeListener)
        );
        
        fileMenu.add(openFileItem);

        JMenuItem ImportParticleTypesItem = new JMenuItem("Import Particle Types...");
        
        ImportParticleTypesItem.addActionListener(
            new ImportParticleTypesListener(environment, frame, particleTypeListener)
        );
        
        fileMenu.add(ImportParticleTypesItem);
    }

    public void createParticleMenu() {
        JMenu particleMenu = new JMenu("Particle");
        menuBar.add(particleMenu);

        JMenuItem createParticle = new JMenuItem("Create Particle");
        
        createParticle.addActionListener(
            new CreateParticleListener(frame, environment, particleTypeListener)
        );
        
        particleMenu.add(createParticle);

        JMenuItem deleteParticles = new JMenuItem("Delete all Particles");
        
        deleteParticles.addActionListener(
            new ClearParticleTypesListener(environment, frame, particleTypeListener)
        );
        
        particleMenu.add(deleteParticles);
    }

    public void createModeMenu() {
        JMenu modeMenu = new JMenu("Mode");
        menuBar.add(modeMenu);

        JRadioButtonMenuItem normalModeOption = new JRadioButtonMenuItem("normal mode", true);
        modeMenu.add(normalModeOption);

        modeMenu.addSeparator();

        JRadioButtonMenuItem receiverModeOption = new JRadioButtonMenuItem("network receiver mode");
        modeMenu.add(receiverModeOption);

        JRadioButtonMenuItem senderModeOption = new JRadioButtonMenuItem("network sender mode");
        modeMenu.add(senderModeOption);

        modeMenu.addSeparator();

        JRadioButtonMenuItem dotGridModeOption = new JRadioButtonMenuItem("dot grid mode");
        modeMenu.add(dotGridModeOption);

        JRadioButtonMenuItem lineGridModeOption = new JRadioButtonMenuItem("line grid mode");
        modeMenu.add(lineGridModeOption);

        modeMenu.addSeparator();

        JRadioButtonMenuItem randomSpawnCycleModeOption1 = new JRadioButtonMenuItem("random spawn mode 1");
        modeMenu.add(randomSpawnCycleModeOption1);

        JRadioButtonMenuItem randomSpawnCycleModeOption2 = new JRadioButtonMenuItem("random spawn mode 2");
        modeMenu.add(randomSpawnCycleModeOption2);
        
        modeListener = new ModeListener(
            environment,
            frame,
            normalModeOption,
            receiverModeOption,
            senderModeOption,
            dotGridModeOption,
            lineGridModeOption,
            randomSpawnCycleModeOption1,
            randomSpawnCycleModeOption2
        );
    }

    public void createHelpMenu() {
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
    }

    /**
     * Creates components for the top bar.
     */
    public void createTopBar() {
        JLabel particleLabel      = new JLabel("particle:");
        JLabel boardWidthLabel    = new JLabel("board width:");
        JLabel boardHeightLabel   = new JLabel("board height:");
        JLabel particleScaleLabel = new JLabel("particle scale:");
        JLabel iterationLabel     = new JLabel("iteration:");
        JLabel speedLabel         = new JLabel("speed:");

        JTextField boardWidthTextField  = new JTextField(3);
        JTextField boardHeightTextField = new JTextField(3);
        JTextField particleScaleTField  = new JTextField(3);
        JTextField iterationTextField   = new JTextField(3);
        JTextField speedTextField       = new JTextField(3);

        JButton playSwitch  = new JButton("Play");
        playSwitch.setPreferredSize(new Dimension(75, 25));
        
        JButton clearButton = new JButton("Clear");
        JButton clearCommandRecordButton = new JButton("Clear Command Record");

        JComboBox<ParticleType> particleList = new JComboBox<ParticleType>();

        boardWidthTextField.addActionListener(
            new BoardWidthListener(this, boardWidthTextField, environment.functions)
        );

        boardHeightTextField.addActionListener(
            new BoardHeightListener(this, boardHeightTextField, environment.functions)
        );

        speedTextField.addActionListener(
            new SpeedInputListener(frame, environment, speedTextField)
        );

        scaleUpdater = new ScaleListener(this, particleScaleTField, environment.functions);
        particleScaleTField.addActionListener(scaleUpdater);

        playSwitch.addActionListener(
            new PlaySwitchListener(playSwitch, frame, environment.functions)
        );

        clearButton.addActionListener(new ClearListener(frame, functions));
        clearCommandRecordButton.addActionListener(new ClearRecordListener(environment, frame));

        itTracker = new IterationsListener(environment, frame, iterationTextField);
        iterationTextField.addActionListener(itTracker);

        particleTypeListener = new ParticleTypeSelectListener(frame, environment, particleList);
        
        particleList.addActionListener(particleTypeListener);
        particleTypeListener.initialize();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(3, 4, 3, 4);

        constraints.gridx = 0;
        constraints.gridy = 0;
        topBar.add(particleLabel, constraints);

        constraints.gridx++;
        topBar.add(particleList, constraints);
        
        constraints.gridx++;
        topBar.add(boardWidthLabel, constraints);

        constraints.gridx++;
        topBar.add(boardWidthTextField, constraints);

        constraints.gridx++;
        topBar.add(boardHeightLabel, constraints);

        constraints.gridx++;
        topBar.add(boardHeightTextField, constraints);
        
        constraints.gridx++;
        topBar.add(particleScaleLabel, constraints);

        constraints.gridx++;
        topBar.add(particleScaleTField, constraints);

        constraints.gridx++;
        topBar.add(new JSeparator(1), constraints);

        constraints.gridx++;
        topBar.add(clearButton, constraints);

        constraints.gridx++;
        topBar.add(playSwitch, constraints);

        constraints.gridx++;
        topBar.add(speedLabel, constraints);

        constraints.gridx++;
        topBar.add(speedTextField, constraints);

        constraints.gridx++;
        topBar.add(iterationLabel, constraints);

        constraints.gridx++;
        topBar.add(iterationTextField, constraints);

        constraints.gridx++;
        topBar.add(clearCommandRecordButton, constraints);
    }
}
