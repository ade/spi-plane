#!/bin/bash
cd /home/pi/spi
echo "-----------------------------"
echo " SportsmanPi test script "
echo "-----------------------------"
echo "Starting in update mode..."
java -jar SportsmanPi.jar update
if [ -f update.jar.tmp ]
  then
  rm --f SportsmanPi.jar
  mv update.jar.tmp SportsmanPi.jar
fi
echo "Starting in test mode..."
java -jar SportsmanPi.jar test
