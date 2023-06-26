#!/bin/bash

rm CellularAutomataVG.jar
rm -rf bin

# Requires that the nesting depth for all files stays the same.
javac -d bin src/vm/emergencevg/*/*.java src/vm/emergencevg/*/*/*.java
cd bin

# Requires that the nesting depth for all files stays the same.
jar cmvf ../MANIFEST.MF ../CellularAutomataVG.jar vm/emergencevg/*/*.class vm/emergencevg/*/*/*.class

# If you increase the folder nesting depth then you need to add vm/emergencevg/*/*/*/*.class after to the jar command. 1 '/*' for each increase in depth.
# Note that an old class file will not get rewritten if the edited files code does not compile.
