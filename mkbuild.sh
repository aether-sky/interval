cp Dockerfile sky.conf interval.conf html/build/dist/
cd html/build/dist
docker build -t olivinedogue/interval:v1 .
