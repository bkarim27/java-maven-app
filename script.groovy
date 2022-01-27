def buildJar() {
    echo "building jar"
    sh 'ls $WORKSPACE'
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image"
    sh 'docker build -t badshak/demo-app:java-maven-app-2.3 .'
} 

def deployApp() {
    echo "deploying to docker hub"
    withCredentials([usernamePassword(credentialsId: 'DockerHub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push badshak/demo-app:java-maven-app-2.3'
    }
} 

return this
