def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.6.3'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "./script.groovy"
                }
            }
        }
        stage("build jar") {
                    steps {
                        script {
                            gv.buildJar()
                    }
             }
        }
        stage("build docker image") {
            steps {
                script {
                    gv.buildImage()
                }
            }
        }
        stage("deploy - docker hub") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }   
}
