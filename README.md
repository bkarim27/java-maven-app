# java-maven-app
Branch dockerhub-deploy-1 : build a jar file using maven, build a docker image using the jar and push the image to docker hub
Branch nexus-deploy-1 : build a jar file using maven, build a docker image using the jar and push the image to nexus
Branch global--JSL-dockerhub-deploy-3 : build a jar file using maven, build a docker image using the jar and push the image to docker hub - it uses Jenkkins Shared Library referred in Jenkins global settings
Branch local-JSL-ec2-deploy-4 : build a jar file using maven, build a docker image using the jar,push the image to docker hub, deploy and run the image container in an EC2 server - it uses a different Jenkkins Shared Library directly from GitHub(it also has a Groovy class)
Branch version-increment-ec2-deploy-5 : increment the POM version, build a jar file using maven, build a docker image using the jar,push the image to docker hub, deploy and run the image container in an EC2 server - it uses a different Jenkkins Shared Library directly from GitHub(it also has a Groovy class), push the incremented POM to GitHub
