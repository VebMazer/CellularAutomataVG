
package vm.emergencevg.control;
import java.util.Random;
import vm.emergencevg.logic.Environment;

public class Bot {
    public Environment environment;
    public int mode;
    public int iterationsPast;
    public Random random;
    public int generalInterval;

    public Bot(Environment environment) {
        random = new Random();
        this.environment = environment;
        mode = 0;
        iterationsPast = 0;
        generalInterval = 25;
    }

    public void mode1() {
        int interval = 20;
        if(iterationsPast >= interval) {
            for (int i = 0; i < environment.width; i++) {
                for (int j = 0; j < environment.height; j++) {
                    if(i % 3 == 0 && j % 2 == 0) {
                        environment.functions.placeParticle(environment.mController.pKey, i, j);
                    }
                }
            }
            iterationsPast = 0;
        }
    }

    public void mode2() {
        int interval = 20;
        if(iterationsPast >= interval) {
            for (int i = 0; i < environment.width; i++) {
                for (int j = 0; j < environment.height; j++) {
                    if(j % 5 == 0) {
                        environment.functions.placeParticle(environment.mController.pKey, i, j);
                    }
                }
            }
            iterationsPast = 0;
        }
    }

    public void mode3() {
        int interval = 25;
        if(iterationsPast >= interval) {
            environment.functions.clear();
            for (int i = 0; i < environment.width; i++) {
                for (int j = 0; j < environment.height; j++) {
                    if(random.nextInt(10000) == 11)environment.functions.placeParticle(environment.mController.pKey, i, j);
                }
            }
            iterationsPast = 0;
        }
    }

    public void mode4() {
        int maxInterval = 50;
        if(iterationsPast % maxInterval == 0) generalInterval = random.nextInt(maxInterval)+1;
        if(iterationsPast % generalInterval == 0) {
            environment.functions.clear();
            for (int i = 0; i < environment.width; i++) {
                for (int j = 0; j < environment.height; j++) {
                    if(random.nextInt(10000) == 11)environment.functions.placeParticle(environment.mController.pKey, i, j);
                }
            }
        }
        if(iterationsPast > 521) iterationsPast = 1;
    }

    public void update() {
        iterationsPast++;
        if(mode == 1) mode1();
        else if(mode == 2) mode2();
        else if(mode == 3) mode3();
        else if(mode == 4) mode4();
        else if(mode == -1 || mode == -2) communicationMode();
        else if (mode < -9) environment.communicator.groupID = mode;
    }

    // Sending client must use mode: -1, while the receiving clients
    // must use mode: -2
    public void communicationMode() {
        if (mode == -1) environment.communicator.sending = true;
        else if (mode == -2) environment.communicator.sending = false;
    }
}
