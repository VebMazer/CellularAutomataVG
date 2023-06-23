
package vm.emergencevg.control;
import java.util.Random;
import vm.emergencevg.logic.GenerativeSpace;

public class Bot {
    public GenerativeSpace space;
    public int mode;
    public int iterationsPast;
    public Random random;
    public int generalInterval;

    public Bot(GenerativeSpace space) {
        random = new Random();
        this.space = space;
        mode = 0;
        iterationsPast = 0;
        generalInterval = 25;
    }

    public void mode1() {
        int interval = 20;
        if(iterationsPast >= interval) {
            for (int i = 0; i < space.xlength; i++) {
                for (int j = 0; j < space.ylength; j++) {
                    if(i % 3 == 0 && j % 2 == 0) {
                        space.functions.placeParticle(space.mController.pKey, i, j);
                    }
                }
            }
            iterationsPast = 0;
        }
    }

    public void mode2() {
        int interval = 20;
        if(iterationsPast >= interval) {
            for (int i = 0; i < space.xlength; i++) {
                for (int j = 0; j < space.ylength; j++) {
                    if(j % 5 == 0) {
                        space.functions.placeParticle(space.mController.pKey, i, j);
                    }
                }
            }
            iterationsPast = 0;
        }
    }

    public void mode3() {
        int interval = 25;
        if(iterationsPast >= interval) {
            space.functions.clear();
            for (int i = 0; i < space.xlength; i++) {
                for (int j = 0; j < space.ylength; j++) {
                    if(random.nextInt(10000) == 11)space.functions.placeParticle(space.mController.pKey, i, j);
                }
            }
            iterationsPast = 0;
        }
    }

    public void mode4() {
        int maxInterval = 50;
        if(iterationsPast % maxInterval == 0) generalInterval = random.nextInt(maxInterval)+1;
        if(iterationsPast % generalInterval == 0) {
            space.functions.clear();
            for (int i = 0; i < space.xlength; i++) {
                for (int j = 0; j < space.ylength; j++) {
                    if(random.nextInt(10000) == 11)space.functions.placeParticle(space.mController.pKey, i, j);
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
        else if (mode < -9) space.communicator.groupID = mode;
    }

    // Sending client must use mode: -1, while the receiving clients
    // must use mode: -2
    public void communicationMode() {
        if (mode == -1) space.communicator.sending = true;
        else if (mode == -2) space.communicator.sending = false;
    }
}
