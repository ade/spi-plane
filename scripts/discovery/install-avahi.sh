sudo apt-get -q install avahi-daemon
sudo insserv avahi-daemon
sudo wget http://ade.se/projects/spi/discovery/multiple.service -O /etc/avahi/services/multiple.service
sudo /etc/init.d/avahi-daemon restart