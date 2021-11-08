pipeline {
	agent any
	stages {
		stage('Maven Build') {
			steps {
				withMaven {
					sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install"
				}
				sh 'echo "Hello World"'
			}
		}
		stage('Sonar Scan'){
		    tools {
				jdk "openjdk11" // the name you have given the JDK installation in Global Tool Configuration
			}
			environment {
				scannerHome = tool 'ReverbScanner' // the name you have given the Sonar Scanner (in Global Tool Configuration)
			}
			steps {
				withSonarQubeEnv(installationName: 'SonarCloud', credentialsId: 'CD_sonarcloud2') {
					sh '''mvn clean verify sonar:sonar
					${scannerHome}/bin/sonar-scanner -X \
					-Dsonar.java.binaries=target/classes   \
					-Dsonar.sources=. \
					-Dsonar.java.binaries=target/classes   \
					-Dsonar.organization='revature-reverb' \
					-Dsonar.projectKey='Revature-Reverb_backend' \
					-Dsonar.sources=. '''
				}
			}
		}
		stage("Quality Gate") {
		    	steps {
		    		timeout(time: 1, unit: 'MINUTES') {
		    			waitForQualityGate abortPipeline: true
					}
				}
		}
	}
}