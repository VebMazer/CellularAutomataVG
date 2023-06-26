
package vm.emergencevg.ui.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;
import vm.emergencevg.logic.Environment;

public class ModeListener implements ActionListener {
    Environment      environment;
    JFrame               frame;
    JRadioButtonMenuItem normalModeOption;
    JRadioButtonMenuItem receiverModeOption;
    JRadioButtonMenuItem senderModeOption;
    JRadioButtonMenuItem dotGridModeOption;
    JRadioButtonMenuItem lineGridModeOption;
    JRadioButtonMenuItem randomSpawnCycleModeOption1;
    JRadioButtonMenuItem randomSpawnCycleModeOption2;

    ButtonGroup          modeButtonGroup;

    public ModeListener(
        Environment      environment,
        JFrame               frame,
        JRadioButtonMenuItem normalModeOption,
        JRadioButtonMenuItem receiverModeOption,
        JRadioButtonMenuItem senderModeOption,
        JRadioButtonMenuItem dotGridModeOption,
        JRadioButtonMenuItem lineGridModeOption,
        JRadioButtonMenuItem randomSpawnCycleModeOption1,
        JRadioButtonMenuItem randomSpawnCycleModeOption2
    ) {
        this.environment                       = environment;
        this.frame                       = frame;
        this.normalModeOption            = normalModeOption;
        this.receiverModeOption          = receiverModeOption;
        this.senderModeOption            = senderModeOption;
        this.dotGridModeOption           = dotGridModeOption;
        this.lineGridModeOption          = lineGridModeOption;
        this.randomSpawnCycleModeOption1 = randomSpawnCycleModeOption1;
        this.randomSpawnCycleModeOption2 = randomSpawnCycleModeOption2;

        this.modeButtonGroup = new ButtonGroup();
        this.modeButtonGroup.add(this.normalModeOption);
        this.modeButtonGroup.add(this.receiverModeOption);
        this.modeButtonGroup.add(this.senderModeOption);
        this.modeButtonGroup.add(this.dotGridModeOption);
        this.modeButtonGroup.add(this.lineGridModeOption);
        this.modeButtonGroup.add(this.randomSpawnCycleModeOption1);
        this.modeButtonGroup.add(this.randomSpawnCycleModeOption2);

        this.normalModeOption.addActionListener(this);
        this.receiverModeOption.addActionListener(this);
        this.senderModeOption.addActionListener(this);
        this.dotGridModeOption.addActionListener(this);
        this.lineGridModeOption.addActionListener(this);
        this.randomSpawnCycleModeOption1.addActionListener(this);
        this.randomSpawnCycleModeOption2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "normal mode":
                environment.bot.mode = 0;
                break;
            case "network receiver mode":
                environment.bot.mode = -2;
                break;
            case "network sender mode":
                environment.bot.mode = -1;
                break;
            case "dot grid mode":
                environment.bot.mode = 1;
                break;
            case "line grid mode":
                environment.bot.mode = 2;
                break;
            case "random spawn mode 1":
                environment.bot.mode = 3;
                break;
            case "random spawn mode 2":
                environment.bot.mode = 4;
                break;
            default:
                System.out.println("ModeListener: unknown action command");
                break;
        }

        frame.requestFocus();
    }
}
