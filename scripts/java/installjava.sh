#!/bin/bash
echo "$Downloading java 8 jdk for ARM"
wget http://www.java.net/download/jdk8/archive/b100/binaries/jdk-8-ea-b100-linux-arm-vfp-hflt-24_jul_2013.tar.gz

echo "Installing."
sudo tar zxvf jdk-8-ea-b100-linux-arm-vfp-hflt-24_jul_2013.tar.gz -C /opt
sudo update-alternatives --install "/usr/bin/java" "java" "/opt/jdk1.8.0/bin/java" 1 
sudo update-alternatives --install "/usr/bin/javac" "javac" "/opt/jdk1.8.0/bin/javac" 1 
java -version