FROM debian:latest
WORKDIR /dist
COPY . .
USER root
RUN apt-get update
RUN printf '#!/bin/sh\nexit 0' > /usr/sbin/policy-rc.d
RUN export RUNLEVEL=1
RUN apt-get -y install apache2
RUN mkdir -p /var/www/interval
RUN mkdir -p /var/www/sky
RUN useradd interval
RUN chown -R interval:interval /var/www/interval
RUN chmod -R 755 /var/www/interval
COPY ./interval.conf /etc/apache2/sites-available/
COPY ./sky.conf /etc/apache2/sites-available/
RUN a2ensite interval.conf
RUN a2dissite 000-default.conf
RUN cp -r * /var/www/interval/

CMD ["apachectl", "-D", "FOREGROUND"]
