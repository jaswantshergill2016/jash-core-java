curl -v -X POST "http://localhost:8888/monitor" \
-H "Content-Type: application/json" \
-H "X-Event-Key: repo:push" \
-H "X-Hook-UUID: webhook-uuid" \
-d '{"push": {"changes": []} }'



-Do the following on the root and it will pick DockerFile
docker build --tag=alpine-java:base --rm=true .

-Do the following on the root and it will pick DockerFile.server
docker build --file=Dockerfile.server --tag=config-server:latest --rm=true .

-useful docker and docker-compose commands

- [ ] docker inspect config-server
- [ ] docker stop config-server
- [ ] docker rm config-server
- [ ] docker-compose up -d client
- [ ] docker-compose up --build --force-recreate
- [ ] docker-compose rm -f -s -v order-service
- [ ] docker exec -it order-service /bin/sh

- CLEANING UP stuff
https://linuxize.com/post/how-to-remove-docker-images-containers-volumes-and-networks/