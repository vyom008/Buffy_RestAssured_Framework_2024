pipeline {
    agent any
    
    tools {
        maven 'maven'
    }
    
    environment {
        PATH = "C:\\Windows\\System32;C:\\Windows;C:\\Windows\\System32\\WindowsPowerShell\\v1.0;C:\\Program Files\\Git\\bin;C:\\Program Files\\Git\\usr\\bin;C:\\TEST AUTOMATION BEGINS\\maven\\apache-maven-3.9.0\\bin;%PATH%"
    }
    
    stages {
        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat '''
                echo %PATH%
                mvn -Dmaven.test.failure.ignore=true clean package
                '''
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        stage("Deploy to QA") {
            steps {
                echo "deploy to qa"
            }
        }
        
        stage('Regression API Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/vyom008/Buffy_RestAssured_Framework_2024.git'
                    bat '''
                    echo %PATH%
                    mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml
                    '''
                }
            }
        }
        
        stage("Publish Allure Reports - QA") {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
                		stage('Publish Extent Report') {
    	steps {
        publishHTML([allowMissing: false,
                     alwaysLinkToLastBuild: false,
                     keepAll: false,
                     reportDir: 'target',
                     reportFiles: 'APIExecutionReport.html',
                     reportName: 'API HTML Extent Report',
                     reportTitles: ''])
    }
	}
        
        stage("Deploy to STAGE") {
            steps {
                echo "deploy to STAGE done"
            }
        }
        
        stage('Sanity API Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/vyom008/Buffy_RestAssured_Framework_2024.git'
                    bat '''
                    echo %PATH%
                    mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml
                    '''
                }
            }
        }
        
        stage("Publish Allure Reports - Staging") {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        		stage('Publish Extent Report') {
    	steps {
        publishHTML([allowMissing: false,
                     alwaysLinkToLastBuild: false,
                     keepAll: false,
                     reportDir: 'target',
                     reportFiles: 'APIExecutionReport.html',
                     reportName: 'API HTML Extent Report',
                     reportTitles: ''])
    }
	}
        
        stage("Deploy to PROD") {
            steps {
                echo "deploy to PROD"
            }
        }
    }
}
