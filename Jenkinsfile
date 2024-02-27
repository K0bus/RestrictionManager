pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install -B -Dbuild.number=${BUILD_NUMBER}'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'target/RestrictionManager*.jar', fingerprint: true
            withCredentials([string(credentialsId: 'discord-hook-rm', variable: 'DISCORD_URL')]) {
                        discordSend webhookURL: DISCORD_URL,
                            title: "$JOB_NAME #$BUILD_NUMBER",
                            thumbnail: "https://static.spigotmc.org/img/spigot.png",
                            description: "CI Automated in Jenkins",
                            footer: "by K0bus",
                            successful: true,
                            link: env.BUILD_URL,
                            showChangeset: true,
                            result: currentBuild.currentResult
            }
        }
    }
}