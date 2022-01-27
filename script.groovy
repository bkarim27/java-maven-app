def buildJar() {
    echo "building jar"
    sh 'mvn package'
} 

def buildImage() {
    echo "building the docker image"
    sh 'docker build -t 139.59.177.91:8083/java-maven-app:1.5 .'
} 

def deployApp() {
    echo "deploying to nexus"
    withCredentials([usernamePassword(credentialsId: 'c75c489e-0c72-468e-99fb-0d00eb95766e', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "echo $PASS | docker login -u $USER --password-stdin 139.59.177.91:8083"
        sh 'docker push 139.59.177.91:8083/java-maven-app:1.5'
    }
} 

return this
