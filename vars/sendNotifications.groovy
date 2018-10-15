#!/usr/bin/env groovy

def call(String buildStatus = 'STARTED')  {
	buildStatus = buildStatus ?: 'SUCCESS'

	// Default values
	def color = ''
	def prefix = "${env.JOB_NAME} - #${env.BUILD_NUMBER}"
	def suffix = "(<${env.BUILD_URL}|Open>)"
	def message = ''

	def duration = ''
	if (currentBuild.duration/(1000.0*60*60) >= 1) {
		duration = "${currentBuild.duration/(1000*60*60)} hrs"
	} else if (currentBuild.duration/(1000.0*60) >= 1) {
		duration = "${currentBuild.duration/(1000*60)} min"
	} else {
		duration = "${currentBuild.duration/(1000)} sec"
	}

	if (buildStatus == 'STARTED') {
		color = 'warning'
		message = "${prefix} Started ${suffix}"
	} else if (buildStatus == 'SUCCESS') {
		color = 'good'
		message = "${prefix} Success after ${duration} ${suffix}"
	} else {
		color = 'danger'
		message = "${prefix} Failed after ${duration} ${suffix}"
	}

	slackSend (color: color, message: message)
}
